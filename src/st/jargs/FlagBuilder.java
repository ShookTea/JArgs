package st.jargs;

/**
 *
 * @author ShookTea
 */
class FlagBuilder extends ElementBuilder {
    protected FlagBuilder() {
        this.longFlag = Element.NO_LONG_FLAG;
        this.shortFlag = Element.NO_SHORT_FLAG;
        this.variableRequired = false;
    }
    
    public FlagBuilder setLongFlag(String longFlag) {
        this.longFlag = longFlag;
        return this;
    }
    
    public FlagBuilder setShortFlag(char shortFlag) {
        this.shortFlag = shortFlag;
        return this;
    }
    
    public FlagBuilder setVariableRequired(boolean variableRequired) {
        this.variableRequired = variableRequired;
        return this;
    }
    
    public Flag build() {
        return new Flag(shortFlag, longFlag, variableRequired);
    }
    
    public static FlagBuilder createFlag() {
        return new FlagBuilder();
    }

    private String longFlag;
    private char shortFlag;
    private boolean variableRequired;
}
