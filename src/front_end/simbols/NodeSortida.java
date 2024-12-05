package front_end.simbols;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.exprsimple;

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
        try {
            // Generate parameter type and call code
            cta.generateCode(paramType() + " " + LlistaValors.getValor().getValor() + "\n");
            cta.generateCode("call " + ((linea) ? "line" : "print") + "\n");
        } catch (RuntimeException e) {
            // Log the error and handle gracefully
            ErrorLogger.logSemanticError(lc, e.getMessage());
        }
        cta.setTemp_id(null);
    }

    private String paramType() {
        // Check if the value is an identifier (ID)
        if (LlistaValors.getValor().getTipus() == exprsimple.id) {
            Simbol id = ts.get(LlistaValors.getValor().getValor());

            // If the variable does not exist in the symbol table, log an error
            if (id == null) {
                ErrorLogger.logSemanticError(
                    lc,
                    "Variable '" + LlistaValors.getValor().getValor() + "' no existeix."
                );
                throw new RuntimeException("Variable '" + LlistaValors.getValor().getValor()+"' no existeix");
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
                    "Unsupported variable type for: " + LlistaValors.getValor().getValor()
                );
                throw new RuntimeException("Unsupported variable type: " + varType);
            }
        } else {
            // If it's a constant value, assume "param_c"
            return "param_c";
        }
    }
}
