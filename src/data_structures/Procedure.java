package data_structures;

import data_structures.Parameter;
import front_end.simbols.TipusSubjacent;
import java.util.ArrayList;

public class Procedure {

    private final int num_proc;
    private final int depth;
    private final String start_label;
    private final ArrayList<Parameter> parametros;
    private int total_store;
    private TipusSubjacent type_return;

    public Procedure(int num_proc, int depth, String start_label, ArrayList<Parameter> parametros, int total_store, TipusSubjacent type_return) {
        this.num_proc = num_proc;
        this.depth = depth;
        this.start_label = start_label;
        this.parametros = parametros;
        this.total_store = total_store;
        this.type_return = type_return;
    }

    public int getNum_proc() {
        return num_proc;
    }

    public int getDepth() {
        return depth;
    }

    public ArrayList<Parameter> getParametros() { return parametros; }

    public int getTotal_store() {
        return total_store;
    }

    public void setTotal_store(int total_store) {
        this.total_store = total_store;
    }

    public void setType_return(TipusSubjacent type_return) {
        this.type_return = type_return;
    }

    public String getStart_label() {
        return start_label;
    }

    public TipusSubjacent getType_return() {
        return type_return;
    }
}
