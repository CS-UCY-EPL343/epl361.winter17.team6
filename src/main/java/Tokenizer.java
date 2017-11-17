import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {


    private String delimiter = " ";

    public List<String> tokenize(String s) {
        List<String> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        return tokens;
    }

    public void setDelimiter(String s) {
        this.delimiter = s;
    }

}
