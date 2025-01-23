package data_structures;

import front_end.simbols.Simbol;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SymbolTable {

    protected Stack<HashMap<String, Simbol>> tambit;
    protected ArrayList<Simbol> temp;
    protected int profunditat;
    protected String currentProcedure = "principal";
    
    public Writer writer;
    private static final String FILE_PATH = "output/Taula_simbols.txt";

    public SymbolTable() {
        try {
            tambit = new Stack<>();
            temp = new ArrayList<>();
            profunditat = 0;

            try (BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(FILE_PATH, false), StandardCharsets.UTF_8))) {
                // Format header with fixed column widths
                headerWriter.write(String.format(
                        "%-30s %-10s %-30s %-20s\n",
                        "NOM", "TIPUS", "SUBPROGRAMA", "ARGS"));
            }

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Logger.getLogger(SymbolTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        incAmbit();
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
        tambit.get(profunditat - 1).put(s.getNom(), s);
        writeSymbolToFile(s, null);
    }

    public void insertElement(String nom, String tipus, ArrayList<Integer> dimensions) {
        Simbol s = new Simbol(nom, tipus, dimensions);
        tambit.get(profunditat - 1).put(s.getNom(), s);
        writeSymbolToFile(s, null); 
    }

    public void insertElement(String nom, String tipus) {
        Simbol s = new Simbol(nom, tipus);
        tambit.get(profunditat - 1).put(s.getNom(), s);
        writeSymbolToFile(s, null); 
    }

    public void insertArray(String nom, String tipus){
        Simbol s = new Simbol(nom, tipus);
        tambit.get(profunditat - 1).put(s.getNom(), s);
        writeArrayToFile(s, null); 
    }

    public void insertElementWithArgs(String nom, String tipus, Object valor, ArrayList<String> args) {
        Simbol s = new Simbol(nom, tipus, valor);
        s.setArgs(args);
        tambit.get(profunditat - 1).put(s.getNom(), s);
        writeSymbolToFile(s, args);
    }

    public boolean existeixTsambit(String a) {
        if (tambit.get(profunditat - 1).containsKey(a)) {
            return true;
        }
        return existeixTemp(a);
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

    public String getTipus(String simbol){
        return get(simbol).getTipus();
    }
    public int getProfunditat() {
        return profunditat;
    }

    public void setProfunditat(int profunditat) {
        this.profunditat = profunditat;
    }

    private void writeSymbolToFile(Simbol simbol, ArrayList<String> args) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8))) {
            String argsString = (args != null && !args.isEmpty()) ? args.toString() : "[]";

            // Format columns with fixed widths
            writer.write(String.format("%-30s %-10s %-30s %-20s\n",
                    simbol.getNom(),
                    simbol.getTipus(),
                    currentProcedure,
                    argsString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeArrayToFile(Simbol simbol, ArrayList<String> args) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8))) {
            String argsString = (args != null && !args.isEmpty()) ? args.toString() : "[]";

            // Format columns with fixed widths
            writer.write(String.format("%-30s %-10s %-30s %-20s\n",
                    simbol.getNom()+"[]",
                    simbol.getTipus(),
                    currentProcedure,
                    argsString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(String currentProcedure) {
        this.currentProcedure = currentProcedure;
    }

}
