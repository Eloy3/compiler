package main;
import java.io.Reader;

import front_end.scanner.Lexic;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;
import front_end.parser.Parser;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        Reader input;
        try {
            if (args.length > 0) {
                input = new FileReader(args[0]);
                cleanTextFile("output/tokens.txt");
                cleanTextFile("output/taulaSimbols.txt");
                cleanTextFile("output/Taula_simbols.txt");
                cleanTextFile("output/Taula_variables.txt");
                cleanTextFile("output/Errors.txt");
                cleanTextFile("output/codiIntermitg.txt");

                SymbolFactory sf = new ComplexSymbolFactory();
                Lexic scanner = new Lexic(input);
                Parser parser = new Parser(scanner, sf);
                parser.parse();
            } else {
                System.out.println("No argument given");
                input = null;
            }

            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("error: " + e.getMessage());
        }
    }

    public static void cleanTextFile(String filePath) {
        try {
            // Open the file for writing, this will clear its contents
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            // Write an empty string to the file
            writer.write("");

            // Close the writer
            writer.close();

            System.out.println("El fitxer " + filePath + " s'ha buidat");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
