/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package data_structures;

import compilador.simbols.Simbol;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Stack;
import java_cup.runtime.Symbol;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SymbolTable {

    protected int n;
    protected Stack<HashMap> tambit;
    protected ArrayList<Simbol> temp;
    public Writer writer;
    private static final String FILE_PATH = "src/compilador/output/Taula_simbols.txt";
    
    
    public SymbolTable (){
        try {
            tambit = new Stack<>();
            temp = new ArrayList<>();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Logger.getLogger(SymbolTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        writeFile("\n");
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
    }  
    
    public void decAmbit() {
        tambit.pop();
    }  
    
    public void insertElement(String nom, String tipus, Object valor) {
        Simbol s = new Simbol(nom,tipus,valor);
        tambit.peek().put(s.getNom(), s);
        escriutxt(s.toString());
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
                    return (Simbol) tambit.get(i).get(a);
                }
            }
        }

        if (temp != null) {

            for (int i = 0; i < temp.size(); i++) {

                if (temp.get(i).getNom().equals(a)) {
                    return temp.get(i);
                }
            }
        }
        return null;
    }
    
    private void escriutxt(String string) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compilador/output/Taula_simbols.txt", true), "utf-8"));
            writer.write(string);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Simbol> getParams() {
        Collections.reverse(temp);
        return temp;
    }
}
