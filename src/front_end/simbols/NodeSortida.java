package front_end.simbols;

import java.util.ArrayList;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.tipusexpr;
import util.Util;

public class NodeSortida extends NodeBase {

    private final NodeLlistaValors LlistaValors;
    private final boolean linea;
    private final int[] lc;

    public NodeSortida(NodeLlistaValors lv, boolean l, int[] lc) {
        super("Sortida", 0);
        this.LlistaValors = lv;
        this.linea = l;
        this.lc = lc;
    }

    public void generateCode() {
        ArrayList<NodeExprsimple> printList = Util.getArrayList(LlistaValors);
        for (NodeExprsimple print : printList) {
            try {
                cta.generateCode(paramType(print) + " " + print.getValor()+"_"+ts.getCurrentProcedure() + "\n");
                cta.generateCode("call " + ((linea) ? "line" : "print") + "\n");
            } catch (RuntimeException e) {
                ErrorLogger.logSemanticError(lc, e.getMessage());
            }
        }
        cta.setTemp_id(null);
    }

    private String paramType(NodeExprsimple print) {
        if (print.getTipus() == tipusexpr.id) {
            Simbol id = ts.get(print.getValor());
            if (id == null) {
                String errorMsg = "Variable '" + print.getValor() + "' no existeix.";
                //ErrorLogger.logSemanticError(lc, errorMsg);
                throw new RuntimeException(errorMsg);
            }

            String varType = id.getTipus();
            switch (varType.toLowerCase()) {
                case "ent":
                    return "param_s"; // Integer parameter
                case "bool":
                    return "param_b"; // Boolean parameter
                default:
                    //String errorMsg = "Unsupported variable type for: " + print.getValor();
                    //ErrorLogger.logSemanticError(lc, errorMsg);
                    throw new RuntimeException("Unsupported variable type: " + varType);
            }
        } else {
            return "param_c"; // Constant value
        }
    }
}
