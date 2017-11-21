import java.util.LinkedList;
import java.util.List;

/**
 * Class TextParser is a class that creates TextParser objects that handle the analysis of messages.
 *
 * A TextParser object has a String attribute called msg which is the user message or selection. It
 * also has five static Lists that contain all the variations of possible words that the user might
 * input and another static list which will contain the words that will be sent over the App class
 * and will define the proper response function to be called.
 *
 * @author Kyriacos Aristides	ID: 965191
 * @version 2.0
 * @since 21/11/17
 */
public class TextParser {

    private String msg;
    private static List<String> wantWords = getWordListFromFile("WordLists/Want_Wordlist.txt");
    private static List<String> burgerWords = getWordListFromFile("WordLists/Burgers_Wordlist.txt");
    private static List<String> sandwichWords = getWordListFromFile("WordLists/Sandwich_Wordlist.txt");
    private static List<String> souvlakiaWords = getWordListFromFile("WordLists/Souvlakia_Wordlist.txt");
    private static List<String> helpWords = getWordListFromFile("WordLists/Help_Wordlist.txt");
    private static List<String> finalKeyWords = new LinkedList<>();

    /**
     * This is the default object constructor.
     *
     * It assigns the user message to the attribute and empties the keywords list.
     *
     * @param msg The first operand Constant object.
     */
    public TextParser(String msg) {
        this.msg = msg;
        finalKeyWords.clear();
    }

    /**
     * This method returns a List containing the main keywords that will determine the response funcionality.
     *
     * @return List object containing the final keywords.
     */
    public List<String> getKeyWords(){
        Tokenizer tk = new Tokenizer(this.msg);
        List<String> tokens = tk.tokenize();

        if (tokens.get(0).equals("usr_selection")) {
            tokens.remove(0);
            addIds(tokens);
        } else {
            List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);
            addWords(mainKeyWords, helpWords);
            if (!mainKeyWords.contains("help")) {
                addWords(mainKeyWords, wantWords);
                addWords(mainKeyWords, burgerWords);
                addWords(mainKeyWords, sandwichWords);
                addWords(mainKeyWords, souvlakiaWords);
            }
        }

        return finalKeyWords;
    }

    /**
     * This method returns a List containing all the possible word variations of a specific word.
     *
     * @param filePath The relative file path.
     * @return List object containing the final keywords.
     */
    private static List<String> getWordListFromFile(String filePath) {
        String words = FileParser.getFileContentAsString(filePath);
        String[] wordList = words.split("\n");
        List<String> outList = new LinkedList<>();
        for (int i=0; i<wordList.length; i++) {
            outList.add(wordList[i]);
        }
        return outList;
    }

    /**
     * This method splits the id name from the id itself and adds it in the List to be sent to the App Class.
     *
     * @param idList List containing the id name and id of the item.
     * @return void
     */
    private static void addIds(List<String> idList) {
        String[] part;
        for (String id : idList) {
            part = id.split("=");
            finalKeyWords.add(part[0]);
            finalKeyWords.add(part[1]);
        }
    }

    /**
     * This method matches the users words with the main word of their family and adds them in the List to be
     * sent to the App Class.
     *
     * @param mainList List containing the relevant words from the user's message.
     * @param wordList List containing all the word variations for a specific word family.
     * @return void
     */
    private static void addWords(List<String> mainList, List<String> wordList) {
        for (String word : mainList) {
            if (wordList.contains(word.toLowerCase()) && !finalKeyWords.contains(wordList.get(0))) {
                finalKeyWords.add(wordList.get(0));
            }
        }
    }

    public static void main(String args[]) {
//        TextParser tp = new TextParser("I want souvlakia help");
//        TextParser tp = new TextParser("I want souvlakia");
//        TextParser tp = new TextParser("I want burgers");
        TextParser tp = new TextParser("I want a sandwich");
//        TextParser tp = new TextParser("usr_selection res_id=123456");
        List<String> keyWords = tp.getKeyWords();
        System.out.println(keyWords);
    }
}
