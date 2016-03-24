package st.jargs;

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
        Flag a = ElementBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = ElementBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = ElementBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag");
        assertFalse(a.isUsed());
        assertTrue(b.isUsed());
        assertFalse(c.isUsed());
    }

    @Test
    public void testParseMoreShortFlagsConnected() {        
        Flag a = ElementBuilder.createFlag().setShortFlag('a').build();
        Flag b = ElementBuilder.createFlag().setShortFlag('b').build();
        Flag c = ElementBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-ac");
        assertTrue(a.isUsed());
        assertFalse(b.isUsed());
        assertTrue(c.isUsed());
    }

    @Test
    public void testParseMoreShortFlags() {       
        Flag a = ElementBuilder.createFlag().setShortFlag('a').build();
        Flag b = ElementBuilder.createFlag().setShortFlag('b').build();
        Flag c = ElementBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-a", "-b");
        assertTrue(a.isUsed());
        assertTrue(b.isUsed());
        assertFalse(c.isUsed());
    }

    @Test
    public void testParseOneShortFlag() {        
        Flag a = ElementBuilder.createFlag().setShortFlag('a').build();
        Flag b = ElementBuilder.createFlag().setShortFlag('b').build();
        Parser p = Parser.createParser(a, b);
        p.parse("-a");
        assertTrue(a.isUsed());
        assertFalse(b.isUsed());
    }

    @Test
    public void testParseMoreLongFlags() {        
        Flag a = ElementBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = ElementBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = ElementBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag", "--c-flag");
        assertFalse(a.isUsed());
        assertTrue(b.isUsed());
        assertTrue(c.isUsed());
    }

    @Test
    public void testParseFlagsCombined() {        
        Flag a = ElementBuilder.createFlag().setShortFlag('a').build();
        Flag b = ElementBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = ElementBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag", "-ac");
        assertTrue(a.isUsed());
        assertTrue(b.isUsed());
        assertTrue(c.isUsed());
    }
}
