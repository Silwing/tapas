#!/usr/bin/env python
import csv

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


class TypeHandler(core.Handler):
    def __init__(self, library):
        super(TypeHandler, self).__init__(library)
        self.array_types = {}
        self.changers_lines = []
        self.init_arrays = []
        self.suspicious_ids = []

    def generate_result(self):
        changing_locations = {}
        suspicious_counter = 0
        for id in self.array_types:
            type = self.array_types[id]
            if bin(type).count('1') > 1:
                suspicious_string = ""
                if id in self.suspicious_ids:
                    suspicious_counter += 1
                    suspicious_string = " - SUSPICIOUS"

                changing_locations[id] = "%s - %d%s" % (self.library.find_define(id), type, suspicious_string)

        result_file = open("result.csv", "wb")
        wr = csv.writer(result_file)
        wr.writerow(self.changers_lines)

        return changing_locations, float(len(changing_locations)) / len(self.array_types), float(
            len(changing_locations) - suspicious_counter) / len(self.array_types)

    def handle_line(self, line, current_line):
        if len(line) < 3:
            return

        line_type = line[0]
        try:
            line_number = int(line[1])
        except ValueError:
            return
        line_file = line[2]
        array_ref = None
        type_int = 0

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
        if line_type == "array_init" and id not in self.suspicious_ids:
            if array_ref in self.init_arrays:
                self.suspicious_ids.append(id)
            else:
                self.init_arrays.append(array_ref)
        if type_int == 0:
            return
        if type_int & 8:
            type_int -= 8

        if id in self.array_types:
            current_type = self.array_types[id]
            self.array_types[id] |= type_int
            if current_type != self.array_types[id]:
                self.changers_lines.append(current_line)

        else:
            self.array_types[id] = type_int
