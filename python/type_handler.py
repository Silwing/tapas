#!/usr/bin/env python
import csv

__author__ = 'Randi Katrine Hilleroee'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core


class TypeHandler(core.Handler):
    def get_blacklist(self):
        return {}

    def __init__(self, library):
        super(TypeHandler, self).__init__(library)
        self.array_types = {}
        self.array_types_sum = {}
        self.changers_lines = []
        self.init_arrays = []
        self.suspicious_ids = []
        self.delta = 0.01

    def generate_result(self):
        changing_locations = {}
        suspicious_counter = 0
        for id in self.array_types:
            type = self.array_types[id]
            if bin(type).count('1') > 1:
                suspicious_string = ""
                id_sum = sum(self.array_types_sum[id].values())
                percent_map = map(lambda x: (
                    x, round(float(self.array_types_sum[id][x]) / id_sum, 4)),
                                  self.array_types_sum[id])
                if all(map(lambda (_, percent): percent <= 1 - self.delta, percent_map)):
                    if id in self.suspicious_ids:
                        suspicious_counter += 1
                        suspicious_string = "*"

                    changing_locations[id] = "%s - %d%s - %d %s" % (
                        self.library.find_define(id), type, suspicious_string, id_sum,
                        str(percent_map))

        num_arrays = self.library.number_of_arrays()
        return [len(changing_locations), len(changing_locations) - suspicious_counter,  num_arrays]

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
        elif line_type == "count" or line_type.endswith("sort") or line_type == "in_array" and len(line) > 6 and \
                line[6].startswith("0x"):
            array_ref = line[6]
            type_int = int(line[3])
        else:
            return
        l = core.location_string(line_file, line_number, line_type)
        id = self.library.generate_id(line_number, line_file, line_type, array_ref)
        define = self.library.find_define(id)

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
            self.array_types[id] |= type_int
            if type_int in self.array_types_sum[id]:
                self.array_types_sum[id][type_int] += 1
            else:
                self.array_types_sum[id][type_int] = 1
        else:
            self.array_types[id] = type_int
            self.array_types_sum[id] = {type_int: 1}
