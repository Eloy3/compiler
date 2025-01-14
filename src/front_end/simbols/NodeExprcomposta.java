

package front_end.simbols;

public class NodeExprcomposta extends NodeBase{
    
    private NodeExprsimple exprsimple;
    private NodeOperador_expr operador;
    private NodeExprcomposta exprcomposta;
    private int[] linecode;
    
    public NodeExprcomposta(NodeExprsimple a, NodeOperador_expr o, NodeExprcomposta b, int[] lc){
        super("Exprcomposta",0);
        this.exprsimple = a;
        this.operador = o;
        this.exprcomposta = b;
        linecode = lc;
    }

    public NodeExprcomposta(NodeExprsimple a, int[] lc){
        super("Exprcomposta",0);
        this.exprsimple = a;
        linecode = lc;
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

    public NodeExprcomposta getExprcomposta() {
        return exprcomposta;
    }

    public void setExprcomposta(NodeExprcomposta b) {
        this.exprcomposta = b;
    }
    
}
