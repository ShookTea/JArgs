/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jargs;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ShookTea
 */
public class ParseFlagsWithVariablesTest {
    public ParseFlagsWithVariablesTest() {}
        
    @Test
    public void testShortFlagWithVariable() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').setVariableRequired(true).build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-a", "someValue");
        assertTrue(a.isUsed());
        assertEquals("someValue", a.getValue());
        assertFalse(b.isUsed());
        assertFalse(c.isUsed());
    }
    
    @Test
    public void testMoreShortFlagsWithVariable() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').setVariableRequired(true).build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').setVariableRequired(true).build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("-acb", "someValue", "otherValue");
        assertTrue(a.isUsed());
        assertEquals("someValue", a.getValue());
        assertEquals("otherValue", b.getValue());
        assertTrue(b.isUsed());
        assertTrue(c.isUsed());
    }
    
    @Test
    public void testLongFlagWithVariable() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').setLongFlag("test-flag").setVariableRequired(true).build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').setVariableRequired(true).build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--test-flag", "someValue");
        assertTrue(a.isUsed());
        assertEquals("someValue", a.getValue());
        assertFalse(b.isUsed());
        assertFalse(c.isUsed());
    }
    
    @Test
    public void testMoreLongFlagsWithVariable() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').setLongFlag("test-flag").setVariableRequired(true).build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').setLongFlag("other-flag").setVariableRequired(true).build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Parser p = Parser.createParser(a, b, c);
        p.parse("--test-flag", "--other-flag", "someValue", "otherValue");
        assertTrue(a.isUsed());
        assertEquals("someValue", a.getValue());
        assertTrue(b.isUsed());
        assertEquals("otherValue", b.getValue());
        assertFalse(c.isUsed());
    }
    
    @Test
    public void testMixedFlagsWithVariable() {
        Flag a = FlagBuilder.createFlag().setShortFlag('a').setLongFlag("test-flag").setVariableRequired(true).build();
        Flag b = FlagBuilder.createFlag().setShortFlag('b').setLongFlag("other-flag").setVariableRequired(true).build();
        Flag c = FlagBuilder.createFlag().setShortFlag('c').build();
        Flag d = FlagBuilder.createFlag().setShortFlag('d').build();
        Parser p = Parser.createParser(a, b, c, d);
        p.parse("--other-flag", "-ac", "someValue", "otherValue");
        assertTrue(a.isUsed());
        assertEquals("otherValue", a.getValue());
        assertTrue(b.isUsed());
        assertEquals("someValue", b.getValue());
        assertTrue(c.isUsed());
        assertFalse(d.isUsed());
    }
    
}
