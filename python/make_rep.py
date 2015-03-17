#!/usr/bin/env python
from pprint import pprint
from core import ArrayLibrary, DummyHandler
from cyclic_handler import CyclicHandler
from type_handler import TypeHandler
from value_handler import ValueHandler

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

    file_argument_orig = sys.argv[len(sys.argv) - 1]
    base_name, extension = os.path.splitext(file_argument_orig)
    clean_file = base_name + "_clean" + extension
    clean_file_object = open(clean_file)
    counter = 0
    for line in clean_file_object:
        counter += 1
        line_object = line.split("\t")
        for handler in handlers:
            handler.handle_line(line_object, counter)

    for handler in handlers:
        pprint(handler.generate_result())


if __name__ == "__main__":
    library = ArrayLibrary()

    handlers = []
    if len(sys.argv) >= 3:
        for arg in sys.argv[:-1]:
            if arg == "value":
                handlers.append(ValueHandler(library))
            if arg == "type":
                handlers.append(TypeHandler(library))
            if arg == "cyclic":
                handlers.append(CyclicHandler(library))

    else:
        handlers = [ValueHandler(library), TypeHandler(library), CyclicHandler(library)]

    run_maker(handlers)

