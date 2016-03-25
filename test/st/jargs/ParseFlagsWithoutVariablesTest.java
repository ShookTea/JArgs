package st.jargs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ShookTea
 */
public class ParseFlagsWithoutVariablesTest {
    public ParseFlagsWithoutVariablesTest() {}

    @Test
    public void testParseOneLongFlag() {        
        Flag a = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        try {
            p.parse("--other-flag");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertFalse(a.isUsed());
            assertTrue(b.isUsed());
            assertFalse(c.isUsed());
        }
    }

    @Test
    public void testParseMoreShortFlagsConnected() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        try {
            p.parse("-ac");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertTrue(a.isUsed());
            assertFalse(b.isUsed());
            assertTrue(c.isUsed());
        }
    }

    @Test
    public void testParseMoreShortFlags() {       
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        try {
            p.parse("-a", "-b");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertTrue(a.isUsed());
            assertTrue(b.isUsed());
            assertFalse(c.isUsed());
        }
    }

    @Test
    public void testParseOneShortFlag() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Parser p = Parser.createParser(a, b);
        try {
            p.parse("-a");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertTrue(a.isUsed());
            assertFalse(b.isUsed());
        }
    }

    @Test
    public void testParseMoreLongFlags() {        
        Flag a = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        try {
            p.parse("--other-flag", "--c-flag");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertFalse(a.isUsed());
            assertTrue(b.isUsed());
            assertTrue(c.isUsed());
        }
    }

    @Test
    public void testParseFlagsCombined() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        try {
            p.parse("--other-flag", "-ac");
        } catch (WrongArgumentException ex) {
            Logger.getLogger(ParseFlagsWithoutVariablesTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertTrue(a.isUsed());
            assertTrue(b.isUsed());
            assertTrue(c.isUsed());
        }
    }
}
