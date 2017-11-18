import java.util.LinkedList;

public class WordHashTable {

    private LinkedList<WordLinkedList> wordHashArray;

    public WordHashTable() {
        this.wordHashArray = new LinkedList<>();
    }

    public void addNewKeyWord(String keyWord) {
        wordHashArray.add(new WordLinkedList(keyWord));
    }

    public void add(String keyWord, String word) {
        for (int i = 0; i < this.wordHashArray.size(); i++) {
            WordLinkedList wordList = this.wordHashArray.get(i);
            if (wordList.getKeyWord().equals(keyWord)) {
                wordList.add(word);
            }
        }
    }

    public void print() {
        for(int i=0; i<this.wordHashArray.size(); i++) {
            WordLinkedList wordList = this.wordHashArray.get(i);
            System.out.println(wordList);
        }
    }

    public static void main(String args[]) {
        WordHashTable wht = new WordHashTable();
        wht.addNewKeyWord("burger");
        wht.print();
        wht.add("burger", "burrger");
        wht.print();
    }
 }
