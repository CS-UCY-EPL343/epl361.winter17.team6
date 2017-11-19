import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Kyriacos on 17/11/2017.
 */
public class TextParser {

    private String msg;
    private static List<String> finalKeyWords = new LinkedList<>();

    public TextParser(String msg) {
        this.msg = msg;
    }

    public List<String> getKeyWords(){
        Tokenizer tk = new Tokenizer(this.msg);
        List<String> tokens = tk.tokenize();
        List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);

        List<String> souvlakiaWords = getWordListFromFile("WordLists/Souvlakia_Wordlist.txt");
        List<String> wantWords = getWordListFromFile("WordLists/Want_Wordlist.txt");

        addWords(mainKeyWords, souvlakiaWords);
        addWords(mainKeyWords, wantWords);

        return finalKeyWords;
    }

    private static List<String> getWordListFromFile(String filePath) {
        String words = FileParser.getFileContentAsString(filePath);
        String[] wordList = words.split("\n");
        List<String> outList = new LinkedList<>();
        for (int i=0; i<wordList.length; i++) {
            outList.add(wordList[i]);
        }
        return outList;
    }

    private static void addWords(List<String> mainList, List<String> wordList) {
        for (String word : mainList) {
            if (wordList.contains(word.toLowerCase())) {
                finalKeyWords.add(wordList.get(0));
            }
        }
    }

    public static void main(String args[]) {
        TextParser tp = new TextParser("I need to order Souvlou8kia");
        List<String> keyWords = tp.getKeyWords();
        System.out.println(keyWords);
    }
}
