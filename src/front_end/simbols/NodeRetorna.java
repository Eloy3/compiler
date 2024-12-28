package front_end.simbols;

import errors.ErrorLogger;
import util.Util;

public class NodeRetorna extends NodeBase{
    
    protected NodeExprsimple expr;
    protected int[] lc;
    protected String returnValue;

    public NodeRetorna(){
        super("retorna", 0);
    }

    public NodeRetorna(NodeExprsimple expr, int[] lc){
        super("retorna", 0);
        this.expr = expr;
        this.lc = lc;
    }

    public void generateCode() {
        String currentFunctionName = cta.getTop(cta.getPproc());
        Simbol functionSymbol = ts.get(currentFunctionName);
    
        if (functionSymbol == null) {
            ErrorLogger.logSemanticError(lc, "Funció '" + currentFunctionName + "' no trobada.");
            return;
        }
    
        String functionReturnType = functionSymbol.getTipus();
    
        if (expr == null) {
            if (!Util.typeMatches(functionReturnType, "BUIT")) {
                ErrorLogger.logSemanticError(lc, "La funció '" + currentFunctionName + "' ha de retornar un valor del tipus '" + functionReturnType + "'.");
            }
            cta.generateCode("rtn\n"); // No return value
            return;
        }
    
        String exprType = expr.getTipusAsString();
        if (exprType.equals("id")) {
            Simbol exprSymbol = ts.get(expr.getValor());
            if (exprSymbol == null) {
                ErrorLogger.logSemanticError(lc, "Variable o identificador '" + expr.getValor() + "' no trobat.");
                return;
            }
            exprType = exprSymbol.getTipus();
        }
    
        if (!Util.typeMatches(functionReturnType, exprType)) {
            ErrorLogger.logSemanticError(lc, "Tipus de retorn incorrecte a la funció '" + currentFunctionName + "'. Esperat: '" + functionReturnType + "', obtingut: '" + exprType + "'.");
        }
    
        String tempVar = cta.newTempVar(functionReturnType, null);
        cta.generateCode(tempVar + " = " + expr.getValor() + "\n");
        cta.generateCode("rtn " + tempVar + "\n");
    }
    
}
