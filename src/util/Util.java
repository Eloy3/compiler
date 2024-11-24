package util;

import data_structures.SymbolTable;
import errors.Error_VarNoExisteix;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.Simbol;

public abstract class Util {
    
    public static Simbol validateVariableExists(SymbolTable ts, String varId, int[] lc) {
        if (!ts.existeixTs(varId)) {
            new Error_VarNoExisteix().printError(lc, varId);
            return null;
        }
        return ts.get(varId);
    }
    
    public static boolean typeMatches(String type1, String type2) {
        return type1 != null && type1.equalsIgnoreCase(type2);
    }

    public static Simbol validateOperand(SymbolTable ts, NodeExprsimple operand, int[] lc) {
        if (operand.getTipus() == NodeExprsimple.exprsimple.id) {
            return Util.validateVariableExists(ts, operand.getValor(), lc);
        }
        // Handle literals by creating a temporary Simbol
        return new Simbol(operand.getTipusAsString(), operand.getValor());
    }

    public static boolean isIdentifier(String type) {
        // Assume identifiers are not primitive types like "ENT" or "BOOL"
        return !type.equals("ENT") && !type.equals("BOOL");
    }
}
