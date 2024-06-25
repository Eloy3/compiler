import java.io.Reader;
import java.io.FileWriter;
import java.io.FileReader;
import front_end.scanner.Lexic;
public class Main {
    public static void main(String[] args) {
        Reader input;
        try{
            if(args.length > 0){
                input = new FileReader(args[0]);
            }else{
                System.out.println("No argument given");
            }
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("error: "+e.getMessage());
        }
    }
}
