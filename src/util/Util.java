package util;

import java.util.ArrayList;

import data_structures.Procedure;
import data_structures.SymbolTable;
import data_structures.TaulaProcediments;
import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.NodeExprsimple.tipusexpr;
import front_end.simbols.NodeLlistaValors;
import front_end.simbols.Simbol;
import front_end.simbols.NodeCondicio;

public abstract class Util {
    
    public static Simbol validateVariableExists(SymbolTable ts, String varId, int[] lc) {
        if (!ts.existeixTs(varId)) {
            ErrorLogger.logSemanticError(lc,"La variable '" + varId + "' no existeix.");
            return null;
        }
        return ts.get(varId);
    }

    public static Procedure validateProcedureExists(TaulaProcediments tp, String varId, int[] lc) {
        if (tp.getProc(varId).equals(null)) {
            ErrorLogger.logSemanticError(lc,"La variable '" + varId + "' no existeix.");
            return null;
        }
        return tp.getProc(varId);
    }
    
    public static boolean typeMatches(String type1, String type2) {
        return type1 != null && type1.equalsIgnoreCase(type2);
    }

    /* public static Simbol validateOperand(SymbolTable ts, NodeExprsimple operand, int[] lc) {
        if (operand.getTipus() == NodeExprsimple.tipusexpr.id) {
            return Util.validateVariableExists(ts, operand.getValor(), lc);
        }
        // Handle literals by creating a temporary Simbol
        return new Simbol(operand.getValor(), operand.getTipusAsString());
    } */

    /* public static void validateUnaryOperand(SymbolTable ts, NodeExprsimple operand, int[] lc) {
        if (validateOperand(ts, operand, lc) == null) {
            ErrorLogger.logSemanticError(lc,"Operand '" + operand.getValor() + "' no es vàlid.");
        }
    } */

    /* public static boolean validateBinaryOperands(SymbolTable ts, NodeExprsimple op1, NodeExprsimple op2, int[] lc) {
        Simbol operand1 = validateOperand(ts, op1, lc);
        Simbol operand2 = validateOperand(ts, op2, lc);

        if (operand1 == null || operand2 == null) {
            return false;
        }

        // Check type compatibility
        if (!typeMatches(operand1.getTipus(), operand2.getTipus())) {
             ErrorLogger.logSemanticError(lc,"Els operands no tenen el mateix tipus: " +
                op1.getValor() + " (" + operand1.getTipus() + ") i " +
                op2.getValor() + " (" + operand2.getTipus() + ")");
                return false;
        }

        return true;
    } */
    public static boolean validateLoop(SymbolTable ts, NodeExprsimple expr, NodeCondicio cond, int[] lc){
        if(expr==null) return false;
        String tipus;
        if(expr.getTipus() == tipusexpr.id){
            Simbol left = Util.validateVariableExists(ts, expr.getValor(), lc);
            if (left == null) return false;
            tipus = left.getTipus();
        }else{
            tipus = expr.getTipusAsString();
        }
        
        if(cond == null) return true;

        NodeExprsimple exprA;
        while(cond != null){
            exprA = cond.getExpr();
            if (exprA.getTipus() == tipusexpr.id) {
                Simbol target = Util.validateVariableExists(ts, exprA.getValor(), lc);
                if(target == null) {
                    return false;
                }else if(!Util.typeMatches(tipus, target.getTipus())) {
                    ErrorLogger.logSemanticError(lc, "La variable '" + exprA.getValor() + "' no té el tipus esperat '" + tipus + "'.");
                    return false;
                }
            }else if(!Util.typeMatches(tipus, exprA.getTipusAsString())) {
                ErrorLogger.logSemanticError(lc, "La variable '" + exprA.getValor() + "' no té el tipus esperat '" + tipus + "'.");
                return false;
            }
            cond = cond.getCond();
        }
        return true;
    }
/*     public static boolean validateCondicio(SymbolTable ts, NodeExprsimple operand1, NodeExprsimple operand2, String ID, int[] lc){
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
    } */

    public static ArrayList<NodeExprsimple> getArrayList(NodeLlistaValors valors) {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeLlistaValors aux = valors;
        while (aux != null) {
            list.add(aux.getValor());
            aux = aux.getLlistaValors();
        }
        return list;
    }

}

