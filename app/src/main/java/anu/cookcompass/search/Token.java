package anu.cookcompass.search;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * @author u7752874, Xinlei Wen
 * @feature Search
 */

/**
 * Token class to save extracted token from tokenizer.
 * Each token has its surface form saved in {@code token}
 * and type saved in {@code type} which is one of the predefined type in Type enum.
 * The following are the different types of tokens:
 * INGREDIENTS: ingredients
 * TITLE: title
 * LIKE: like
 * VIEW: view
 * BOOL_GT: >
 * BOOL_EQ: =
 * BOOL_LT: =
 * COMMA: ,
 * SEMI: ;
 * STRING: string
 * INT: integer
 */
public class Token {
    public enum Type{BOOL_GT, BOOL_EQ, BOOL_LT, COMMA, SEMI, STRING}

    /**
     * If a tokenizer attempts to tokenize something that is not of one of the types of tokens,
     * IllegamTokenException will be thrown.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        if(type == Type.STRING){
            return "STRING(" + token + ")";
        }
        else {
            return type + "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof Token)) return false; // Null or not the same type.
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken()); // Values are the same.
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }
}
