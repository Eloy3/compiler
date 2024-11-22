package front_end.simbols;

import errors.ErrorLogger;
import util.Util;

public class NodeRetorna extends NodeBase{
    
    protected NodeExprsimple expr;
    protected int[] lc;

    public NodeRetorna(){
        super("retorna", 0);
    }

    public NodeRetorna(NodeExprsimple expr, int[] lc){
        super("retorna", 0);
        this.expr = expr;
        this.lc = lc;
    }

    public void generateCode(){

        String currentFunctionName = cta.getTop(cta.getPproc());
        Simbol functionSymbol = ts.get(currentFunctionName);

        if (functionSymbol == null) {
            ErrorLogger.logSemanticError(lc, "Funció '" + currentFunctionName + "' no trobada.");
            return;
        }

        String functionReturnType = functionSymbol.getTipus();

        // If there is no expression, validate that the function return type is buit
        if (expr == null) {
            if (!Util.typeMatches(functionReturnType, "BUIT")) {
                ErrorLogger.logSemanticError(lc, "La funció '" + currentFunctionName + "' ha de retornar un valor del tipus '" + functionReturnType + "'.");
            }
            cta.generateCode("rtn " + currentFunctionName + "\n");
            return;
        }

        String exprType = expr.getTipusAsString();
        if (!Util.typeMatches(functionReturnType, exprType)) {
            ErrorLogger.logSemanticError(lc, "Tipus de retorn incorrecte a la funció '" + currentFunctionName + "'. Esperat: '" + functionReturnType + "', obtingut: '" + exprType + "'.");
        }

        String returnValue = expr.getValor();
        cta.generateCode("rtn " + returnValue + "\n");
        
    }
}
