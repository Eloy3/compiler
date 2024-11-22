package front_end.simbols;

import java.util.ArrayList;
import java.util.List;

import errors.*;

public class NodeCrida_funcio extends NodeBase {

    private NodeParam param;
    private String functionName;
    private int[] lc;

    public NodeCrida_funcio(String functionName, NodeParam p, int[] lc) {
        super("crida_funcio", 0);
        this.functionName = functionName;
        this.lc = lc;     
    }

    public void generateCode() {

        if (!ts.existeixTs(functionName)) {
            ErrorLogger.logSintacticError(lc, "La funció '" + functionName + "' no existeix");
        } 

        List<NodeParam> paramList = extractParamList();
        List<String> providedTypes = new ArrayList<>();
        for (NodeParam param : paramList) {
            providedTypes.add(param.getTipus().toString());
        }

    }

    private List<NodeParam> extractParamList() {
        List<NodeParam> params = new ArrayList<>();
        NodeParam current = param;
        while (current != null) {
            params.add(current);
            current = current.getNext();
        }
        return params;
    }

}

