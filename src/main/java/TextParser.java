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
        KeyMapper.getMainKeyWords(tokens);
        return null;
    }
}
