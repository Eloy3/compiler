
package compilador.simbols;


public class NodeOperador_expr extends NodeBase{
    
    private String tipus;
    
    public NodeOperador_expr(String a) {
        super("NodeOperador_expr", 0);
        this.tipus = a;
    }

    public String getTipus() {
        return tipus;
    }

}
