__author__ = 'silwing'

import core


class OperationHandler(core.Handler):

    def __init__(self, library, finder):
        self.library = library
        self.finder = finder
        self.operations = {}

    def get_blacklist(self):
        return {}

    def handle_line(self, line, current_line):
        if line[0] == "array_pop":
            lines = self.finder.getLines(line[2], line[1])
            pass
        if line[0] in self.operations:
            self.operations[line[0]] += 1
        else:
            self.operations[line[0]] = 1

    def generate_result(self):
        return [self.operations.keys(), self.operations.values()]
