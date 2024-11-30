
package front_end.simbols;

import data_structures.Parameter;
import data_structures.Procedure;
import java.util.ArrayList;


public class NodePmb extends NodeBase{
    
     public NodePmb() {
        super("PMB", 0);
    }
    public void generateCode(){
        String startLabel = cta.getTemp_id();
        cta.setTemp_id(null);
        tp.addRow(new Procedure(tp.getNewNumProc(), 0, startLabel, getParams(), 0, Tipus.NULL));
        cta.generateCode("pmb " + startLabel + "\n");
    }
    private ArrayList<Parameter> getParams() {
        ArrayList<Simbol> paramSymbol = ts.getParams();
        ArrayList<Parameter> params = new ArrayList<>();

        for (Simbol symbol : paramSymbol) {
            Tipus enum_type = Tipus.valueOf(symbol.getTipusSubjacent().toUpperCase());
            params.add(new Parameter(symbol.getNom(), enum_type));
        }

        return params;
    }
}
