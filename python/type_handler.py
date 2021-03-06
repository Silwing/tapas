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

    def __init__(self, library, finder):
        super(TypeHandler, self).__init__(library, finder)
        self.array_types = {}
        self.array_types_sum = {}
        self.changers_lines = []
        self.delta = 0.0
        self.checked = []

    def generate_result(self):
        result_list = [0, 0, 0, 0, 0, 0, 0, 0]
        for id in self.array_types:
            t = self.array_types[id] - 1
            result_list[t] += 1
        return [result_list]

    def handle_line(self, line, current_line):
        if len(line) < 3:
            return

        line_type = line[0]
        try:
            line_number = int(line[1])
        except ValueError:
            return
        line_file = line[2]

        if line_type == "array_mr_part":
            return
        try:
            if line_type.startswith("array") and len(line) > 6:
                array_ref = line[6]
                type_int = int(line[3])
            elif line_type.startswith("assign_") and len(line) >= 14 and line[8] == "array":
                array_ref = line[12]
                type_int = int(line[9])
            elif (line_type == "count" or line_type.endswith("sort") or line_type == "in_array") and len(line) > 6 and \
                    line[6].startswith("0x"):
                array_ref = line[6]
                type_int = int(line[3])
            else:
                return
        except ValueError:
            return
        id = self.library.generate_id(line_number, line_file, line_type, array_ref)

        if type_int <= 0 or type_int > 15:
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

        '''Joomla: [17, 34, 96, 99, 103, 110, 112, 113, 128, 181, 182, 192, 215, 373, 395, 403, 459, 487, 553, 585, 613, 614, 620, 624, 647, 660, 680, 709, 825, 831, 872, 950, 1193, 1203, 1207, 1213, 1217, 1231, 1236, 1237, 1239, 1603, 1632, 1773, 1774, 1898, 1913, 1929, 1931, 1961]
           Magento: [43, 75, 125, 126, 133, 206, 243]
        '''
        if self.array_types[id] > 4 and line_type != "array_values" and id not in self.checked:
            lines = self.finder.getLines(line_file, line_number)
            self.checked.append(id)
            pass
