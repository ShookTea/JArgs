package st.jargs;

import java.util.Arrays;

/**
 *
 * @author ShookTea
 */
public class Parser {
    public Parser() {
        elements = new ElementsPack();
    }
    
    public void insertElements(Element... elem) {
        elements.insertElements(elem);
    }
    
    public Variable newVariable() {
        Variable v = new Variable();
        elements.insertElements(v);
        return v;
    }
    
    public FlagBuilder newFlag() {
        return new FlagBuilder(this);
    }
    
    public void parse(String... args) throws ParserException {
        try {
            arguments = createArgumentsList(args);
            tryParse();
        } catch (ParserException ex) {
            throw new ParserException("Exception while parsing arguments " + Arrays.toString(args), ex);
        }
    }
    
    private Argument[] createArgumentsList(String[] arguments) {
        Argument[] ret = new Argument[arguments.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new Argument(arguments[i]);
        }
        return ret;
    }
    
    private void tryParse() throws ParserException {
        Argument arg;
        while ((arg = getNextUnusedArgument()) != null) {
            parseNextArgument(arg);
            arg.use();
        }
    }
    
    private Argument getNextUnusedArgument() {
        return this.getNextUnusedArgument(false);
    }
    
    private Argument getNextUnusedArgument(boolean valueOnly) {
        Argument ret = null;
        for (Argument each : arguments) {
            if (!each.isUsed() && (!valueOnly || (!each.isLongFlag() && !each.isShortFlag()))) {
                ret = each;
                break;
            }
        }
        return ret;
    }
    
    private void parseNextArgument(Argument arg) throws ParserException {
        if (arg.isLongFlag()) {
            Flag flag = parseLongFlag(arg);
            if (flag.isValueRequired()) {
                Argument value = getNextUnusedArgument(true);
                flag.use(value.getArgument());
                value.use();
            }
            else {
                flag.use();
            }
        }
        else if (arg.isShortFlag()) {
            Flag[] flags = parseShortFlags(arg);
            for (Flag f : flags) {
                if (f.isValueRequired()) {
                    Argument value = getNextUnusedArgument(true);
                    f.use(value.getArgument());
                    value.use();
                }
                else {
                    f.use();
                }
            }
        }
        else {
            Variable v = parseVariable(arg);
            v.use(arg.getArgument());
        }
    }

    private Flag parseLongFlag(Argument argument) throws ParserException {
        String flagArgument = argument.getArgument().substring(2).trim();
        Flag[] flagsList = elements.getAllFlags();
        for (Flag each : flagsList) {
            if (checkLongFlag(flagArgument, each)) return each;
        }
        throw new ParserException("Correct flag for --" + flagArgument + " not found");
    }
    
    private boolean checkLongFlag(String flagArgument, Flag flag) {
        if (!flag.isLongFlagSet()) return false;
        return flag.getLongFlag().equals(flagArgument);
    }
    
    private Flag[] parseShortFlags(Argument argument) throws ParserException {
        String flagsArgument = argument.getArgument().substring(1).trim();
        Flag[] ret = new Flag[flagsArgument.length()];
        for (int i = 0; i < ret.length; i++) {
            char each = flagsArgument.charAt(i);
            ret[i] = parseShortFlag(each);
        }
        return ret;
    }
    
    private Flag parseShortFlag(char argument) throws ParserException {
        Flag[] all = elements.getAllFlags();
        for (Flag each : all) {
            if (checkShortFlag(argument, each)) return each;
        }
        throw new ParserException("Correct flag for -" + argument + " not found");
    }
    
    private boolean checkShortFlag(char flagArgument, Flag flag) {
        if (!flag.isShortFlagSet()) return false;
        return flag.getShortFlag() == flagArgument;
    }
    
    private Variable parseVariable(Argument arg) throws ParserException {
        Variable[] all = elements.getAllVariables();
        for (Variable each : all) {
            if (checkVariable(arg, each)) return each;
        }
        throw new ParserException("Variable not found for argument " + arg.getArgument());
    }
    
    private boolean checkVariable(Argument arg, Variable var) {
        return !var.isUsed();
    }
    
    private final ElementsPack elements;
    private Argument[] arguments;
    
    public static Parser createParser(Element... elems) {
        Parser p = new Parser();
        p.insertElements(elems);
        return p;
    }
}
