
package front_end.simbols;

public class NodeLlistaValors extends NodeBase{
    
    private NodeValor Valor;
    private NodeLlistaValors LlistaValors;

    public NodeLlistaValors(NodeValor v) {
        super("LlistaValors", 0);
        this.Valor = v;
    }

    public NodeLlistaValors(NodeValor v, NodeLlistaValors lv) {
        super("F", 0);
        this.Valor = v;
        this.LlistaValors = lv;
    }

    public NodeLlistaValors getLlistaValors() {
        return LlistaValors;
    }

    public void setLlistaValors(NodeLlistaValors LlistaValors) {
        this.LlistaValors = LlistaValors;
    }

    public NodeValor getValor() {
        return Valor;
    }

    public void setValor(NodeValor Valor) {
        this.Valor = Valor;
    }
    

}
