import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tomis on 18/11/2017.
 */
class FileParser {

     static String getFileContentAsString(String path) {
        StringBuilder fileString = new StringBuilder("");
        // The name of the file to open.
        String fileName = path;
        // This will reference one line at a time
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                fileString.append(line +"\n");
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.err.println("Unable to open file '" + fileName + "'");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        return fileString.toString();
    }
}
