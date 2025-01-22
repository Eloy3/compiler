package front_end.simbols.Loops;

import errors.ErrorLogger;
import front_end.simbols.NodeBase;
import front_end.simbols.Simbol;

public class NodeComportamentv1 extends NodeBase {

    private String id;
    private Comportament comportament; // Use the enum instead of a string
    private int[] lc;

    public NodeComportamentv1(){
        super("Comportamentv1", 0);
    }

    public NodeComportamentv1(String id, Comportament comportament, int[] lc){
        super("Comportamentv1", 0);
        this.id = id;
        this.comportament = comportament; // Store the operator
        this.lc = lc;
        
    }

    public void generateCode() {
        // Check if the variable exists in the symbol table
        Simbol variable = ts.get(id);
        if (variable == null) {
            ErrorLogger.logSemanticError(lc,"La variable '" + id + "' no existeix.");
        } else {
            String tempVar;

            // Check the type of operation
            switch (comportament) {
                case INCREMENT:
                    // Generate TAC for increment: temp = variable + 1
                    tempVar = cta.newTempVar(variable.getTipus().toString(), null);
                    cta.generateCode(tempVar + " = " + id + " + 1\n");
                    cta.generateCode(id + " = " + tempVar + "\n");
                    break;

                case DECREMENT:
                    // Generate TAC for decrement: temp = variable - 1
                    tempVar = cta.newTempVar(variable.getTipus().toString(), null);
                    cta.generateCode(tempVar + " = " + id + " - 1\n");
                    cta.generateCode(id + " = " + tempVar + "\n");
                    break;
            }

            // Clean up the temp variable for future use
            cta.setTemp_id(null);
        }

        
    }

    public String getId() {
        return id;
    }

    public Comportament getComportament() {
        return comportament;
    }
}
