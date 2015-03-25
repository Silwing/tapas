import core
from type_handler import TypeHandler
from value_handler import ValueHandler

__author__ = 'budde'


class CategorizeHandler(core.Handler):
    def __init__(self, library):
        super(CategorizeHandler, self).__init__(library)
        self.type_handler = TypeHandler(library)
        self.value_handler = ValueHandler(library)

    def get_blacklist(self):
        return {}

    def generate_result(self):
        not_type = 0
        not_value = 0
        not_type_value = 0
        objects = [0, 0, 0, 0, 0, 0, 0, 0]
        maps_and_lists = [0, 0, 0, 0, 0, 0, 0, 0]

        for id in range(0, self.library.number_of_arrays()):
            if id not in self.type_handler.array_types and id not in self.value_handler.array_value_types:
                not_type_value += 1
                continue
            if id not in self.type_handler.array_types:
                not_type += 1
                continue
            if id not in self.value_handler.array_value_types:
                not_value += 1
                continue

            if len(self.value_handler.array_value_types[id]) > 1:
                objects[0] += 1
                objects[self.type_handler.array_types[id]] += 1
            else:
                maps_and_lists[0] += 1
                maps_and_lists[self.type_handler.array_types[id]] += 1

        return [[not_type_value, not_type, not_value] + objects + maps_and_lists]


    def handle_line(self, line, current_line):
        self.type_handler.handle_line(line, current_line)
        self.value_handler.handle_line(line, current_line)
