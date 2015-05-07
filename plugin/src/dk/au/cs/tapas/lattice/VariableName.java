package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface VariableName {

    String getName();

    static boolean isSuperGlobal(VariableName name){
        return name.getName().matches("^(GLOBALS|(_(POST|GET|SESSION|COOKIE|SERVER|REQUEST|FILES|ENV)))$");
    }

    VariableName POST = new VariableNameImpl("_POST");
    VariableName GET = new VariableNameImpl("_GET");
    VariableName SESSION = new VariableNameImpl("_SESSION");
    VariableName COOKIE = new VariableNameImpl("_COOKIE");
    VariableName SERVER = new VariableNameImpl("_SERVER");
    VariableName REQUEST = new VariableNameImpl("_REQUEST");
    VariableName FILES = new VariableNameImpl("_FILES");
    VariableName ENV = new VariableNameImpl("_ENV");
    VariableName GLOBALS = new VariableNameImpl("GLOBALS");
    VariableName[] superGlobals = new VariableName[]{POST, GET, SESSION, COOKIE, SERVER, REQUEST, FILES, ENV, GLOBALS};


    boolean isSuperGlobal();





}
