__author__ = 'budde'

from abc import ABCMeta, abstractmethod


class ArrayLibrary:
    def __init__(self):
        self.current_id = 0


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

    def handle_line(self, line_object):
        if len(line_object) < 3:
            return

        line_type = line_object[0]
        line_number = line_object[1]
        line_file = line_object[2]

        if line_type == "array_write":
            array_ref = line_object[6]
            print(self.library.generate_id(line_number, line_file, array_ref))

