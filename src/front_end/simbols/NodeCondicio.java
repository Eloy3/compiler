package front_end.simbols;

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
        cta.generateCode(ID + " then ");
        return true;
    }

    public boolean generateCodeOperador(){
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
