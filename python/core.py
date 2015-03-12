__author__ = 'budde'

from abc import ABCMeta, abstractmethod


class ArrayLibrary:
    def __init__(self):
        self.current_id = -1
        self.address_lookup = {}
        self.loc_lookup = {}
        self.lookup_loc = {}

    def generate_id(self, line_no, file_path, address):
        if address in self.address_lookup:
            return self.address_lookup[address]

        loc = "%s:%d" % (file_path, line_no)
        if loc in self.loc_lookup:
            return self.generate_id(line_no, file_path, self.loc_lookup[loc])

        self.current_id += 1
        self.address_lookup[address] = self.current_id
        self.loc_lookup[loc] = address
        self.lookup_loc[self.current_id] = loc
        return self.current_id

    def find_define(self, array_id):
        return self.lookup_loc[array_id]


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

        if line_type.startswith("array_") and len(line_object) > 6:
            array_ref = line_object[6]
            self.library.generate_id(int(line_number), line_file, array_ref)

