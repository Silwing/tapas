import core

__author__ = 'budde'


class OperationIDHandler(core.Handler):
    def get_blacklist(self):
        return {
        }

    def __init__(self, library):
        super(OperationIDHandler, self).__init__(library)
        self.operations = {}

    def generate_result(self):
        result = []
        for id in self.operations:
            result.append([id] + list(self.operations[id]))
        return result

    def handle_line(self, line, current_line):
        if len(line) < 3:
            return

        line_type = line[0]
        try:
            line_number = int(line[1])
            line_file = line[2]

            if line_type == "array_mr_part":
                return

            if line_type.startswith("array") and len(line) > 6:
                array_ref = line[6]
            elif line_type.startswith("assign_") and len(line) >= 14 and line[8] == "array":
                array_ref = line[12]
            elif (line_type == "count" or line_type.endswith("sort") or line_type == "in_array") and len(line) > 6 and \
                    line[6].startswith("0x"):
                array_ref = line[6]
            else:
                return
        except ValueError:
            return
        id = self.library.generate_id(line_number, line_file, line_type, array_ref)
        if id in self.operations:
            if line_type not in self.operations[id]:
                self.operations[id][line_type] = 0

            self.operations[id][line_type] += 1
        else:
            self.operations[id] = {line_type: 1}