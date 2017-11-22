import java.util.List;

public class HelpResponse extends AbstractResponse{

    private String HELPMSG="Instructions:\n\nTo use the Chatbot you have to either enter a specific restaurant that " +
            "you want to order from or a specific food category in order to receive a list of the restaurants that " +
            "serve the food from that category.";
    private final static String HELP_CLASS_NAME = "food-categ";

    public HelpResponse() {
    }

    public HelpResponse(String msg) {
        this.HELPMSG = msg + "\n" + HELPMSG;
    }

    public String getResponse() {

        StringBuilder response = new StringBuilder();

        response.append("<ul class=\"" + HELP_CLASS_NAME +  "\""+ HELPMSG + "</ul>");



            return HELPMSG;
    }
}
