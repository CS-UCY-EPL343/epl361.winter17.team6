import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class Tokenizer is a class that uses a StringTokenizer object to isolate all the words from the user's message.
 *
 * A Tokenizer object has a String field that is the user's message, called msg and another String field that
 * is the delimiter for the StringTokenizer object which is set to the default space character. The default operation
 * of a Tokenizer object is to return a List containing all the words from the user's message.
 *
 * @author Kyriacos Aristides	ID: 965191
 * @version 1.0
 * @since 21/11/17
 */
public class Tokenizer {

    private String msg;
    private String delimiter = " ";

    /**
     * This is the default object constructor.
     *
     * It assigns the user message to the field msg.
     *
     * @param msg The user's message.
     */
    public Tokenizer(String msg){
        this.msg = msg;
    }

    /**
     * This is the secondary object constructor.
     *
     * It assigns the user message to the field msg and a different delimeter.
     *
     * @param msg The user's message.
     * @param delimiter Different delimiter for StringTokenizer.
     */
    public Tokenizer(String msg, String delimiter){
        this.msg = msg;
        this.delimiter = delimiter;
    }

    /**
     * This method returns a List containing all the words from the user's message.
     *
     * @return List object containing all the words from the user's message.
     */
    public List<String> tokenize(){
        List<String> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(this.msg);
        while (st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }

        return tokens;
    }

    /**
     * This method sets the value of the msg field.
     *
     * The value of the msg field is set with the given user's message.
     *
     * @param msg The user's message.
     */
    public void setMsg(String msg){
        this.msg = msg;
    }

    /**
     * This method sets the value of the delimiter field.
     *
     * @param delimiter The new delimiter.
     */
    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    public static void main(String[] args){
        String sentence = "Test this sentence for tokens.";
        Tokenizer tk= new Tokenizer(sentence);

        List<String> sentence_tokens = tk.tokenize();
        for (String token : sentence_tokens) {
            System.out.println(token);
        }
    }

}
