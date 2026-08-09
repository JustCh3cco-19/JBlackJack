SHELL := /bin/sh

TEX      := JBlackJack-report.tex
JOB      := JBlackJack-report
BUILD    := build
PDF      := $(BUILD)/$(JOB).pdf
PDFLATEX := pdflatex
FLAGS    := -interaction=nonstopmode -halt-on-error -file-line-error
UML      := ../UML/JBlackJack.png
PUML     := ../UML/JBlackJack.puml
UMLGEN   := ../.github/scripts/generate-uml.py
JAVA     := $(shell find ../src/main -name '*.java' -type f)

.PHONY: all report clean distclean

all: report

report: $(PDF)

$(PDF): $(TEX) $(UML)
	@mkdir -p $(BUILD)
	$(PDFLATEX) $(FLAGS) -output-directory=$(BUILD) $(TEX)
	$(PDFLATEX) $(FLAGS) -output-directory=$(BUILD) $(TEX)

$(UML): $(PUML)
	plantuml -failfast2 -tpng $(PUML)

$(PUML): $(UMLGEN) $(JAVA)
	python3 $(UMLGEN) ../src/main $(PUML)

clean:
	@rm -f $(BUILD)/*.aux $(BUILD)/*.log $(BUILD)/*.out \
		$(BUILD)/*.toc $(BUILD)/*.lof $(BUILD)/*.lot

distclean: clean
	@rm -f $(PDF)
