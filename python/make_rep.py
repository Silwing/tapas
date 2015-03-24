#!/usr/bin/env python
import csv
from core import ArrayLibrary
from cyclic_handler import CyclicHandler
from type_handler import TypeHandler
from value_handler import ValueHandler
from operation_handler import OperationHandler, OperationNamesHandler

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


def run_maker(library_builder, file_builder, handler_builder):
    files = file_builder()

    if len(files) < 1:
        print("Missing file arguments")
        return
    with open('results.csv', 'wb') as csvfile:
        writer = csv.writer(csvfile, delimiter="\t")
        file_counter = 0
        for filename in files:
            file_counter += 1
            sys.stdout.write("Running file: %s (%d of %d)\n" % (filename, file_counter, len(files)))
            sys.stdout.flush()
            base_name, extension = os.path.splitext(filename)
            clean_file = base_name + "_clean" + extension
            clean_file_object = open(clean_file)
            counter = 0
            size_counter = 0
            file_size = os.stat(clean_file).st_size
            library = library_builder()
            handlers = handler_builder(library)
            for line in clean_file_object:
                counter += 1
                size_counter += len(line)
                if counter % 10000 == 0:
                    sys.stdout.write("\r%2.3f%%" % (float(size_counter) / file_size * 100))
                    sys.stdout.flush()
                line = line.replace("\n", "")
                line_object = line.split("\t")
                if "hash_init" == line_object[0]:
                    library.clear_address(line_object[1])
                    continue
                for handler in handlers:
                    handler.handle_line(line_object, counter)

            sys.stdout.write("\r100.000%\n")
            for handler in handlers:
                result = handler.generate_result()
                result[:0] = [handler.__class__.__name__, filename, library.number_of_arrays()]
                writer.writerow(result)


def build_handlers(library, args):
    handlers = []
    if len(args) >= 3 and ".csv" not in args[1]:
        arg = args[1]
        if "value" in arg:
            handlers.append(ValueHandler(library))
        if "type" in arg:
            handlers.append(TypeHandler(library))
        if "cyclic" in arg:
            handlers.append(CyclicHandler(library))
        if "names" in arg:
            handlers.append(OperationNamesHandler(library))
        if "operation" in arg:
            handlers.append(OperationHandler(library))
        files = sys.argv[2:]

    if len(handlers) == 0:
        handlers = [ValueHandler(library), TypeHandler(library), CyclicHandler(library), OperationNamesHandler(library),
                    OperationHandler(library)]

    blacklists = map(lambda h: h.get_blacklist(), handlers)
    blacklist = reduce(reduce_blacklist, blacklists[1:], blacklists[0])
    library.set_blacklist(blacklist)

    return handlers


def build_files(args):
    result_files = []
    for arg in args:
        if ".csv" in arg:
            result_files.append(arg)
    return result_files


if __name__ == "__main__":
    run_maker(ArrayLibrary, lambda: build_files(sys.argv), lambda lib: build_handlers(lib, sys.argv))

