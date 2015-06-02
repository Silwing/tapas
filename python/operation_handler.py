__author__ = 'silwing'

import core


class OperationHandler(core.Handler):

    def __init__(self, library, finder):
        self.library = library
        self.finder = finder
        self.operations = {}
        self.ignoreList = []

    def get_blacklist(self):
        return {}

    @staticmethod
    def num(num):
        try:
            return int(num)
        except ValueError:
            return 0

    def ignore(self, lineno, file):
        if [lineno, file] in self.ignoreList:
            return True
        else:
            self.ignoreList.append([lineno, file])
            return False

    def handle_line(self, line, current_line):
        if line[0] in ["array_pop", "array_push", "array_shift", "array_unshift"] and self.num(line[3]) >= 4 and not self.ignore(self.num(line[1]), line[2]):
            lines = self.finder.getLines(line[2], int(line[1]))
            pass
        if line[0] in self.operations:
            self.operations[line[0]] += 1
        else:
            self.operations[line[0]] = 1

    def generate_result(self):
        return [self.operations.keys(), self.operations.values()]
