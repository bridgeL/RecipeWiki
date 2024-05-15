package anu.cookcompass.search;


/**
 * @author u7752874, Xinlei Wen
 * @feature Search
 */

/**
 * The tokenizer for tokenize user search input. Similar to the tokenizer used in the lab.
 */
public class Tokenizer {
    private String buffer;
    private Token currentToken;

    /**
     * Tokenizer constructor. Initialize the tokenizer with search input. Should be called by
     * the search model.
     * @param text The search input from the user.
     */
    public Tokenizer(String text){
        buffer = text;
        next();
    }

    /**
     * Asks the tokenizer to extract the next token. Results will be saved to
     */
    public void next(){
        buffer = buffer.trim();     // remove whitespace at the beginning

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        // tokenizing part
        char firstChar = buffer.charAt(0);
        // bool operators
        if(firstChar == '=')
            currentToken = new Token("=", Token.Type.BOOL_EQ);
        else if (firstChar == '>')
            currentToken =  new Token(">", Token.Type.BOOL_GT);
        else if (firstChar == '<')
            currentToken =  new Token("<", Token.Type.BOOL_LT);
            // semicolon and comma
        else if (firstChar == ';')
            currentToken =  new Token(";", Token.Type.SEMI);
        else if (firstChar == ',')
            currentToken = new Token(",", Token.Type.COMMA);
        // consider everything apart from symbols above as a string
        else{
            StringBuilder result = new StringBuilder();
            int idx = 0;
            while(idx < buffer.length()
                    && (buffer.charAt(idx) != ','
                    && buffer.charAt(idx) != ';'
                    && buffer.charAt(idx) != '>'
                    && buffer.charAt(idx) != '<'
                    && buffer.charAt(idx) != '=')){
                result.append(buffer.charAt(idx));
                idx++;
            }
            currentToken = new Token(result.toString(), Token.Type.STRING);
        }

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    public Token current(){
        return currentToken;
    }

    public boolean hasNext(){
        return currentToken != null;
    }
}
