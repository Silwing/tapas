__author__ = 'budde'

from abc import ABCMeta, abstractmethod


def location_string(file_path, line_no, op_type):
    return "%s:%s:%s" % (file_path, str(line_no), op_type)

class Finder:
    def __init__(self):
        self.corpusPath = 'D:/Randi/Programmering/Speciale/tapas-survey/corpus'

    def getLines(self, file, lineno):
        try:
            f = open(self.corpusPath+file)
            lines = f.readlines()
            return lines[lineno-2:lineno+1]
        except:
            return []


class ArrayLibrary:
    def __init__(self, blacklist=None):
        if not blacklist: blacklist = {}
        self.current_id = -1
        self.address_lookup = {}
        self.loc_to_id = {}
        self.id_to_loc = {}
        self.blacklist = blacklist

    def set_blacklist(self, blacklist):
        self.blacklist = blacklist

    def clear_address(self, address):
        if address in self.address_lookup:
            del self.address_lookup[address]

    def generate_id(self, line_no, file_path, op_type, address):

        loc = location_string(file_path, line_no, op_type)

        if loc in self.loc_to_id:
            self.address_lookup[address] = self.loc_to_id[loc]
            return self.loc_to_id[loc]

        if address in self.address_lookup:
            address_id = self.address_lookup[address]
            id_location = self.id_to_loc[address_id]
            if id_location not in self.blacklist or loc not in self.blacklist[id_location]:
                self.loc_to_id[loc] = address_id
                return self.address_lookup[address]

        self.current_id += 1
        self.address_lookup[address] = self.current_id
        self.loc_to_id[loc] = self.current_id
        self.id_to_loc[self.current_id] = loc
        return self.current_id

    def find_define(self, array_id):
        return self.id_to_loc[array_id]

    def number_of_arrays(self):
        return self.current_id+1


class Handler:
    __metaclass__ = ABCMeta

    def __init__(self, library, finder=None):
        self.library = library
        self.finder = finder

    @abstractmethod
    def get_blacklist(self):
        pass

    @abstractmethod
    def handle_line(self, line, current_line):
        pass

    @abstractmethod
    def generate_result(self):
        pass


class DummyHandler(Handler):
    def get_blacklist(self):
        return {}

    def generate_result(self):
        return []

    def handle_line(self, line_object, current_line):
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


