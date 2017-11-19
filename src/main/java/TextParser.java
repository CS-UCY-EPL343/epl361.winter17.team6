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
    private static List<String> finalKeyWords = new LinkedList<>();

    public TextParser(String msg) {
        this.msg = msg;
    }

    public List<String> getKeyWords(){
        Tokenizer tk = new Tokenizer(this.msg);
        List<String> tokens = tk.tokenize();
        List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);

        Scanner souvlakiaInputStream = null;
        Scanner wantInputStream = null;

        List<String> souvlakiaWords = new LinkedList<>();
        List<String> wantWords = new LinkedList<>();

        souvlakiaWords = getWordListFromFile(souvlakiaInputStream, "Souvlakia_Wordlist.txt");
        wantWords = getWordListFromFile(wantInputStream, "Want_Wordlist.txt");

        addWords(mainKeyWords, souvlakiaWords);
        addWords(mainKeyWords, wantWords);

        /*for (String word : mainKeyWords) {
            if (souvlakiaWords.contains(word.toLowerCase())) {
                finalKeyWords.add(souvlakiaWords.get(0));
            }
            if (wantWords.contains(word.toLowerCase())) {
                finalKeyWords.add(wantWords.get(0));
            }
        }*/

        return finalKeyWords;
    }

    private static List<String> getWordListFromFile(Scanner inputStream, String fileName) {
        try {
            inputStream = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened");
            System.exit(0);
        }

        List<String> wordList = new LinkedList<>();
        while (inputStream.hasNextLine()) {
            wordList.add(inputStream.nextLine());
        }

        inputStream.close();
        return  wordList;
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
