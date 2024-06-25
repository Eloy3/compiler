

package compilador.simbols;


public class NodeExprsimple extends NodeBase{
    public enum exprsimple {id, enter, boolea, tupla}
    private exprsimple tipus;
    private String valor;
    private NodeExprtupla exprtupla;
    
    public NodeExprsimple(exprsimple t, String v){
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
    }
    
    public NodeExprsimple(exprsimple t, NodeExprtupla v){
        super("Exprsimple", 0);
        this.tipus = t;
        exprtupla = v;
    }

    public exprsimple getTipus() {
        return tipus;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
    
    
}
