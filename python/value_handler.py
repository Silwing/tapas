#!/usr/bin/env python

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


class ValueHandler(core.Handler):
    def get_blacklist(self):
        return {
        }

    def __init__(self, library):
        super(ValueHandler, self).__init__(library)
        self.array_value_types = {}
        self.array_types = {}
        self.last_write_id = None
        self.change_locations = {}
        self.partFalses = [14, 49, 87, 91, 94, 165, 194, 280, 312, 331]
        self.ciFalses = []

    def generate_result(self):
        multiple_locations = {}
        multiple_maps = 0
        multiple_lists = 0
        for id in self.array_value_types:
            types = self.array_value_types[id]
            if len(types) > 1:
                multiple_locations[id] = "%s - %s - %s" % (self.array_types[id], self.library.find_define(id), str(types))
                if self.array_types[id] & 4 == 4:
                    multiple_maps += 1
                elif self.array_types[id] & 1 == 1:
                    multiple_lists += 1

        '''return multiple_locations, self.change_locations, len(self.array_types), len(multiple_locations), float(len(multiple_locations)) / len(self.array_value_types), float(multiple_maps) / len(self.array_value_types), float(multiple_lists) / len(self.array_value_types)'''
        return [len(multiple_locations), multiple_maps, multiple_lists]

    def add_type_to_array(self, array_id, vtype, class_name, line_number, line_file, line_type, current_line):
        if vtype == "object":
            assign_type = vtype
        else:
            assign_type = vtype
        if array_id not in self.array_value_types:
            self.array_value_types[array_id] = {assign_type: 1}
        elif assign_type not in self.array_value_types[array_id]:
            if array_id in self.change_locations:
                self.change_locations[array_id].append([line_number, line_file, line_type, current_line])
            else:
                self.change_locations[array_id] = [[line_number, line_file, line_type, current_line]]
            self.array_value_types[array_id][assign_type] = 1
        else:
            self.array_value_types[array_id][assign_type] += 1

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

        if self.last_write_id is not None:

            if line_type.startswith("assign_") and len(line) > 9:
                self.add_type_to_array(self.last_write_id, line[8], line[9], line_number, line_file, line_type, current_line)
            self.last_write_id = None

        if array_ref is not None:

            id = self.library.generate_id(line_number, line_file, line_type, array_ref)

            if line_type.startswith("array") and (id not in self.array_types or (line[3].isdigit() and int(line[3]) != 0)):
                self.array_types[id] = int(line[3])
            elif line_type.startswith("assign_") and (id not in self.array_types or (line[9].isdigit() and int(line[9]) != 0)):
                self.array_types[id] = int(line[9])

            if line_type in ["array_write", "array_append"]:
                self.last_write_id = id
                return

            if line_type in ["array_push", "array_unshift"]:
                for i in range(7, len(line)-1, 5):
                    self.add_type_to_array(id, line[i], line[i+1], line_number, line_file, line_type, current_line)
                return