package st.jargs;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ShookTea
 */
public class ParseVariablesTest {
    public ParseVariablesTest() {}
    
    @Test
    public void testParseOneVariable() {
        Variable a = new Variable();
        Variable b = new Variable();
        Parser p = Parser.createParser(a, b);
        p.parse("test");
        assertTrue(a.isUsed());
        assertEquals("test", a.getValue());
        assertFalse(b.isUsed());
    }
    
    @Test
    public void testParseMoreVariables() {
        Variable a = new Variable();
        Variable b = new Variable();
        Variable c = new Variable();
        Parser p = Parser.createParser(a, b, c);
        p.parse("testA", "testB");
        assertTrue(a.isUsed());
        assertEquals("testA", a.getValue());
        assertTrue(b.isUsed());
        assertEquals("testB", b.getValue());
        assertFalse(c.isUsed());
    }
    
    @Test
    public void testVariablesWithFlags() {
        Variable a = new Variable();
        Variable b = new Variable();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Flag d = FlagBuilder.createFlag().setShortFlag('d').build();
        Flag e = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Parser.createParser(a, b, c, d, e).parse("testA", "-cd", "testB", "--test-flag");
        assertTrue(a.isUsed());
        assertEquals("testA", a.getValue());
        assertTrue(b.isUsed());
        assertEquals("testB", b.getValue());
        assertTrue(c.isUsed());
        assertTrue(d.isUsed());
        assertTrue(e.isUsed());
    }
    
    @Test
    public void testVariablesWithVariableFlags() {
        Variable a = new Variable();
        Variable b = new Variable();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').setVariableRequired(true).build();
        Flag d = FlagBuilder.createFlag().setShortFlag('d').build();
        Flag e = FlagBuilder.createFlag().setLongFlag("test-flag").build();
        Parser.createParser(a, b, c, d, e).parse("testA", "-cd", "testB", "--test-flag");
        assertTrue(a.isUsed());
        assertEquals("testA", a.getValue());
        assertFalse(b.isUsed());
        assertTrue(c.isUsed());
        assertEquals("testB", c.getValue());
        assertTrue(d.isUsed());
        assertTrue(e.isUsed());
    }
}
