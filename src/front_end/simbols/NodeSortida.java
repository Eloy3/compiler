package front_end.simbols;

import java.util.ArrayList;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.tipusexpr;
import util.Util;

public class NodeSortida extends NodeBase {

    private NodeLlistaValors LlistaValors;
    private boolean linea;
    private int[] lc;

    public NodeSortida(NodeLlistaValors lv, boolean l, int[] lc) {
        super("Sortida", 0);
        LlistaValors = lv;
        linea = l;
        this.lc = lc;
    }

    public void generateCode() {
        ArrayList<NodeExprsimple> printList = Util.getArrayList(LlistaValors);
        for (NodeExprsimple print : printList) {
            try {
                cta.generateCode(paramType(print) + " " + print.getValor() + "\n");
                cta.generateCode("call " + ((linea) ? "line" : "print") + "\n");
            } catch (RuntimeException e) {
                ErrorLogger.logSemanticError(lc, e.getMessage());
            }
        }
        cta.setTemp_id(null);
    }

    private String paramType(NodeExprsimple print) {
        // Check if the value is an identifier (ID)
        if (print.getTipus() == tipusexpr.id) {
            Simbol id = ts.get(print.getValor());

            // If the variable does not exist in the symbol table, log an error
            if (id == null) {
                ErrorLogger.logSemanticError(
                    lc,
                    "Variable '" + print.getValor() + "' no existeix."
                );
                throw new RuntimeException("Variable '" + print.getValor()+"' no existeix");
            }

            // Determine parameter type based on the variable's type
            String varType = id.getTipus();
            if (varType.equalsIgnoreCase("ent")) {
                return "param_s"; // Integer parameter
            } else if (varType.equalsIgnoreCase("bool")) {
                return "param_b"; // Boolean parameter
            } else {
                ErrorLogger.logSemanticError(
                    lc,
                    "Unsupported variable type for: " + print.getValor()
                );
                throw new RuntimeException("Unsupported variable type: " + varType);
            }
        } else {
            // If it's a constant value, assume "param_c"
            return "param_c";
        }
    }
}
