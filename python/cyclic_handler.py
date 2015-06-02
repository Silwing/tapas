#!/usr/bin/env python
import csv

__author__ = 'budde'

import core


class CyclicHandler(core.Handler):
    def get_blacklist(self):
        return {}

    def __init__(self, library, finder):
        super(CyclicHandler, self).__init__(library)
        self.cyclic_id = {}
        self.finder = finder
        self.ignoreList = []

    def generate_result(self):
        return [[len(self.cyclic_id)]]

    def ignore(self, lineno, file):
        if [lineno, file] in self.ignoreList:
            return True
        else:
            self.ignoreList.append([lineno, file])
            return False

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
        try:
            if line_type.startswith("array") and len(line) > 6:
                array_ref = line[6]
                type_int = int(line[3])
            elif line_type.startswith("assign_") and len(line) >= 14 and line[8] == "array":
                array_ref = line[12]
                type_int = int(line[9])
        except ValueError:
            return
        if array_ref is None:
            return

        id = self.library.generate_id(line_number, line_file, line_type, array_ref)

        if type_int & 8:
            self.cyclic_id[id] = core.location_string(line_file, line_number, line_type)
            if not self.ignore(line_number, line_file):
                lines = self.finder.getLines(line_file, line_number)
                pass
