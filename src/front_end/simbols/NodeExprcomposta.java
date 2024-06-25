

package compilador.simbols;

import compilador.errors.Error_DistintTipus;
import compilador.errors.Error_VarNoExisteix;
import static compilador.simbols.NodeBase.ts;


public class NodeExprcomposta extends NodeBase{
    
    private NodeExprsimple a;
    private NodeOperador_expr operador;
    private NodeExprsimple b;
    private Simbol operand1;
    private Simbol operand2;
    
    public NodeExprcomposta(NodeExprsimple a, NodeOperador_expr o, NodeExprsimple b, int[] lc){
        super("Exprcomposta",0);
        this.a = a;
        this.operador = o;
        this.b = b;
        
    }

    public NodeExprsimple getA() {
        return a;
    }

    public void setA(NodeExprsimple a) {
        this.a = a;
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
