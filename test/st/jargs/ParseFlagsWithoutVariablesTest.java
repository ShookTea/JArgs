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
        Flag a = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag");
        assertFalse(a.isUsed());
        assertTrue(b.isUsed());
        assertFalse(c.isUsed());
    }

    @Test
    public void testParseMoreShortFlagsConnected() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-ac");
        assertTrue(a.isUsed());
        assertFalse(b.isUsed());
        assertTrue(c.isUsed());
    }

    @Test
    public void testParseMoreShortFlags() {       
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-a", "-b");
        assertTrue(a.isUsed());
        assertTrue(b.isUsed());
        assertFalse(c.isUsed());
    }

    @Test
    public void testParseOneShortFlag() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Parser p = Parser.createParser(a, b);
        p.parse("-a");
        assertTrue(a.isUsed());
        assertFalse(b.isUsed());
    }

    @Test
    public void testParseMoreLongFlags() {        
        Flag a = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setLongFlag("c-flag").build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag", "--c-flag");
        assertFalse(a.isUsed());
        assertTrue(b.isUsed());
        assertTrue(c.isUsed());
    }

    @Test
    public void testParseFlagsCombined() {        
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Flag b = FlagBuilder.createFlag().setLongFlag("other-flag").build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--other-flag", "-ac");
        assertTrue(a.isUsed());
        assertTrue(b.isUsed());
        assertTrue(c.isUsed());
    }
    
    @Test
    public void testParseFlagsWithBugs() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').build();
        Parser p = Parser.createParser(a);
        p.parse("-ab");
        assertTrue(a.isUsed());
    }
}
