#!/usr/bin/env python3
"""Generate a PlantUML class diagram from the project's Java sources."""

from __future__ import annotations

import argparse
import re
from dataclasses import dataclass
from pathlib import Path


TYPE_RE = re.compile(
    r"\b(public\s+)?(?P<kind>class|interface|enum|record)\s+"
    r"(?P<name>[A-Za-z_]\w*)"
    r"(?P<tail>[^\{]*)\{"
)
PACKAGE_RE = re.compile(r"\bpackage\s+([\w.]+)\s*;")


@dataclass(frozen=True)
class JavaType:
    package: str
    kind: str
    name: str
    tail: str
    source: str


def without_comments(source: str) -> str:
    source = re.sub(r"/\*.*?\*/", "", source, flags=re.DOTALL)
    return re.sub(r"//.*", "", source)


def discover(source_root: Path) -> list[JavaType]:
    types: list[JavaType] = []
    for path in sorted(source_root.rglob("*.java")):
        source = without_comments(path.read_text(encoding="utf-8"))
        package_match = PACKAGE_RE.search(source)
        declaration = TYPE_RE.search(source)
        if not declaration:
            continue
        types.append(
            JavaType(
                package=package_match.group(1) if package_match else "default",
                kind=declaration.group("kind"),
                name=declaration.group("name"),
                tail=declaration.group("tail"),
                source=source,
            )
        )
    return types


def names_after(keyword: str, tail: str) -> list[str]:
    match = re.search(rf"\b{keyword}\s+([^{{]+?)(?=\bextends\b|\bimplements\b|$)", tail)
    if not match:
        return []
    return [part.strip().split("<", 1)[0] for part in match.group(1).split(",")]


def write_diagram(types: list[JavaType], destination: Path) -> None:
    known = {java_type.name for java_type in types}
    lines = [
        "@startuml JBlackJack",
        "title JBlackJack - generated from Java sources",
        "skinparam classAttributeIconSize 0",
        "skinparam packageStyle rectangle",
        "skinparam shadowing false",
        "hide empty members",
        "set namespaceSeparator none",
        "left to right direction",
        "",
    ]

    for package in sorted({java_type.package for java_type in types}):
        lines.append(f'package "{package}" {{')
        for java_type in (item for item in types if item.package == package):
            stereotype = " <<record>>" if java_type.kind == "record" else ""
            plantuml_kind = "class" if java_type.kind == "record" else java_type.kind
            lines.append(f"  {plantuml_kind} {java_type.name}{stereotype}")
        lines.extend(["}", ""])

    relationships: set[str] = set()
    for java_type in types:
        for parent in names_after("extends", java_type.tail):
            if parent in known:
                arrow = "<|.." if java_type.kind == "interface" else "<|--"
                relationships.add(f"{parent} {arrow} {java_type.name}")
        for interface in names_after("implements", java_type.tail):
            if interface in known:
                relationships.add(f"{interface} <|.. {java_type.name}")

        # Type references in fields, parameters and return values provide a useful
        # high-level dependency view without requiring a Java compiler plugin.
        for referenced in known - {java_type.name}:
            if re.search(rf"\b{re.escape(referenced)}\b", java_type.source):
                relationships.add(f"{java_type.name} ..> {referenced}")

    lines.extend(sorted(relationships))
    lines.extend(["", "@enduml", ""])
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text("\n".join(lines), encoding="utf-8")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("source_root", type=Path)
    parser.add_argument("output", type=Path)
    args = parser.parse_args()
    java_types = discover(args.source_root)
    if not java_types:
        raise SystemExit(f"No Java types found below {args.source_root}")
    write_diagram(java_types, args.output)
    print(f"Generated {args.output} from {len(java_types)} Java types")


if __name__ == "__main__":
    main()
