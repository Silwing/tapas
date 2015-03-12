#!/usr/bin/env python

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


class TypeHandler(core.Handler):
    def __init__(self, library):
        super(TypeHandler, self).__init__(library)
        self.array_types = {}
        self.changers = {}

    def generate_result(self):
        changing_locations = {}
        for id in self.array_types:
            type = self.array_types[id]
            if bin(type).count('1') > 1:
                changing_locations[id] = "%s - %d" % (self.library.find_define(id), type)

        return changing_locations, float(len(changing_locations)) / len(self.array_types), self.changers

    def handle_line(self, line):
        if len(line) < 3:
            return

        line_type = line[0]
        line_number = int(line[1])

        line_file = line[2]
        array_ref = None
        type_int = 0

        if line_number == 401:
            pass

        if line_type == "array_mr_part":
            return

        if line_type.startswith("array") and len(line) > 6:
            array_ref = line[6]
            type_int = int(line[3])
        elif line_type.startswith("assign_") and len(line) >= 14 and line[8] == "array":
            array_ref = line[12]
            type_int = int(line[9])

        if array_ref is None:
            return

        id = self.library.generate_id(line_number, line_file, line_type, array_ref)

        if type_int == 0:
            return

        if id in self.array_types:
            current_types = self.array_types[id]
            self.array_types[id] |= 1 << type_int
            if current_types != self.array_types[id]:
                if id in self.changers:
                    self.changers[id].append([line_file, line_number, line_type])
                else:
                    self.changers[id] = [[line_file, line_number, line_type]]


        else:
            self.array_types[id] = 1 << type_int
