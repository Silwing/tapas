import core

__author__ = 'budde'


class OperationIDHandler(core.Handler):
    def get_blacklist(self):
        return {
        }

    def __init__(self, library):
        super(OperationIDHandler, self).__init__(library)

    def generate_result(self):
        return []

    def handle_line(self, line, current_line):
        pass