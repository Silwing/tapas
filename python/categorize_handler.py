import core
from operation_id_handler import OperationIDHandler
from type_handler import TypeHandler
from value_handler import ValueHandler

__author__ = 'budde'


class CategorizeHandler(core.Handler):
    def __init__(self, library):
        super(CategorizeHandler, self).__init__(library)
        self.type_handler = TypeHandler(library)
        self.value_handler = ValueHandler(library)
        self.operation_id_handler = OperationIDHandler(library)

    def get_blacklist(self):
        return {}

    def generate_result(self):
        not_type = 0
        not_value = 0
        not_type_value = 0
        objects = [0, 0, 0, 0, 0, 0, 0]
        objects_op = \
            [
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0]
            ]
        maps_and_lists = [0, 0, 0, 0, 0, 0, 0]
        maps_and_lists_op = \
            [
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0]

            ]

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
            type = self.type_handler.array_types[id] - 1
            if len(self.value_handler.array_value_types[id]) > 1:
                objects[type] += 1
                objects_op[type] = self.op_set_to_list(id, objects_op[type], self.operation_id_handler.operations)
            else:
                maps_and_lists[type] += 1
                maps_and_lists_op[type] = self.op_set_to_list(id, maps_and_lists_op[type],
                                                              self.operation_id_handler.operations)
        objects_op = [item for sub_list in objects_op for item in sub_list]
        maps_and_lists_op = [item for sub_list in maps_and_lists_op for item in sub_list]
        return \
            [
                [
                    not_type_value,
                    not_type,
                    not_value
                ] +
                objects +
                maps_and_lists +
                objects_op +
                maps_and_lists_op
            ]

    def handle_line(self, line, current_line):
        self.type_handler.handle_line(line, current_line)
        self.value_handler.handle_line(line, current_line)
        self.operation_id_handler.handle_line(line, current_line)

    @staticmethod
    def op_set_to_list(id, orig_list, set_list):
        if id not in set_list:
            return orig_list
        set = set_list[id]
        if "array_write" in set:
            orig_list[0] += set["array_write"]
        if "array_push" in set:
            orig_list[1] += set["array_push"]
        if "array_pop" in set:
            orig_list[2] += set["array_pop"]
        if "array_shift" in set:
            orig_list[3] += set["array_shift"]
        if "array_unshift" in set:
            orig_list[4] += set["array_unshift"]
        if "array_append" in set:
            orig_list[5] += set["array_append"]
        return orig_list
