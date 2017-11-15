import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tomis on 15/11/2017.
 */
public class TextParser {
    private String msg;
    private String DEFAULT_DELIMITER = " ";
    public TextParser(String msg) {
        this.msg = msg;
    }

    List<String>[] getKeyWords() {
        this.msg = msg;
        StringTokenizer stk = new StringTokenizer(msg, DEFAULT_DELIMITER);
        return null;
    }
}
