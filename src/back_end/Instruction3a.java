/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package back_end;

public class Instruction3a {

    private Operation codi;
    private String operand1;
    private String operand2;
    private String desti;
    
    public Instruction3a(Operation Operation, String operand1, String operand2, String desti) {
        this.codi = Operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;

    }
    
    public void modInstruccion(Operation codi, String operand1, String operand2, String desti){
        this.codi = codi;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;
    }

    public Operation getOperation() {
        return codi;
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

    public boolean esCondicional(){
        return codi==Operation.IF || codi==Operation.IFIGUAL || codi==Operation.IFMAJOR ||
                codi==Operation.IFMENOR || codi==Operation.IFMAJORIGUAL ||
                codi==Operation.IFMENORIGUAL || codi==Operation.IFDIFERENT;
    }

    public boolean esArtim(){
        return codi==Operation.SUMA || codi==Operation.RESTA || codi==Operation.MULTIPLICACIO ||
                codi==Operation.DIVISIO  || codi==Operation.AND || codi==Operation.OR;
    }

    public boolean esParam(){
        return codi == Operation.PARAM_C || codi == Operation.PARAM_S;
    }

    @Override
    public String toString() {
        return  " | " + codi + " | "  + operand1 + " | " + operand2 + " | "
                + desti + " |";
    }

    public void setCodi(Operation codi) {
        this.codi = codi;
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
