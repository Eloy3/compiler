package back_end;

public class Instruction3a {

    private Operation operation;
    private String operand1;
    private String operand2;
    private String desti;
    
    public Instruction3a(Operation Operation, String operand1, String operand2, String desti) {
        this.operation = Operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;

    }
    
    public void modInstruccion(Operation codi, String operand1, String operand2, String desti){
        this.operation = codi;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String getDestiny() {
        return desti;
    }

    public boolean isConditional(){
        return operation==Operation.IF || operation==Operation.IFIGUAL || operation==Operation.IFMAJOR ||
               operation==Operation.IFMENOR || operation==Operation.IFDIFERENT;
    }

    public boolean isAritmethic(){
        return operation==Operation.SUMA || operation==Operation.RESTA || operation==Operation.MULTIPLICACIO ||
                operation==Operation.DIVISIO  || operation==Operation.AND || operation==Operation.OR;
    }

    public boolean isParam(){
        return operation == Operation.PARAM_C || operation == Operation.PARAM_S;
    }

    @Override
    public String toString() {
        return  " | " + operation + " | "  + operand1 + " | " + operand2 + " | " + desti + " |";
    }

    public void setOperation(Operation codi) {
        this.operation = codi;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public void setDesti(String desti) {
        this.desti = desti;
    }
}
