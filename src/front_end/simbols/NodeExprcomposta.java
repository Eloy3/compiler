

package front_end.simbols;

import static front_end.simbols.NodeBase.ts;


public class NodeExprcomposta extends NodeBase{
    
    private NodeExprsimple exprsimple;
    private NodeOperador_expr operador;
    private NodeExprsimple b;
    private Simbol operand1;
    private Simbol operand2;
    
    public NodeExprcomposta(NodeExprsimple a, NodeOperador_expr o, NodeExprsimple b, int[] lc){
        super("Exprcomposta",0);
        this.exprsimple = a;
        this.operador = o;
        this.b = b;
        
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }

    public void setExprsimple(NodeExprsimple a) {
        this.exprsimple = a;
    }

    public NodeOperador_expr getOperador() {
        return operador;
    }

    public void setOperador(NodeOperador_expr operador) {
        this.operador = operador;
    }

    public NodeExprsimple getB() {
        return b;
    }

    public void setB(NodeExprsimple b) {
        this.b = b;
    }

    
}
