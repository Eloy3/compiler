package front_end.simbols;

import java.util.List;

import front_end.simbols.Array.NodeValorTaula;

public class NodeExprsimple extends NodeBase {
    public enum tipusexpr {id, ent, bool, procediment, arrayValue, text}
    private tipusexpr tipus;
    private String valor;
    private int[] lineCode;
    private boolean isNegative = false;
    //just for array index
    private List<String> pos;

    public NodeExprsimple(tipusexpr t, String v, int[] l) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
        this.lineCode = l;
    }

    public NodeExprsimple(NodeValorTaula v, int[] l) {
        super("Exprsimple", 0);
        this.tipus = tipusexpr.arrayValue;
        this.valor = v.getId();
        this.lineCode = l;
        this.pos = v.getIndexes();
    }

    public NodeExprsimple(String negative, tipusexpr t, String v, int[] l) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
        this.lineCode = l;
        this.isNegative = true;
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

    public void setTipus(tipusexpr tipus) {
        this.tipus = tipus;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int[] getLineCode() {
        return lineCode;
    }

    public void setLineCode(int[] lineCode) {
        this.lineCode = lineCode;
    }

    public List<String> getPos() {
        return pos;
    }

    public void setPos(List<String> pos) {
        this.pos = pos;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean isNegative) {
        this.isNegative = isNegative;
    }
    
}
