package data_structures;

import front_end.simbols.Tipus;
import java.util.ArrayList;

public class Procedure {

    private final int num_proc;
    private final int depth;
    private final String start_label;
    private final ArrayList<Parameter> parameters;
    private int total_store;
    private Tipus type_return;

    public Procedure(int num_proc, int depth, String start_label, ArrayList<Parameter> parametros, int total_store, Tipus type_return) {
        this.num_proc = num_proc;
        this.depth = depth;
        this.start_label = start_label;
        this.parameters = parametros;
        this.total_store = total_store;
        this.type_return = type_return;
    }

    public int getNum_proc() {
        return num_proc;
    }

    public int getDepth() {
        return depth;
    }

    public ArrayList<Parameter> getParameters() { return parameters; }

    public Parameter getParam(String id){
        for(Parameter p : this.parameters){
            if(p.getNombre().equals(id)){
                return p;
            }
        }
        
        return null;
    }
    public int getTotal_store() {
        return total_store;
    }

    public void setTotal_store(int total_store) {
        this.total_store = total_store;
    }

    public void setType_return(Tipus type_return) {
        this.type_return = type_return;
    }

    public String getStart_label() {
        return start_label;
    }

    public Tipus getType_return() {
        return type_return;
    }
}
