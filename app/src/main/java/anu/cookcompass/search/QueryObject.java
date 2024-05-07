package anu.cookcompass.search;

/**
 * The object that stores a recipe query. This class is the output of the search parser, which
 * delivers the information in the user input to search module.
 */
public class QueryObject {
    public String[] title_keywords;
    public String[] ingredient_keywords;
    public int[] like_range;
    public int[] collect_range;
    public boolean queryInvalid;
    public String errorMessage;

//    /**
//     * Parameterized constructor. Constructs a query object with every search details
//     */
//    public QueryObject(String[] title_kw, String[] ingredient_kw, int[] like_r, int[] collect_r){
//        title_keywords = title_kw;
//        ingredient_keywords = ingredient_kw;
//        like_range = like_r;
//        collect_range = collect_r;
//        queryInvalid = false;
//        errorMessage = "";
//    }

    /**
     * Parameterized constructor that constructs an invalid query object with error message
     * indicating why the query is invalid.
     * @param err_msg The error message
     */
    public QueryObject(String err_msg){
        queryInvalid = true;
        errorMessage = err_msg;

        title_keywords = new String[]{};
        ingredient_keywords = new String[]{};
        like_range = new int[]{-1, -1};
        collect_range = new int[]{-1, -1};
    }

    /**
     * Default constructor. Constructs a query object that indicates a valid query object with
     * empty fields to be filled
     */
    public QueryObject(){
        queryInvalid = false;

        title_keywords = new String[]{};
        ingredient_keywords = new String[]{};
        like_range = new int[]{-1, -1};
        collect_range = new int[]{-1, -1};
    }
}
