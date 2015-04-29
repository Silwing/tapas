package dk.au.cs.tapas.cfg;

/**
 * Created by budde on 4/29/15.
 */
public enum UnaryOperator {
    NEGATION,
    PRE_INCREMENT,
    POST_INCREMENT,
    PRE_DECREMENT,
    POST_DECREMENT;

    @Override
    public String toString() {
        switch (this) {
            case NEGATION:
                return "!_";
            case PRE_INCREMENT:
                return "++_";
            case POST_INCREMENT:
                return "_++";
            case PRE_DECREMENT:
                return "--_";
            case POST_DECREMENT:
                return "_--";
        }
        return "";
    }

    public static UnaryOperator fromString(String string) {
        switch (string) {
            case "!_":
                return NEGATION;
            case "++_":
                return PRE_INCREMENT;
            case "_++":
                return POST_INCREMENT;
            case "--_":
                return PRE_DECREMENT;
            case "_--":
                return POST_DECREMENT;
        }
        throw new UnsupportedOperationException();
    }

}
