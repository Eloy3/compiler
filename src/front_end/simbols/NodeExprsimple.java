package front_end.simbols;

import data_structures.Procedure;
public class NodeExprsimple extends NodeBase {
    public enum tipusexpr {id, ent, bool, procediment, arrayValue}
    private tipusexpr tipus;
    private String valor;

    public NodeExprsimple(tipusexpr t, String v) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
    }

    public tipusexpr getTipus() {
        return tipus;
    }

    public String getTipusAsString() {
        return tipus.name(); 
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
