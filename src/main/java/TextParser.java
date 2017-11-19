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
    private static List<String> wantWords = getWordListFromFile("WordLists/Want_Wordlist.txt");
    private static List<String> burgerWords = getWordListFromFile("WordLists/Burgers_Wordlist.txt");
    private static List<String> sandwichWords = getWordListFromFile("WordLists/Sandwich_Wordlist.txt");
    private static List<String> souvlakiaWords = getWordListFromFile("WordLists/Souvlakia_Wordlist.txt");
    private static List<String> finalKeyWords = new LinkedList<>();


    public TextParser(String msg) {
        this.msg = msg;
    }

    public List<String> getKeyWords(){
        Tokenizer tk = new Tokenizer(this.msg);
        List<String> tokens = tk.tokenize();

        if (tokens.get(0).equals("usr_selection")) {
            tokens.remove(0);
            addIds(tokens);
        } else {
            List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);
            addWords(mainKeyWords, wantWords);
            addWords(mainKeyWords, burgerWords);
            addWords(mainKeyWords, sandwichWords);
            addWords(mainKeyWords, souvlakiaWords);
        }

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

    private static void addIds(List<String> idList) {
        String[] part;
        for (String id : idList) {
            part = id.split("=");
            finalKeyWords.add(part[0]);
            finalKeyWords.add(part[1]);
        }
    }

    private static void addWords(List<String> mainList, List<String> wordList) {
        for (String word : mainList) {
            if (wordList.contains(word.toLowerCase()) && !finalKeyWords.contains(wordList.get(0))) {
                finalKeyWords.add(wordList.get(0));
            }
        }
    }

    public static void main(String args[]) {
        TextParser tp = new TextParser("usr_selection res_ID=123456");
        List<String> keyWords = tp.getKeyWords();
        System.out.println(keyWords);
    }
}
