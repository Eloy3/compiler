
package front_end.simbols;

import front_end.simbols.NodeExprsimple.tipusexpr;
import util.Util;

/**
 *
 * @author Eloy
 */
public class NodeCondicio extends NodeBase{
    
    private NodeExprsimple operand1;
    private NodeExprsimple operand2;
    private NodeOperador_cond operador;
    private int[] lc;
    
    private String ID;
    
    public NodeCondicio(String id, int[] lc) {
        super("NodeCondicio", 0);
        ID = id;
        this.lc = lc;
    }
    
    public NodeCondicio(NodeExprsimple v, NodeOperador_cond o, NodeExprsimple v1, int[] lc) {
        super("NodeCondicio", 0);
        operand1 = v;
        operand2 = v1;
        operador = o;
        this.lc = lc;
    }

    public boolean generateCodeID(){
        if(Util.validateVariableExists(ts, ID, lc)==null) return false;
        cta.generateCode(ID + " then ");
        return true;
    }

    public boolean generateCodeOperador(){
        if(operand1.getTipus().equals(tipusexpr.id)){
            if(Util.validateVariableExists(ts, operand1.getValor(), lc)==null) return false;
        }
        
        if(operand2.getTipus().equals(tipusexpr.id)){
            if(Util.validateVariableExists(ts, operand2.getValor(), lc)==null) return false;
        }

        if(!Util.validateBinaryOperands(ts, operand2, operand1, lc)) return false;

        cta.generateCode(operand1.getValor()+" ");
        cta.generateCode(operador.getOperador()+ " ");
        cta.generateCode(operand2.getValor() + " ");
        return true;
    }

    public NodeOperador_cond getOperador() {
        return operador;
    }

    public NodeExprsimple getOperand1() {
        return operand1;
    }

    public NodeExprsimple getOperand2() {
        return operand2;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    
}
