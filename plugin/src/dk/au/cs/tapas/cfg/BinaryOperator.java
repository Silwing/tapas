package dk.au.cs.tapas.cfg;

/**
 * Created by budde on 4/29/15.
 */
public enum BinaryOperator {

    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    MODULO,
    EXPONENTIATION,
    EQUAL,
    IDENTICAL,
    NOT_EQUAL,
    NOT_IDENTICAL,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_OR_EQ,
    LESS_THAN_OR_EQ,
    AND,
    OR,
    XOR,
    CONCATENATION;

    @Override
    public String toString() {
        switch (this) {
            case ADDITION:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            case MODULO:
                return "%";
            case EXPONENTIATION:
                return "**";
            case EQUAL:
                return "==";
            case IDENTICAL:
                return "===";
            case NOT_EQUAL:
                return "!=";
            case NOT_IDENTICAL:
                return "!==";
            case LESS_THAN:
                return "<";
            case GREATER_THAN:
                return ">";
            case GREATER_THAN_OR_EQ:
                return ">=";
            case LESS_THAN_OR_EQ:
                return "<=";
            case AND:
                return "&&";
            case XOR:
                return "xor";
            case OR:
                return "||";
            case CONCATENATION:
                return ".";
        }
        return "";
    }

    public static BinaryOperator fromString(String string) {
        switch (string.toLowerCase()) {
            case "+":
                return ADDITION;
            case "-":
                return SUBTRACTION;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            case "%":
                return MODULO;
            case "**":
                return EXPONENTIATION;
            case "==":
                return EQUAL;
            case "===":
                return IDENTICAL;
            case "!=":
                return NOT_EQUAL;
            case "!==":
                return NOT_IDENTICAL;
            case "<":
                return LESS_THAN;
            case ">":
                return GREATER_THAN;
            case ">=":
                return GREATER_THAN_OR_EQ;
            case "<=":
                return LESS_THAN_OR_EQ;
            case "&&":
            case "and":
                return AND;
            case "xor":
                return XOR;
            case "||":
            case "or":
                return OR;
            case ".":
                return CONCATENATION;
        }
        throw new UnsupportedOperationException();
    }
}
