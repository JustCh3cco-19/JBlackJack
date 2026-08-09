# JBlackJack

A desktop Blackjack game written in Java 17 and Swing. The player competes
against the dealer alongside two bots, can save a local profile, and review
their game statistics.

## Features

- core Blackjack rules, including handling aces as either 1 or 11;
- dealer hole card hidden until the end of the round;
- bots and dealer powered by replaceable strategies;
- persistent profiles with validated nicknames and atomic saving;
- responsive Swing interface with updates performed on the Event Dispatch Thread;
- optional audio and images: the game remains usable when assets are unavailable;
- JUnit 5 tests and an automated GitHub Actions build.

## Requirements

- JDK 17 or newer;
- Maven 3.9 or newer.

## Running the application

```bash
mvn clean test
mvn exec:java
```

Alternatively, compile the application without Maven:

```bash
mkdir -p out
javac -d out $(find src/main -name '*.java')
java -cp out:src/main/resources main.blackjack.JBlackJack
```

## Image and audio assets

Third-party assets are not included in the repository for licensing reasons.
The game displays textual card names and remains silent when these files are
not available. Custom assets can be placed at the following paths:

```text
src/main/resources/images/cards/<rank>_of_<suit>.png
src/main/resources/images/avatars/<name>.png
src/main/resources/audio/game.wav
src/main/resources/audio/card_flip.wav
src/main/resources/audio/chip_place.wav
```

Ranks and suits use English names, for example `ace_of_hearts.png`.

## Project structure

```text
src/main/blackjack    application entry point
src/main/model        game rules and domain model
src/main/controller   UI and game coordination
src/main/view         Swing windows
src/main/util         optional services
src/test/java         automated tests
```

Profiles are saved locally in the `profiles/` directory and are not tracked by
Git. Generated Javadoc, UML diagrams, and the compiled report are distributed
with each GitHub Release instead of being committed to the repository.

## Technical report

The English technical report has a dedicated LaTeX source and Makefile. Build
it from the repository root with:

```bash
make -C report
```

The generated PDF is written to `report/build/JBlackJack-report.pdf`.
Use `make -C report clean` to remove auxiliary LaTeX files or
`make -C report distclean` to remove the generated PDF as well.

The CI workflow regenerates `UML/JBlackJack.puml` and `UML/JBlackJack.png`
directly from the Java sources before compiling the report. These generated
files are ignored by Git and published as release assets.

## Automated releases

Every push to `main` runs the tests, packages the application, generates the
Javadoc and UML diagram, and compiles the report. The workflow then creates the
tag and GitHub Release `v<version>` from the Maven version (with `-SNAPSHOT`
removed). If that release already exists, it is left unchanged and no duplicate
is created.

To publish a different version without changing `pom.xml`, open **Actions →
Build documentation and release → Run workflow** and enter a version such as
`2.1.0`. Each release contains the application JAR, the generated Javadoc ZIP,
the PDF report, and both the PNG and PlantUML versions of the generated diagram.
The repository must allow GitHub Actions read/write workflow permissions so that
`GITHUB_TOKEN` can create tags and releases.

## Development

Run the following command before submitting a change:

```bash
mvn verify
```

Game rules must remain in the `model` package. The UI must not directly mutate
hands, decks, or player statistics.
