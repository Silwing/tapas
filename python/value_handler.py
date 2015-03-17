#!/usr/bin/env python

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


class ValueHandler(core.Handler):
    def __init__(self, library):
        super(ValueHandler, self).__init__(library)
        self.array_value_types = {}
        self.last_write_id = None
        self.multiples = {}

    def generate_result(self):
        multiple_locations = {}
        for id in self.array_value_types:
            types = self.array_value_types[id]
            if len(types) > 1:
                multiple_locations[id] = "%s - %s" % (self.library.find_define(id), str(types))

        return multiple_locations, float(len(multiple_locations)) / len(self.array_value_types), self.multiples

    def add_type_to_array(self, array_id, vtype, class_name, line_number, line_file, line_type):
        if vtype == "object":
            assign_type = class_name
        else:
            assign_type = vtype
        if array_id not in self.array_value_types:
            self.array_value_types[array_id] = [assign_type]
        elif assign_type not in self.array_value_types[array_id]:
            self.array_value_types[array_id].append(assign_type)
            if array_id in self.multiples:
                self.multiples[array_id].append([line_number, line_file, line_type])
            else:
                self.multiples[array_id] = [[line_number, line_file, line_type]]

    def handle_line(self, line, current_line):
        if len(line) < 3:
            return

        line_type = line[0]
        line_number = int(line[1])
        array_ref = None
        line_file = line[2]

        if line_type == "array_mr_part":
            return

        if line_type.startswith("array") and len(line) > 6:
            array_ref = line[6]
        elif line_type.startswith("assign_") and len(line) >= 14 and line[8] == "array":
            array_ref = line[12]

        if array_ref is None:
            if self.last_write_id is not None:
                if line_type.startswith("assign_"):
                    self.add_type_to_array(self.last_write_id, line[8], line[9], line_number, line_file, line_type)
                self.last_write_id = None
                return

        else:
            id = self.library.generate_id(line_number, line_file, line_type, array_ref)

            if line_type in ["array_write", "array_append"]:
                self.last_write_id = id
                return

            if line_type in ["array_push", "array_unshift"]:
                for i in range(7, len(line)-1, 5):
                    self.add_type_to_array(id, line[i], line[i+1], line_number, line_file, line_type)
                return





