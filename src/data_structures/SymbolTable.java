package data_structures;

import front_end.simbols.Simbol;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SymbolTable {

    protected Stack<HashMap<String, Simbol>> tambit;
    protected ArrayList<Simbol> temp;
    protected int profunditat;
    public Writer writer;
    private static final String FILE_PATH = "output/Taula_simbols.txt";

    public SymbolTable() {
        try {
            tambit = new Stack<>();
            temp = new ArrayList<>();
            profunditat = 0;
    
            // Open the file in overwrite mode (to write the header cleanly)
            try (BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, false), StandardCharsets.UTF_8))) {
                headerWriter.write("ID\tTIPUS\tVALOR\tNIVELL\tARGS\n");
            }
    
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Logger.getLogger(SymbolTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        incAmbit();
    }

    private void writeFile(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incAmbit() {
        tambit.push(new HashMap<>());
        profunditat++;
    }

    public void decAmbit() {
        tambit.pop();
        profunditat--;
    }

    public void addParams(Simbol a) {
        temp.add(a);
    }

    public void insertElement(String nom, String tipus, Object valor) {
        Simbol s = new Simbol(nom, tipus, valor);
        tambit.peek().put(s.getNom(), s);
        writeSymbolToFile(s, null); // Arguments are null by default
    }

    public void insertElementWithArgs(String nom, String tipus, Object valor, ArrayList<String> args) {
        Simbol s = new Simbol(nom, tipus, valor);
        tambit.peek().put(s.getNom(), s);
        writeSymbolToFile(s, args);
    }

    public boolean existeixTs(String a) {
        for (int i = tambit.size() - 1; i >= 0; i--) {
            if (tambit.get(i).containsKey(a)) {
                return true;
            }
        }
        return existeixTemp(a);
    }

    public boolean existeixTemp(String a) {
        if (temp == null) {
            return false;
        }

        for (Simbol temp : temp) {
            if (temp.getNom().equals(a)) {
                return true;
            }
        }
        return false;
    }

    public Simbol get(String a) {
        if (existeixTs(a)) {
            for (int i = tambit.size() - 1; i >= 0; i--) {
                if (tambit.get(i).containsKey(a)) {
                    return tambit.get(i).get(a);
                }
            }
        }

        if (temp != null) {
            for (Simbol t : temp) {
                if (t.getNom().equals(a)) {
                    return t;
                }
            }
        }
        return null;
    }

    public int getProfunditat() {
        return profunditat;
    }

    public void setProfunditat(int profunditat) {
        this.profunditat = profunditat;
    }

    private void writeSymbolToFile(Simbol simbol, ArrayList<String> args) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), "utf-8"))) {
            String argsString = "";
            if (args != null && !args.isEmpty()) {
                Collections.reverse(args); // Reverse arguments
                argsString = args.toString();
            }
            writer.write(String.format("%s\t%s\t%s\t%d\t%s\n",
                    simbol.getNom(),
                    simbol.getTipus(),
                    simbol.getValor() != null ? simbol.getValor() : "null",
                    profunditat,
                    argsString.isEmpty() ? "[]" : argsString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Simbol> getParams() {
        Collections.reverse(temp);
        return temp;
    }
}
