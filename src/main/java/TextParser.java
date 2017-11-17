import java.util.List;

/**
 * Created by Kyriacos on 17/11/2017.
 */
public class TextParser {

    private String msg;

    public TextParser(String msg) {
        this.msg = msg;
    }

    public List<String> getKeyWords(){
        Tokenizer tk = new Tokenizer(this.msg);
        List<String> tokens = tk.tokenize();
        List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);

        /*Write code to isolate usable keywords
        * for detection of response functionality.*/

        return mainKeyWords;
    }
}
