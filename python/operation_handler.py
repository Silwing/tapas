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
        return self.operations.values()

class OperationNamesHandler(core.Handler):
    def __init__(self, library):
        self.library = library
        self.names = []

    def get_blacklist(self):
        return {}

    def handle_line(self, line, current_line):
        if line[0] not in self.names:
            self.names.append(line[0])

    def generate_result(self):
        return self.names