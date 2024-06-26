

package compilador.errors;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Error_VarNoExisteix extends Error {

    @Override
    public int printError(int[] column_line, String var_err) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compilador/output/Errors.txt", true), StandardCharsets.UTF_8));
            writer.write("Error semàntic. "+ "[" + column_line[0] + ":" + column_line[1] + "]" + " La variable " + "'" + var_err + "'" + " no existeix\n");
            
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(Error_VarNoExisteix.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
