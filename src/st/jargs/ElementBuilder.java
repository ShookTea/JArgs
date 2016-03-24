package st.jargs;

/**
 *
 * @author ShookTea
 */
public abstract class ElementBuilder {
    /**
     * @deprecated  
     */
    public static FlagBuilder createFlag() {
       return new FlagBuilder();
    }
}
