SHELL := /bin/sh

TEX      := JBlackJack-report.tex
JOB      := JBlackJack-report
BUILD    := build
PDF      := $(BUILD)/$(JOB).pdf
PDFLATEX := pdflatex
FLAGS    := -interaction=nonstopmode -halt-on-error -file-line-error

.PHONY: all report clean distclean

all: report

report: $(PDF)

$(PDF): $(TEX) ../UML/JBlackJack.png
	@mkdir -p $(BUILD)
	$(PDFLATEX) $(FLAGS) -output-directory=$(BUILD) $(TEX)
	$(PDFLATEX) $(FLAGS) -output-directory=$(BUILD) $(TEX)

clean:
	@rm -f $(BUILD)/*.aux $(BUILD)/*.log $(BUILD)/*.out \
		$(BUILD)/*.toc $(BUILD)/*.lof $(BUILD)/*.lot

distclean: clean
	@rm -f $(PDF)

