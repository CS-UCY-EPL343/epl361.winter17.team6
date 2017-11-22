package com.chatbot.foody;

import java.util.LinkedList;
import java.util.List;

/**
 * Class com.chatbot.foody.TextParser is a class that creates com.chatbot.foody.TextParser objects that handle the analysis of messages.
 *
 * A com.chatbot.foody.TextParser object has a String field called msg which is the user message or selection. It also has five
 * static Lists that contain all the variations of possible words that the user might input and another static list
 * which will contain the words that will be sent over the com.chatbot.foody.App class and will define the proper response function
 * to be called.
 *
 * @author Kyriacos Aristides	ID: 965191
 * @version 2.0
 * @since 21/11/17
 */
public class TextParser {

    private String msg;
    private static List<String> anammaWords = getWordListFromFile("WordLists/Anamma_Wordlist.txt");
    private static List<String> wantWords = getWordListFromFile("WordLists/Want_Wordlist.txt");
    private static List<String> burgerWords = getWordListFromFile("WordLists/Burgers_Wordlist.txt");
    private static List<String> coffeeWords = getWordListFromFile("WordLists/Coffee_Wordlist.txt");
    private static List<String> sandwichWords = getWordListFromFile("WordLists/Sandwich_Wordlist.txt");
    private static List<String> souvlakiaWords = getWordListFromFile("WordLists/Souvlakia_Wordlist.txt");
    private static List<String> helpWords = getWordListFromFile("WordLists/Help_Wordlist.txt");
    private static List<String> finalKeyWords = new LinkedList<>();

    /**
     * This is the default object constructor.
     *
     * It assigns the user message to the field msg and empties the keywords list.
     *
     * @param msg The user's message.
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
            addRestId(tokens);
        } else if (tokens.get(0).equals("usr_menu")) {
            finalKeyWords.add(tokens.get(0));
            while(tokens.remove("usr_menu"));
//            while (tokens.remove("usr_menu"))
            /*for (String item : tokens) {
                System.out.println(item);
                if (item.equals("usr_menu"))
                    tokens.remove(item);
            }*/
            addMenuItemsIds(tokens);
        } else {
            List<String> mainKeyWords = KeyMapper.getMainKeyWords(tokens);
            addWords(mainKeyWords, helpWords);
            if (finalKeyWords.isEmpty()) {
                addWords(mainKeyWords, anammaWords);
                if (!finalKeyWords.isEmpty()) {
                    List<String> idList = new LinkedList<>();
                    idList.add(finalKeyWords.get(0));
                    finalKeyWords.clear();
                    addRestId(idList);
                    return finalKeyWords;
                }
            }
            if (finalKeyWords.isEmpty()) {
                addWords(mainKeyWords, wantWords);
                addWords(mainKeyWords, burgerWords);
                addWords(mainKeyWords, coffeeWords);
                addWords(mainKeyWords, sandwichWords);
                addWords(mainKeyWords, souvlakiaWords);
                return finalKeyWords;
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
     * This method splits the id name from the id itself and adds it in the List to be sent to the com.chatbot.foody.App Class.
     *
     * @param idList List containing the id name and id of the item.
     * @return void
     */
    private static void addRestId(List<String> idList) {
        String[] part;
        part = idList.get(0).split("=");
        finalKeyWords.add(part[0]);
        finalKeyWords.add(part[1]);
    }

    /**
     * This method splits the id name from the id itself and adds it in the List to be sent to the com.chatbot.foody.App Class.
     *
     * @param idList List containing the id name and id of the item.
     * @return void
     */
    private static void addMenuItemsIds(List<String> idList) {
        String[] part;
        for (String id : idList) {
            part = id.split("=");
            finalKeyWords.add(part[1]);
            part = null;
        }
    }

    /**
     * This method matches the users words with the main word of their family and adds them in the List to be sent to
     * the com.chatbot.foody.App Class.
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
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("I want souvlakia help");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("I want burgers");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("asd");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("I want a sandwich");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("I want souvlakia");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("usr_selection res_id=123456");
//        com.chatbot.foody.TextParser tp = new com.chatbot.foody.TextParser("I want souvlakia from anama");
        TextParser tp = new TextParser("usr_menu mi_id=1000 usr_menu mi_id=1001 usr_menu mi_id=1002 usr_menu mi_id=1003 usr_menu mi_id=1004");
        List<String> keyWords = tp.getKeyWords();
        System.out.println(keyWords);
    }
}
