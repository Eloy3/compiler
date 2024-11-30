package front_end.simbols;

import data_structures.Procedure;

public class NodeExprsimple extends NodeBase {
    public enum exprsimple {id, enter, boolea, procediment}
    private exprsimple tipus;
    private String valor;
    private NodeCrida_funcio a;

    public NodeExprsimple(exprsimple t, String v) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
    }

    public NodeExprsimple(NodeCrida_funcio a) {
        super("Exprsimple", 0);
        this.tipus = exprsimple.procediment;
        this.valor = a.getResult();
        this.a = a;
    }

    public void generateCodeProcedure(){
        a.generateCode();
    }
    public exprsimple getTipus() {
        return tipus;
    }

    public String getTipusAsString() {
        return tipus.name(); 
    }

    public String getValor() {
        return valor;
    }

    public NodeCrida_funcio getA() {
        return a;
    }

    public void setA(NodeCrida_funcio a) {
        this.a = a;
    }

    public String tipusProcediment(){
        String nom = a.getFunctionName();
        Procedure p = tp.getProc(nom);
        return p.getType_return().toString();
    }
    @Override
    public String toString() {
        return valor;
    }
}
