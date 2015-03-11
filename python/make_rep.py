#!/usr/bin/env python
from core import ArrayLibrary, DummyHandler

__author__ = 'Christian Budde Christensen'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "cchris42@uwo.ca"

import sys
import os.path


def run_maker(handlers):
    if len(sys.argv) < 2:
        print("Missing file arguments")
        return

    file_argument_orig = sys.argv[1]
    base_name, extension = os.path.splitext(file_argument_orig)
    clean_file = base_name + "_clean" + extension
    for line in open(clean_file):
        line_object = line.split("\t")
        for handler in handlers:
            handler.handle_line(line_object)


if __name__ == "__main__":
    library = ArrayLibrary()
    run_maker([DummyHandler(library)])
