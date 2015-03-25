__author__ = 'silwing'

import core


class OperationHandler(core.Handler):

    def __init__(self, library):
        self.library = library
        self.operations = {}

    def get_blacklist(self):
        return {}

    def handle_line(self, line, current_line):
        if line[0] in self.operations:
            self.operations[line[0]] += 1
        else:
            self.operations[line[0]] = 1

    def generate_result(self):
        return [self.operations.keys(), self.operations.values()]
