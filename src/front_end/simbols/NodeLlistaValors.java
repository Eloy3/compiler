
package front_end.simbols;

public class NodeLlistaValors extends NodeBase{
    
    private NodeExprsimple Valor;
    private NodeLlistaValors LlistaValors;

    public NodeLlistaValors(NodeExprsimple v) {
        super("LlistaValors", 0);
        this.Valor = v;
    }

    public NodeLlistaValors(NodeExprsimple v, NodeLlistaValors lv) {
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

    public NodeExprsimple getValor() {
        return Valor;
    }

    public void setValor(NodeExprsimple Valor) {
        this.Valor = Valor;
    }
    

}
