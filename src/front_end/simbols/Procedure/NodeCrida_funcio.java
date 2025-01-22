package front_end.simbols.Procedure;

import data_structures.Parameter;
import java.util.ArrayList;
import java.util.List;

import data_structures.Procedure;
import errors.*;
import front_end.simbols.NodeBase;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.Simbol;
import front_end.simbols.NodeExprsimple.tipusexpr;
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

    public boolean generateCode() {
        if(tp.getProc(functionName)==null){
            ErrorLogger.logSemanticError(lc, "La funció '" + functionName + "' no existeix");
            return false;
        }
        // Extract parameter list from the call
        List<NodeArg> params = extractParamList();
    
        // Get function definition from the symbol table
        //Simbol functionSymbol = ts.get(functionName);
        Procedure functionSymbol = tp.getProc(functionName);
        ArrayList<Parameter> expectedArgs = functionSymbol.getParameters();
        ArrayList<String> expectedTypes = getTipusArgs(expectedArgs);
    
        if (expectedArgs.size() != params.size()) {
            ErrorLogger.logSintacticError(
                lc,
                "La funció '" + functionName + "' esperava " + expectedArgs.size() +
                " paràmetres, però se n'han proporcionat " + params.size()
            );
            return false;
        }
    
        // Validate argument types and generate parameter code
        for (int i = 0; i < params.size(); i++) {
            NodeArg paramNode = params.get(i);
            NodeExprsimple.tipusexpr actualType = paramNode.getTipus(); 
            String expectedType = expectedTypes.get(i);

            if (actualType == NodeExprsimple.tipusexpr.id) {
                // If the argument is an identifier, check its type in the symbol table
                Simbol argSymbol = ts.get(paramNode.getExprsimple().getValor());
                if (argSymbol == null) {
                    ErrorLogger.logSemanticError(
                        lc,
                        "L'argument '" + paramNode.getExprsimple().getValor() + "' no està declarat."
                    );
                    return false;
                }
    
                // Check type compatibility
                if (!Util.typeMatches(expectedType, argSymbol.getTipus())) {
                    ErrorLogger.logSemanticError(
                        lc,
                        "Paràmetre " + (i + 1) + " de la funció '" + functionName +
                        "' esperava tipus '" + expectedType + "' però s'ha trobat '" + argSymbol.getTipus() + "'"
                    );
                    return false;
                }
    
                cta.generateCode("param_s", argSymbol.getNom(), "", ts);
    
            } else if (actualType == NodeExprsimple.tipusexpr.ent || actualType == NodeExprsimple.tipusexpr.bool) {
                // If the argument is a literal, directly validate its type
                if (!Util.typeMatches(expectedType, actualType.name().toUpperCase())) {
                    ErrorLogger.logSemanticError(
                        lc,
                        "Paràmetre " + (i + 1) + " de la funció '" + functionName +
                        "' esperava tipus '" + expectedType + "' però s'ha trobat literal '" + actualType + "'"
                    );
                    return false;
                }
    
                cta.generateCode("param_c", paramNode.getExprsimple().getValor(), "", ts);
            } else {
                ErrorLogger.logSemanticError(
                    lc,
                    "Tipus d'argument desconegut per al paràmetre " + (i + 1) + " de la funció '" + functionName + "'."
                );
                return false;
            }
        }
    
        cta.generateCode("call " + functionName + "\n");
    
        // Handle return value (if applicable)
        if (!functionSymbol.getType_return().toString().equals("BUIT")) {
            //resultTemp = cta.newTempVar(functionSymbol.getTipus(), null);
            //cta.generateCode("MOVE.W (A7)+, " + resultTemp + "\n");
        }
        return true;
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

    public ArrayList<String> getTipusArgs(ArrayList<Parameter> params) {
        ArrayList<String> tipusList = new ArrayList<>();
        for (Parameter param : params) {
            tipusList.add(param.getTipo().toString());
        }
        return tipusList;
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

