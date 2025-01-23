package util;

import java.util.ArrayList;
import java.util.List;

import back_end.IntermediateCode;
import data_structures.SymbolTable;
import front_end.simbols.Simbol;

public abstract class TacUtil {

    public static void inicibucle(IntermediateCode cta) {
        String startLabel = cta.newLabel();
        cta.push(cta.getStart_stack(), startLabel);
        cta.generateCode(startLabel + ":skip\n");
    }

    public static void etiquetacond(IntermediateCode cta) {
        cta.generateCode("if ");
        String trueLabel = cta.newLabel();
        cta.push(cta.getTrue_stack(), trueLabel);

        String falseLabel = cta.newLabel();
        cta.push(cta.getFalse_stack(), falseLabel);
    }

    public static void condiciobot(IntermediateCode cta, boolean inverter) {
        String trueLabel = cta.getTop(cta.getTrue_stack());
        String falseLabel = cta.getTop(cta.getFalse_stack());

        if (inverter) {
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode("goto " + trueLabel + "\n");
            cta.generateCode(trueLabel + ":skip\n");
            cta.pop(cta.getTrue_stack());
        } else {
            cta.generateCode("goto " + trueLabel + "\n");
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode(trueLabel + ":skip\n");
            cta.pop(cta.getTrue_stack());
        }
        cta.setTemp_id(null);
    }

    public static void retornabucle(IntermediateCode cta) {
        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String falseLabel = cta.getTop(cta.getFalse_stack());
        cta.generateCode(falseLabel + ":skip\n");
    }

    public static void procedureResultToVariable(IntermediateCode cta, SymbolTable ts, String variable, String variableType) {
        /**
         * Transfers the result of a procedure to a specified variable.
         *
         * @param variable     The name of the variable to store the procedure result.
         * @param variableType The type of the variable.
         * 
         */
        String tempVar = cta.newTempVar(variableType, null);
        cta.generateCode(tempVar + " = retInt\n");
        ts.insertElement(variable, variableType, tempVar);
        cta.generateCode(variable + " = " + tempVar + "\n");
        cta.newVar(variable, variableType);
    }

    /**
     * Generates code for an indexed assignment operation.
     *
     * @param cta The Three Address Code generator.
     * @param ts The symbol table.
     * @param id The identifier of the array.
     * @param value The value to be assigned.
     * @param tipus The type of the value.
     * @param indexes The indexes at which the value is to be assigned.
     * array[i][j]...[n] = value
     */
    public static void generateInd_ass(IntermediateCode cta, SymbolTable ts, String id, String value, String tipus, List<String> indexes) {
        /* Simbol idSimbol = ts.get(id);
        ArrayList<Integer> dimensions = idSimbol.getArrayDimensions();

        //reverse the values in the list
        ArrayList<Integer> reversed = new ArrayList<>();
        for (int i = dimensions.size() - 1; i >= 0; i--) {
            reversed.add(dimensions.get(i));
        }
        dimensions = reversed;


        //calculate whichever position name[i][j]...[n] is
        ArrayList<Integer> dimensionsValue = new ArrayList<>();
        int elements = 1;
        for (int i = dimensions.size() - 1; i >= 0; i--) {
            dimensionsValue.add(elements);
            elements = dimensions.get(i) * elements;
        }
        
        String indextmp = cta.newTempVar("ent");
        for(int i = 0; i < indexes.size(); i++){
            indextmp = cta.newTempVar("ent");
            cta.generateCode("assign", indextmp, indexes.get(i), ts);
            cta.generateCode(indextmp + " = " + indextmp + " * " + dimensionsValue.get(i) + "\n");
        } */
        String index = generateIndexes(cta, ts, id, indexes);
        String tempVar = cta.newTempVar(tipus);
        cta.generateCode(tempVar + " = " + value + "\n");
        cta.generateCode("assign", index, tempVar, ts);
    }

    public static String generateIndexes(IntermediateCode cta, SymbolTable ts, String id, List<String> indexes) {
        Simbol idSimbol = ts.get(id);
        ArrayList<Integer> dimensions = idSimbol.getArrayDimensions();

        //reverse the values in the list
        ArrayList<Integer> reversed = new ArrayList<>();
        for (int i = dimensions.size() - 1; i >= 0; i--) {
            reversed.add(dimensions.get(i));
        }
        dimensions = reversed;


        //calculate whichever position name[i][j]...[n] is
        ArrayList<Integer> dimensionsValue = new ArrayList<>();
        int elements = 1;
        for (int i = dimensions.size() - 1; i >= 0; i--) {
            dimensionsValue.add(elements);
            elements = dimensions.get(i) * elements;
        }
        
        String indextmp = cta.newTempVar("ent");
        cta.generateCode(indextmp + " = 0\n");
        String indextmp2 = cta.newTempVar("ent");
        for(int i = 0; i < indexes.size(); i++){
            cta.generateCode(indextmp2 + " = " + indexes.get(i) + " * " + dimensionsValue.get(i) + "\n");
            cta.generateCode(indextmp + " = " + indextmp + " + " + indextmp2 + "\n");
            indextmp2 = cta.newTempVar("ent");
        }
        return id+"["+indextmp+"]";
    }

    public static void generateInd_val(IntermediateCode cta, SymbolTable ts, String array, String target, String tipus, List<String> indexes, int[] lineCode) {
        Simbol targetSimbol = Util.validateVariableExists(ts, target, lineCode);
        if(targetSimbol == null) return;
        String index = generateIndexes(cta, ts, array, indexes);
        String tempVar = cta.newTempVar(tipus);
        cta.generateCode(tempVar + " = " + index + "\n");
        cta.generateCode("assign", target, tempVar, ts);
        cta.generateNewVarAssign(targetSimbol, tempVar, tempVar, ts);
    }
}
