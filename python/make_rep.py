#!/usr/bin/env python
from abc import ABCMeta, abstractmethod

__author__ = 'Christian Budde Christensen'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "cchris42@uwo.ca"

import sys
import os.path


class ArrayLibrary:
    def __init__(self):
        pass

    def generate_id(self, line_no, file_path, address):
        pass


class Handler:
    __metaclass__ = ABCMeta

    def __init__(self, library):
        self.library = library


    @abstractmethod
    def handle_line(self, line):
        pass

    @abstractmethod
    def generate_result(self):
        pass


class DummyHandler(Handler):
    def generate_result(self):
        return []

    def handle_line(self, line):
        pass


def run_maker(handlers):
    if len(sys.argv) < 2:
        print("Missing file arguments")
        return

    file_argument_orig = sys.argv[1]
    base_name, extension = os.path.splitext(file_argument_orig)
    clean_file = base_name + "_clean" + extension
    for line in open(clean_file):
        for handler in handlers:
            handler.handle_line(line)


if __name__ == "__main__":
    library = ArrayLibrary()
    run_maker([DummyHandler(library)])
