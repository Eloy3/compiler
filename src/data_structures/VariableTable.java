/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package data_structures;

import front_end.simbols.Tipus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class VariableTable {

    private int int_store;
    private int logic_store;
    private int null_store;

    private static final String TABLE_NAME = "Taula de variables";

    public Writer writer;
    public BufferedReader br;
    private final ArrayList<Variable> rows_list = new ArrayList<>();

    private static final String TABLE_FILE_PATH = "output/Taula_variables.txt";
    private static final String STORES_FILE_PATH = "src/data_structures/stores.txt";

    public VariableTable() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TABLE_FILE_PATH), StandardCharsets.UTF_8));
            setStore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeFile(Title());
        writeFile(TableHeader());


    }

    private void setStore() throws IOException {
        br = new BufferedReader(new FileReader(STORES_FILE_PATH));
        int_store = Integer.parseInt(br.readLine().split(" ")[2]);
        logic_store = Integer.parseInt(br.readLine().split(" ")[2]);
        null_store = Integer.parseInt(br.readLine().split(" ")[2]);
        br.close();
    }


    public void addRow(Variable var) {

        for (Variable variable : rows_list) { //Cerca i Si ja existeix no la torna a afegir
            if (variable.getName().equals(var.getName())) {
                return;
            }
        }

        rows_list.add(var);
    }

    public Variable getVar(String id){
        for(Variable v : this.rows_list){
            if(v.getName().equals(id)){
                return v;
            }
        }
        return null;
    }

    private void writeFile(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int calculateStore(String type, String s) {
        Tipus enum_type = Tipus.valueOf(type.toUpperCase());
        return switch (enum_type) {
            case ENT -> int_store;
            case BOOL -> logic_store;
            case NULL -> null_store;
            case BUIT -> null_store;
        };
    }

    public void closeFile() {
        try {
            for (Variable variable : rows_list) {
                writeFile(AddTableRow(variable));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String Title() {
        return TABLE_NAME + "\n";
    }

    private String TableHeader() {
        return String.format(
            "\n%-15s %-10s %-20s %-15s %-15s %-20s %-10s\n",
            "Nombre", "NV", "Subprograma", "Ocupació", "Desplaçament", "Tipus subjacent", "Valor"
        );
    }
    
    private String AddTableRow(Variable node) {
        return String.format(
            "%-15s %-10d %-20s %-15d %-15d %-20s %-10s\n",
            node.getName(),
            node.getN_var(),
            node.getSubprog(),
            node.getStore(),
            node.getOffset(),
            node.getType(),
            (node.getValue() != null) ? node.getValue() : ""
        );
    }

    public ArrayList<Variable> getRows_list() {
        return rows_list;
    }
}
