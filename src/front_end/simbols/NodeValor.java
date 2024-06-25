
package compilador.simbols;


public class NodeValor extends NodeBase{
    
    public enum exprsimple {id, enter, boolea, tupla}
    private exprsimple tipus;
    private String valor;
    
    public NodeValor(exprsimple t, String v){
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
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
