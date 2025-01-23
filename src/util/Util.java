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
import front_end.simbols.Conditionals.NodeCondicio;

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

    public static ArrayList<NodeExprsimple> getArrayList(NodeLlistaValors valors) {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeLlistaValors aux = valors;
        while (aux != null) {
            list.add(aux.getValor());
            aux = aux.getLlistaValors();
        }
        return list;
    }

    public static String labelFormat(String s){
        //remove both quotes and spaces
        String noQuotes = s.replace("\"", "");
        String noSpaces = noQuotes.replace(" ", "_");
        //replace all non-alphanumeric characters with underscores
        String noNonAlphanumeric = noSpaces.replaceAll("[^a-zA-Z0-9]", "_");
        //replace all consecutive underscores with a single underscore
        String noConsecutiveUnderscores = noNonAlphanumeric.replaceAll("_+", "_");
        return noConsecutiveUnderscores;
    }
}

