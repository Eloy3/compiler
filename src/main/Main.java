package main;
import java.io.*;


import back_end.AssemblyCode;
import back_end.ThreeAdressCodeBackEnd;
import front_end.scanner.Lexic;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;
import front_end.parser.Parser;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument given");
            return;
        }

        try (Reader input = new FileReader(args[0])) {
            clearFiles(
                "output/tokens.txt",
                "output/Taula_simbols.txt",
                "output/Taula_variables.txt",
                "output/Errors.txt",
                "output/codiIntermitg.txt"
            );

            SymbolFactory sf = new ComplexSymbolFactory();
            Lexic scanner = new Lexic(input);
            Parser parser = new Parser(scanner, sf);
            parser.parse();

            ThreeAdressCodeBackEnd c3a = new ThreeAdressCodeBackEnd();
            AssemblyCode ac = new AssemblyCode(c3a);
            ac.generate();
            writeFile("output/codiEnsamblador.X68", ac.getCode());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("error: " + e.getMessage());
        }
    }

    private static void clearFiles(String... filePaths) {
        for (String filePath : filePaths) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("");
                System.out.println("El fitxer " + filePath + " s'ha buidat");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void writeFile(String canonicalFilename, String text) throws IOException {
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(canonicalFilename), "UTF-8"))) {
            out.write(text);
        }
    }
}