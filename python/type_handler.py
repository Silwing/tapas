#!/usr/bin/env python

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


def _convert_base(param):
    if param == 0:
        return ""
    return _convert_base(param // 2) + str(param % 2)


class TypeHandler(core.Handler):
    def __init__(self, library):
        super(TypeHandler, self).__init__(library)
        self.array_types = {}


    def generate_result(self):
        for id in self.array_types:
            self.array_types[id] = _convert_base(self.array_types[id])

        return self.array_types


    def handle_line(self, line):
        if len(line) < 3:
            return

        line_type = line[0]
        line_number = line[1]
        line_file = line[2]

        if line_type.startswith("array") and len(line) > 6:
            array_ref = line[6]
            id = self.library.generate_id(line_number, line_file, array_ref)
            type_int = int(line[3])

            if type_int == 0:
                return

            if id in self.array_types:
                self.array_types[id] |= 1 << type_int
            else:
                self.array_types[id] = 1 << type_int
