package errors;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ErrorLogger {
    private static final String ERROR_FILE = "output/errors.txt";

    public static void logLexicError(int[] column_line, String message) {
        logError("Error lèxic. [" + column_line[0] + ":" + column_line[1] + "] " + message);
    }

    public static void logSemanticError(int[] column_line, String message) {
        logError("Error semàntic. [" + column_line[0] + ":" + column_line[1] + "] " + message);
    }

    public static void logSemanticError(String message) {
        logError("Error semàntic. " + message + "\n");
    }

    public static void logSintacticError(int[] column_line, String message) {
        logError("Error sintàctic. [" + column_line[0] + ":" + column_line[1] + "] " + message);
    }

    public static void logCompilerError(String message) {
        logError("Error intern del compilador: " + message);
    }

    private static void logError(String message) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(ERROR_FILE, true), StandardCharsets.UTF_8))) {
                    writer.write(message);
                    writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to log error: " + e.getMessage());
        }
        System.exit(0);
    }
}
