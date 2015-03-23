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


def reduce_blacklist(list1, list2):
    for key in list2:
        if key in list1:
            list1[key] = list1[key] + list(set(list2[key]) - set(list1[key]))
        else:
            list1[key] = list2[key]

    return list1


def run_maker(library, handlers):
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
        if "hash_init" == line_object[0]:
            library.clear_address(line_object[1])
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

    blacklists = map(lambda h: h.get_blacklist(), handlers)
    blacklist = reduce(reduce_blacklist, blacklists[1:], blacklists[0])
    library.set_blacklist(blacklist)

    run_maker(library, handlers)

