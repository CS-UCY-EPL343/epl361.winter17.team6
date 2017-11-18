public class WordLinkedList {
    private class Node {
        private String item;
        private Node link;

        public Node(String word) {
            item = word;
            link = null;
        }

        public Node(String newItem, Node linkValue) {
            item = newItem;
            link = linkValue;
        }
    }//End of Node inner class

    private Node head;

    public WordLinkedList(String word) {
        head = new Node(word);
    }

    public void add(String word) {
        Node node = new Node(word, head.link);
        head.link = node;
    }

    public boolean contains(String item) {
        return (find(item) != null);
    }

    private Node find(String target) {
        Node position = head;
        String itemAtPosition;
        while (position != null) {
            itemAtPosition = position.item;
            if (itemAtPosition.equals(target))
                return position;
            position = position.link;
        }
        return null; //target was not found
    }

    public String getKeyWord() {
        return this.head.item;
    }

    public String toString() {
        String out = this.head.item + ": ";
        Node pos = this.head.link;
        while (pos != null) {
            out += pos.item + ", ";
            pos = pos.link;
        }
        out += "/end of list";
        return out;
    }
}
