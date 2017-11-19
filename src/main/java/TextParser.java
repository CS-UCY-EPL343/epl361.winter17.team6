import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
        List<String> finalKeyWords = new LinkedList<>();

        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream("C:\\Users\\kyria\\Documents\\GitHub\\epl361.winter17.team6\\WordList\\Souvlakia_Wordlist.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened");
            System.exit(0);
        }

        List<String> souvlakiaWords = new LinkedList<>();

        while (inputStream.hasNextLine()) {
            souvlakiaWords.add(inputStream.nextLine());
        }

        inputStream.close();

        for (String word : mainKeyWords) {
            if (souvlakiaWords.contains(word)) {
                finalKeyWords.add(souvlakiaWords.get(0));
            }
        }

        return finalKeyWords;
    }

    public static void main(String args[]) {
        TextParser tp = new TextParser("I want to order souvlou8kia");
        List<String> keyWords = tp.getKeyWords();
        System.out.println(keyWords);
    }
}
