package util;

import data_structures.SymbolTable;
import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.NodeExprsimple.tipusexpr;
import front_end.simbols.Simbol;

public abstract class Util {
    
    public static Simbol validateVariableExists(SymbolTable ts, String varId, int[] lc) {
        if (!ts.existeixTs(varId)) {
            ErrorLogger.logSemanticError(lc,"La variable '" + varId + "' no existeix.");
            return null;
        }
        return ts.get(varId);
    }

    public static Simbol validateProcedureExists(SymbolTable ts, String varId, int[] lc) {
        if (!ts.existeixTs(varId)) {
            ErrorLogger.logSemanticError(lc,"La funció '" + varId + "' no existeix.");
            return null;
        }
        return ts.get(varId);
    }
    
    public static boolean typeMatches(String type1, String type2) {
        return type1 != null && type1.equalsIgnoreCase(type2);
    }

    public static Simbol validateOperand(SymbolTable ts, NodeExprsimple operand, int[] lc) {
        if (operand.getTipus() == NodeExprsimple.tipusexpr.id) {
            return Util.validateVariableExists(ts, operand.getValor(), lc);
        }
        // Handle literals by creating a temporary Simbol
        return new Simbol(operand.getTipusAsString(), operand.getValor());
    }

    public static boolean isIdentifier(String type) {
        // Assume identifiers are not primitive types like "ENT" or "BOOL"
        //return !type.equals("ENT") && !type.equals("BOOL");
        return type.equalsIgnoreCase("id");
    }

    public static void validateUnaryOperand(SymbolTable ts, NodeExprsimple operand, int[] lc) {
        if (validateOperand(ts, operand, lc) == null) {
            ErrorLogger.logSemanticError(lc,"Operand '" + operand.getValor() + "' is not valid.");
        }
    }

    public static boolean validateBinaryOperands(SymbolTable ts, NodeExprsimple op1, NodeExprsimple op2, int[] lc) {
        Simbol operand1 = validateOperand(ts, op1, lc);
        Simbol operand2 = validateOperand(ts, op2, lc);

        if (operand1 == null || operand2 == null) {
            ErrorLogger.logSemanticError(lc,"Operands are not valid.");
            return false;
        }

        // Check type compatibility
        if (!typeMatches(operand1.getTipus(), operand2.getTipus())) {
             ErrorLogger.logSemanticError(lc,"Operand types do not match: " +
                op1.getValor() + " (" + operand1.getTipus() + ") and " +
                op2.getValor() + " (" + operand2.getTipus() + ")");
                return false;
        }

        return true;
    }

    public static boolean validateCondicio(SymbolTable ts, NodeExprsimple operand1, NodeExprsimple operand2, String ID, int[] lc){
        if (operand1 != null) {
            if(operand1.getTipus().equals(tipusexpr.id)){
                if(validateVariableExists(ts, operand1.getValor(), lc)==null) return false;
            }
            
            if(operand2.getTipus().equals(tipusexpr.id)){
                if(validateVariableExists(ts, operand2.getValor(), lc)==null) return false;
            }
    
            if(!validateBinaryOperands(ts, operand2, operand1, lc)) return false;
        }else{
            if(validateVariableExists(ts, ID, lc)==null) return false;
        }
        return true;
    }
}
