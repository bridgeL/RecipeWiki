package anu.cookcompass.TokenizerAndParser;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

/**
 * A parser for parsing the result of a query from the user input to a search tree. <br>
 * The grammar is shown as follow: <br>
 * < Query > :== < Ingr-Query > < Titl-Query > < Like-Query > < Collect-Query > <br>
 * < Ingr-Query > :== ingredients = < Names >; | EMPTY <br>
 * < Titl-Query > :== title = < Names >; | EMPTY<br>
 * < Like-Query > :== like < BoolOperator > INTEGER ; | EMPTY<br>
 * < Collect-Query > :== collect < BoolOperator > INTEGER ; | EMPTY<br>
 * < Names > :== STRING | STRING , < Names ><br>
 * < BoolOperator > :==  > | <  | = <br>
 * <br>
 * IMPORTANT: in this grammar, we don't allow any query (e.g., < Reci-Query >) to appear more than once.
 */
public class Parser {
    /**
     * Exception to be thrown when the query read from user input is invalid.
     */
    public static class IllegalQueryException extends IllegalArgumentException{
        public IllegalQueryException(String message) {super(message);}
    }

    Tokenizer tokenizer;
    // boolean variables that states which part has already been read.
    boolean includes_ingr, includes_titl, includes_like, includes_collect;
    int currentProgress = 0;    // marks which keyword the parser is now in

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        includes_ingr = false;
        includes_titl = false;
        includes_like = false;
        includes_collect = false;
    }

    /**
     * Parse the given search input into a query object for search module.
     * @return The query object. Will be a query object with invalid flag set to true
     * if the given query input is invalid.
     */
    public QueryObject parseQuery(){
        if(!tokenizer.hasNext()){
            return new QueryObject("Invalid search: empty search input.");
        }
        QueryObject result = new QueryObject();
        while(tokenizer.hasNext()){
            // switch the token type
            switch(tokenizer.current().getType()){
                case INGREDIENTS -> {
                    // no duplicated query keyword allowed
                    if(includes_ingr)
                        return new QueryObject("Invalid search: Keyword \"ingredients\" duplicated.");
                    else if(includes_titl || includes_like || includes_collect)
                        return new QueryObject("Invalid search: Incorrect position for keyword \"ingredients\".");
                    includes_ingr = true;
                    // check format correctness
                    tokenizer.next();
                    if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.BOOL_EQ)
                        return new QueryObject("Invalid search: Keyword \"ingredients\" not followed by \"=\".");
                    tokenizer.next();
                    // start parsing
                    ArrayList<String> ingr_values = parseValues();
                    if(ingr_values.size() == 0)
                        return new QueryObject("Invalid search: invalid value list for keyword \"ingredients\" or missing semicolon at the end.");
                    else {
                        String[] array = new String[ingr_values.size()];
                        ingr_values.toArray(array);
                        result.ingredient_keywords = array;
                    }
                }
                case TITLE -> {
                    // no duplicated query keyword allowed
                    if(includes_titl)
                        return new QueryObject("Invalid search: Keyword \"title\" duplicated.");
                    else if(includes_like || includes_collect)
                        return new QueryObject("Invalid search: Incorrect position for keyword \"title\".");
                    includes_titl = true;
                    // check format correctness
                    tokenizer.next();
                    if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.BOOL_EQ)
                        return new QueryObject("Invalid search: Keyword \"title\" not followed by \"=\".");
                    tokenizer.next();
                    // start parsing
                    ArrayList<String> titl_values = parseValues();
                    if(titl_values.size() == 0)
                        return new QueryObject("Invalid search: invalid value list for keyword \"title\" or missing semicolon at the end.");
                    else {
                        String[] array = new String[titl_values.size()];
                        titl_values.toArray(array);
                        result.title_keywords = array;
                    }
                }
                case LIKE -> {
                    // no duplicated query keyword allowed
                    if(includes_like)
                        return new QueryObject("Invalid search: Keyword \"like\" duplicated.");
                    else if(includes_collect)
                        return new QueryObject("Invalid search: Incorrect position for keyword \"like\".");
                    includes_like = true;
                    tokenizer.next();
                    // parse
                    ArrayList<Integer> like_range = parseStat();
                    if(like_range.size() == 0){
                        return new QueryObject("Invalid search: invalid range for keyword \"like\" or missing semicolon at the end.");
                    }
                    else{
                        Integer[] array = new Integer[like_range.size()];
                        like_range.toArray(array);
                        result.like_range = ArrayUtils.toPrimitive(array);
                    }
                }
                case COLLECT -> {
                    // no duplicated query keyword allowed
                    if(includes_collect)
                        return new QueryObject("Invalid search: Keyword \"collect\" duplicated.");
                    includes_collect = true;
                    tokenizer.next();
                    // parse
                    ArrayList<Integer> collect_range = parseStat();
                    if(collect_range.size() == 0){
                        return new QueryObject("Invalid search: invalid range for keyword \"collect\" or missing semicolon at the end.");
                    }
                    else{
                        Integer[] array = new Integer[collect_range.size()];
                        collect_range.toArray(array);
                        result.collect_range = ArrayUtils.toPrimitive(array);
                    }
                }
                default ->{
                    return new QueryObject("Invalid search: unexpected keyword.");
                }
            }
        }

        return result;
    }

    /**
     * Parses the values following recipe-type keyword (ingredients and title).
     * @return A list of {@code String} that contains the parsing result. Will be empty if the
     * given values are invalid.
     */
    ArrayList<String> parseValues(){
        // the beginning of a query must be a string
        if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.STRING)
            return new ArrayList<>();

        ArrayList<String> result = new ArrayList<>();
        while(tokenizer.hasNext() && tokenizer.current().getType() != Token.Type.SEMI){
            if(tokenizer.current().getType() == Token.Type.COMMA){
                tokenizer.next();
                // no comma at the end of the query is allowed
                if(!tokenizer.hasNext()){
                    return (new ArrayList<String>());
                }
                // no adjacent comma or semi is allowed after one comma
                else if(tokenizer.current().getType() == Token.Type.COMMA
                        || tokenizer.current().getType() == Token.Type.SEMI){
                    return (new ArrayList<String>());
                }
            }
            else if(tokenizer.current().getType() == Token.Type.STRING){
                // add the string into the result
                result.add(tokenizer.current().getToken());
                tokenizer.next();
            }
            else{
                return (new ArrayList<String>());
            }
        }
        // move to the next token if there is still tokens left
        if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.SEMI)
            return new ArrayList<>();   // if the query does not end with a semi, it is a wrong expression
        else
            tokenizer.next();
        return result;
    }

    ArrayList<Integer> parseStat(){
        // if the value list is empty or first token is not a boolean operator, this part is invalid.
        if(!tokenizer.hasNext()
            || (tokenizer.current().getType() != Token.Type.BOOL_EQ
            &&tokenizer.current().getType() != Token.Type.BOOL_GT
            &&tokenizer.current().getType() != Token.Type.BOOL_LT) ){
            return new ArrayList<>();
        }
        Token.Type operator = tokenizer.current().getType();
        ArrayList<Integer> result = new ArrayList<>();
        tokenizer.next();
        // if no value is following the operator, or the next token is not an integer, this part is invalid.
        if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.INT){
            return new ArrayList<>();
        }
        int range = Integer.parseInt(tokenizer.current().getToken());
        // format and return the result
        if(operator == Token.Type.BOOL_EQ){
            result.add(range);
            result.add(range);
        }
        else if(operator == Token.Type.BOOL_LT){
            result.add(0);
            result.add(range);
        } else if(operator == Token.Type.BOOL_GT) {
            result.add(range);
            result.add(-1);
        }
        else{
            result.add(-1);
            result.add(-1); // usually unreachable
        }
        tokenizer.next();
        // move to the next token if there is still tokens left
        if(!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.SEMI)
            return new ArrayList<>();   // if the query does not end with a semi, it is a wrong expression
        else
            tokenizer.next();
        return result;
    }

//    ArrayList<String> parseTitle(){
//        ArrayList<String> result = new ArrayList<>();
//        while(tokenizer.hasNext() && tokenizer.current().getType() != Token.Type.SEMI){
//            // go to the next value if we meet a comma
//            if(tokenizer.current().getType() == Token.Type.COMMA){
//                tokenizer.next();
//                // no adjacent comma or semi is allowed after one comma
//                if(tokenizer.current().getType() == Token.Type.COMMA
//                        || tokenizer.current().getType() == Token.Type.SEMI){
//                    return (new ArrayList<String>());
//                }
//            }
//            else if(tokenizer.current().getType() == Token.Type.STRING){
//                // add string to the result arraylist
//                result.add(tokenizer.current().getToken());
//                tokenizer.next();
//            }
//            else{
//                return (new ArrayList<>());
//            }
//        }
//        // move to the next token if there is still tokens left
//        if(tokenizer.hasNext()) tokenizer.next();
//        return result;
//    }
}
