package front_end.simbols;

import java.util.ArrayList;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.tipusexpr;
import util.TacUtil;
import util.Util;

public class NodeSortida extends NodeBase {

    private final NodeLlistaValors LlistaValors;
    private final boolean linea;
    private final int[] lineCode;

    public NodeSortida(NodeLlistaValors lv, boolean l, int[] lc) {
        super("Sortida", 0);
        this.LlistaValors = lv;
        this.linea = l;
        this.lineCode = lc;
    }

    public void generateCode() {
        ArrayList<NodeExprsimple> printList = Util.getArrayList(LlistaValors);
        for (NodeExprsimple print : printList) {
            try {
                if(print.getTipus() == tipusexpr.arrayValue){
                    Simbol id = Util.validateVariableExists(ts, print.getValor(), lineCode);
                    String tempVar = cta.newTempVar(id.getTipus(), null);
                    String array = TacUtil.generateIndexes(id.getNom(), print.getPos());
                    cta.generateCode("assign", tempVar, array, ts);
                    cta.generateCode("param_s " + tempVar + "\n");
                }else{
                    cta.generateCode(paramType(print) + " " + print.getValor() + "\n");
                }
                cta.generateCode("call " + ((linea) ? "line" : "print") + "\n");
            } catch (RuntimeException e) {
                ErrorLogger.logSemanticError(lineCode, e.getMessage());
            }
        }
        cta.setTemp_id(null);
    }

    private String paramType(NodeExprsimple print) {
        if (print.getTipus() == tipusexpr.id || print.getTipus() == tipusexpr.arrayValue) {
            Simbol id = ts.get(print.getValor());
            if (id == null) {
                String errorMsg = "Variable '" + print.getValor() + "' no existeix.";
                throw new RuntimeException(errorMsg);
            }

            String varType = id.getTipus();
            switch (varType.toLowerCase()) {
            case "ent":
                return "param_s"; // Integer parameter
            case "bool":
                return "param_b"; // Boolean parameter
            default:
                throw new RuntimeException("Unsupported variable type: " + varType);
            }
        } else {
            return "param_c"; // Constant value
        }
    }
}
