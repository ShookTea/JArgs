package st.jargs;

/**
 *
 * @author ShookTea
 */
public class WrongArgumentException extends Exception {
    public WrongArgumentException(String message, Throwable src) {
        super(message, src);
    }
}
