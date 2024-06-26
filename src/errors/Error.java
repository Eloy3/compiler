package compilador.errors;

import java.io.Writer;

public abstract class Error extends Exception {
    Writer writer;
    public abstract int printError(int[] e, String Error);
}
