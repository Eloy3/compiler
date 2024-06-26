/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package back_end;

public class ThreeAdressInstruction {

    private Operacio codi;
    private String operand1;
    private String operand2;
    private String desti;
    
    public Instruccioc3a(Operacio operacio, String operand1, String operand2, String desti) {
        this.codi = operacio;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;

    }
    
    public void modInstruccion(Operacio codi, String operand1, String operand2, String desti){
        this.codi = codi;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.desti = desti;
    }

    public Operacio getOperation() {
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
        return codi==Operacio.IF || codi==Operacio.IFIGUAL || codi==Operacio.IFMAJOR ||
                codi==Operacio.IFMENOR || codi==Operacio.IFMAJORIGUAL ||
                codi==Operacio.IFMENORIGUAL || codi==Operacio.IFDIFERENT;
    }

    public boolean esArtim(){
        return codi==Operacio.SUMA || codi==Operacio.RESTA || codi==Operacio.MULTIPLICACIO ||
                codi==Operacio.DIVISIO  || codi==Operacio.AND || codi==Operacio.OR;
    }

    public boolean esParam(){
        return codi == Operacio.PARAM_C || codi == Operacio.PARAM_S;
    }

    @Override
    public String toString() {
        return  " | " + codi + " | "  + operand1 + " | " + operand2 + " | "
                + desti + " |";
    }
}
