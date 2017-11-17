import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {

    private String msg;
    private String delimiter = " ";

    public Tokenizer(String msg){
        this.msg = msg;
    }

    public Tokenizer(String msg, String delimiter){
        this.msg = msg;
        this.delimiter = delimiter;
    }

    public List<String> tokenize(){
        List<String> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(this.msg);
        while (st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }

        return tokens;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    /*public static void main(String[] args){
        String sentence = "Test this sentence for tokens.";
        Tokenizer tk= new Tokenizer(sentence);

        List<String> sentence_tokens = tk.tokenize();
        for (String token : sentence_tokens) {
            System.out.println(token);
        }
    }*/

}
