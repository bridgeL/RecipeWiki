package anu.cookcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import anu.cookcompass.search.Token;
import anu.cookcompass.search.Tokenizer;


public class TokenizerTest {

    @Test
    public void testInitialization() {
        Tokenizer tokenizer = new Tokenizer("test");
        assertNotNull(tokenizer.current());
        assertEquals(new Token("test", Token.Type.STRING), tokenizer.current());
    }

    @Test
    public void testNextToken() {
        Tokenizer tokenizer = new Tokenizer("test1, test2; test3 > test4 < test5 = test6");

        assertEquals(new Token("test1", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token(",", Token.Type.COMMA), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test2", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token(";", Token.Type.SEMI), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test3", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token(">", Token.Type.BOOL_GT), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test4", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("<", Token.Type.BOOL_LT), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test5", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("=", Token.Type.BOOL_EQ), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test6", Token.Type.STRING), tokenizer.current());
    }

    @Test
    public void testHasNext() {
        Tokenizer tokenizer = new Tokenizer("test1, test2");

        assertTrue(tokenizer.hasNext());
        tokenizer.next();
        assertTrue(tokenizer.hasNext());
        tokenizer.next();
        assertTrue(tokenizer.hasNext());
        tokenizer.next();
        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testEmptyInput() {
        Tokenizer tokenizer = new Tokenizer("");
        assertFalse(tokenizer.hasNext());
        assertNull(tokenizer.current());
    }

    @Test
    public void testWhitespaceHandling() {
        Tokenizer tokenizer = new Tokenizer("   test1   ,   test2   ;   test3   ");

        assertEquals(new Token("test1", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token(",", Token.Type.COMMA), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test2", Token.Type.STRING), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token(";", Token.Type.SEMI), tokenizer.current());
        tokenizer.next();
        assertEquals(new Token("test3", Token.Type.STRING), tokenizer.current());
    }
}
