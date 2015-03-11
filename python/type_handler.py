#!/usr/bin/env python

__author__ = 'Randi Katrine Hillerøe'
__license__ = "Apache 2.0"
__version__ = "1.0.1"
__email__ = "silwing@gmail.com"

import core

class TypeHandler(Handler):
	def __init__(self):
		self.array_types = {};

    def generate_result(self):
        return self.array_types;

    def handle_line(self, line):
        if len(line) < 3:
            return

        line_type = line[0]
        line_number = line[1]
        line_file = line[2]


        if line_type.startswith("array") and len(line) > 6 :
            array_ref = line[6]
            id = self.library.generate_id(line_number, line_file, array_ref)
			self.array_types[id] &= 1<<line[3];
