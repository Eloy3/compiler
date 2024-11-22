package front_end.simbols;

public class NodeExprsimple extends NodeBase {
    public enum exprsimple {id, enter, boolea}
    private exprsimple tipus;
    private String valor;

    public NodeExprsimple(exprsimple t, String v) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
    }

    public NodeExprsimple(exprsimple t) {
        super("Exprsimple", 0);
        this.tipus = t;
    }

    public exprsimple getTipus() {
        return tipus;
    }

    public String getTipusAsString() {
        return tipus.name(); // Converts enum to String
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
