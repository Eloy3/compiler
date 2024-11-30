package front_end.simbols;

import java.util.ArrayList;
import java.util.List;

import errors.*;
import util.Util;

public class NodeCrida_funcio extends NodeBase {

    private NodeArg param;
    private String functionName;
    private int[] lc;
    private String resultTemp;

    public NodeCrida_funcio(String functionName, NodeArg p, int[] lc) {
        super("crida_funcio", 0);
        this.functionName = functionName;
        this.lc = lc;     
        param = p;
    }

    public void generateCode() {
        // Check if the function exists in the symbol table
        if (!ts.existeixTs(functionName)) {
            ErrorLogger.logSintacticError(lc, "La funció '" + functionName + "' no existeix");
            return;
        }
    
        // Extract parameter list from the call
        List<NodeArg> params = extractParamList();
    
        // Get function definition from the symbol table
        Simbol functionSymbol = ts.get(functionName);
        ArrayList<String> expectedArgs = functionSymbol.getArgs();
        if (expectedArgs.size() != params.size()) {
            ErrorLogger.logSintacticError(
                lc,
                "La funció '" + functionName + "' esperava " + expectedArgs.size() +
                " paràmetres, però se n'han proporcionat " + params.size()
            );
            return;
        }
    
        // Validate argument types and generate parameter code
        for (int i = 0; i < params.size(); i++) {
            NodeArg paramNode = params.get(i);
            String expectedType = functionSymbol.getTipus();
    
            // Resolve argument type
            Simbol argSymbol = ts.get(paramNode.getId());
            if (argSymbol == null) {
                ErrorLogger.logSemanticError(
                    lc,
                    "L'argument '" + paramNode.getId() + "' no està declarat."
                );
                return;
            }
    
            String actualType = argSymbol.getTipus();
    
            // Check type compatibility
            if (!Util.typeMatches(expectedType, actualType)) {
                ErrorLogger.logSemanticError(
                    lc,
                    "Paràmetre " + (i + 1) + " de la funció '" + functionName + 
                    "' esperava tipus '" + expectedType + "' però s'ha trobat '" + actualType + "'"
                );
                return;
            }else{
                cta.generateCode("param_s "+argSymbol.getNom() + "\n");
            }
            
        }
    
        // Generate code for the function call
        cta.generateCode("call " + functionName + "\n");

        // Handle return value (if applicable)
        if (!functionSymbol.getTipus().equals("BUIT")) {
            //resultTemp = cta.newTempVar(functionSymbol.getTipus(), null);
            //cta.generateCode("MOVE.W (A7)+, " + resultTemp + "\n");
        }
    }
    
    private List<NodeArg> extractParamList() {
        List<NodeArg> params = new ArrayList<>();
        NodeArg current = param;
        while (current != null) {
            params.add(current);
            current = current.getNext();
        }
        return params;
    }

    public String getResult() {
        return resultTemp;
    }

    public void setResult(String result) {
        this.resultTemp = result;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
}

