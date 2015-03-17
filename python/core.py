__author__ = 'budde'

from abc import ABCMeta, abstractmethod


def location_string(file_path, line_no, op_type):
    return "%s:%s:%s" % (file_path, str(line_no), op_type)


class ArrayLibrary:
    def __init__(self):
        self.current_id = -1
        self.address_lookup = {}
        self.loc_lookup = {}
        self.lookup_loc = {}

    def generate_id(self, line_no, file_path, op_type, address):

        loc = location_string(file_path, line_no, op_type)
        if op_type == "array_init":
            self.clear_address(address, loc)
        return self.generate_id_from_loc(loc, address)

    def generate_id_from_loc(self, loc, address):
        if address in self.address_lookup:
            array_id = self.address_lookup[address]
            return array_id

        if loc in self.loc_lookup:
            return self.generate_id_from_loc(loc, self.loc_lookup[loc])

        self.current_id += 1
        self.address_lookup[address] = self.current_id
        self.loc_lookup[loc] = address
        self.lookup_loc[self.current_id] = loc
        return self.current_id

    def find_define(self, array_id):
        return self.lookup_loc[array_id]

    def clear_address(self, address, loc=None):
        if address not in self.address_lookup or (loc in self.loc_lookup and address == self.loc_lookup[loc]):
            return
        del self.address_lookup[address]
        for loc in self.loc_lookup.keys():
            if self.loc_lookup[loc] == address:
                del self.loc_lookup[loc]


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
            try:
                self.library.generate_id(int(line_number), line_file, array_ref)
            except ValueError:
                pass


