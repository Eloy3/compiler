package front_end.simbols;

import java.util.ArrayList;

import data_structures.Parameter;
import data_structures.Procedure;
import java_cup.runtime.Symbol;

public class NodePrincipal extends NodeBase {
    private NodeSentencies sentencies;

    public NodePrincipal(NodeSentencies sentencies) {
        super("Principal", 0);
        this.sentencies = sentencies;
    }

    public void generateCode() {
        ts.insertElement("principal", "NULL", null);
        cta.setTemp_id("principal");
        String start_label = cta.getTemp_id();
        cta.push(cta.getStart_stack(), "principal");
        cta.push(cta.getPproc(), "principal");
        cta.generateCode("principal:skip\n");

        String startLabel = cta.getTemp_id();
        cta.setTemp_id(null);
        tp.addRow(new Procedure(tp.getNewNumProc(), ts.getProfunditat(), startLabel, getParams(), 0, Tipus.NULL));
        cta.generateCode("pmb " + startLabel + "\n");

        cta.push(cta.getStart_stack(), start_label);
        cta.setTemp_id("principal");
        if (sentencies != null) {
            sentencies.generateCode();
        }
    }

    private ArrayList<Parameter> getParams() {
        ArrayList<Simbol> paramSymbol = ts.getParams();
        ArrayList<Parameter> params = new ArrayList<>();

        for (Simbol symbol : paramSymbol) {
            Tipus enum_type = Tipus.valueOf(symbol.getTipusSubjacent().toUpperCase());
            params.add(new Parameter(symbol.getNom(), enum_type, "principal"));
        }

        return params;
    }
}
