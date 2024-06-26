
package compilador.simbols;

import compilador.backend.Parametro;
import compilador.estructura_de_dades.Procediment;
import java.util.ArrayList;


public class NodePmb extends NodeBase{
    
     public NodePmb() {
        super("PMB", 0);

        String startLabel = cta.getTemp_id();
        cta.setTemp_id(null);
        tp.addRow(new Procedure(tp.getNewNumProc(), 0, startLabel, getParams(), 0, TipusSubjacent.NULL));
        cta.generateCode("pmb " + startLabel + "\n");
    }

    private ArrayList<Parameter> getParams() {
        ArrayList<Simbol> paramSymbol = ts.getParams();
        ArrayList<Parameter> params = new ArrayList<>();

        for (Simbol symbol : paramSymbol) {
            TipusSubjacent enum_type = TipusSubjacent.valueOf(symbol.getTipusSubjacent().toUpperCase());
            params.add(new Parameter(symbol.getNom(), enum_type));
        }

        return params;
    }
}
