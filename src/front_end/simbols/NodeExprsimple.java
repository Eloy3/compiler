package front_end.simbols;

import errors.ErrorLogger;

public class NodeExprsimple extends NodeBase {
    public enum tipusexpr {id, ent, bool, procediment, arrayValue}
    private tipusexpr tipus;
    private String valor;
    private int[] lineCode;
    //just for array index
    private int pos;

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

        try {
            this.pos = Integer.parseInt(v.getValue());
        } catch (NumberFormatException e) {
            ErrorLogger.logSemanticError(lineCode,"El valor de l'index no és un enter.");
        }
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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    
}
