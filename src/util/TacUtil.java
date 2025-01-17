package util;

import java.util.List;

import back_end.generate_code.ThreeAdressCode;
import data_structures.SymbolTable;

public abstract class TacUtil {

    public static void inicibucle(ThreeAdressCode cta) {
        String startLabel = cta.newLabel();
        cta.push(cta.getStart_stack(), startLabel);
        cta.generateCode(startLabel + ":skip\n");
    }

    public static void etiquetacond(ThreeAdressCode cta) {
        cta.generateCode("if ");
        String trueLabel = cta.newLabel();
        cta.push(cta.getTrue_stack(), trueLabel);

        String falseLabel = cta.newLabel();
        cta.push(cta.getFalse_stack(), falseLabel);
    }

    public static void condiciobot(ThreeAdressCode cta, boolean inverter) {
        String trueLabel = cta.getTop(cta.getTrue_stack());
        String falseLabel = cta.getTop(cta.getFalse_stack());

        if (inverter) {
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode("goto " + trueLabel + "\n");
            cta.pop(cta.getTrue_stack());
            cta.generateCode(trueLabel + ":skip\n");
        } else {
            cta.generateCode("goto " + trueLabel + "\n");
            cta.pop(cta.getTrue_stack());
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode(trueLabel + ":skip\n");
        }
        cta.setTemp_id(null);
    }

    public static void retornabucle(ThreeAdressCode cta) {
        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String falseLabel = cta.getTop(cta.getFalse_stack());
        cta.generateCode(falseLabel + ":skip\n");
    }

    public static void procedureResultToVariable(ThreeAdressCode cta, SymbolTable ts, String variable, String variableType) {
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
     * @param indexes The indices at which the value is to be assigned.
     * array[i][j]...[n] = value
     */
    public static void generateInd_ass(ThreeAdressCode cta, SymbolTable ts, String id, String value, String tipus, List<String> indexes) {
        String tempVar = cta.newTempVar(tipus, value);
        cta.generateCode(tempVar + " = " + value + "\n");
        cta.generateCode("assign", generateIndexes(id, indexes), tempVar, ts);
    }

    public static String generateIndexes(String id, List<String> indexes) {
        StringBuilder indexBuilder = new StringBuilder();
        for (String index : indexes) {
            indexBuilder.append("[").append(index).append("]");
        }
        return id + indexBuilder.toString();
    }
}
