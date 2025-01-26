package data_structures;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TaulaProcediments {

    private int num_proc;
    public Writer writer;
    private ArrayList<Procedure> row_list ;

    private static final String TABLE_NAME = "Taula de procedimients";
    private static final String FILE_PATH = "output/Taula_Procedures.txt";

    public TaulaProcediments() {
        try {
            this.num_proc = 0;
            this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8));
            this.row_list = new ArrayList<>();

            writeFile(Title());
            writeFile(TableHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addRow(Procedure proc) {
        row_list.add(proc);
    }


    public void closeFile() {
        for (Procedure procedure : row_list) {
            writeFile(AddTableRow(procedure));
        }

        try {

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateLocalVarSize(VariableTable tv) {
        for (Procedure procedure : row_list) {

            for (int i = 0; i < tv.getRows_list().size(); i++) {
                if (tv.getRows_list().get(i).getSubprog().equalsIgnoreCase(procedure.getStart_label())) {
                    procedure.setTotal_store(procedure.getTotal_store() + tv.getRows_list().get(i).getStore());
                }
            }
        }
    }

    private void writeFile(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNewNumProc() {
        return ++num_proc;
    }

    public Procedure getProc(String id){
        for(Procedure p : this.row_list){
            if(p.getStart_label().equals(id)){
                return p;
            }
        }
        return null;
    }

    public Parameter getParam(String procedure,String id){
        for(Procedure p : this.row_list){
            if(p.getStart_label().equals(procedure)){
                return p.getParam(id);
            }
        }
        
        System.err.println("Error: Procedure not found");
        return null;
    }

    private String Title() {
        return TABLE_NAME + "\n";
    }

    private String TableHeader() {
        return String.format(
            "\n%-5s %-12s %-15s %-15s %-25s %-15s\n",
            "NP", "Profunditat", "Etiqueta inici", "Parámetres", "Ocupació variables locals", "Tipus retorn"
        );
    }

    private String AddTableRow(Procedure node) {
        return String.format(
            "%-5d %-12d %-15s %-15s %-25d %-15s\n",
            node.getNum_proc(),
            node.getDepth(),
            node.getStart_label(),
            node.getParameters().toString(),
            node.getTotal_store(),
            (node.getType_return() != null) ? node.getType_return() : ""
        );
    }


}