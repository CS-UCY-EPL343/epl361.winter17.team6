import java.util.List;

public class HelpResponse extends AbstractResponse{

    private String HELPMSG="Please enter a specific food or near restaurant such as souvlakia,burger,sandwich";
    private final static String HELP_CLASS_NAME = "food-categ";

    public String getResponse() {

        StringBuilder response = new StringBuilder();

        response.append("<ul class=\"" + HELP_CLASS_NAME +  "\""+ HELPMSG + "</ul>");



            return HELPMSG;
    }
}
