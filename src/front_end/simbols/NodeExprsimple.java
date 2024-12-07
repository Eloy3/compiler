package front_end.simbols;

import data_structures.Procedure;
public class NodeExprsimple extends NodeBase {
    public enum tipusexpr {id, ent, bool, procediment}
    private tipusexpr tipus;
    private String valor;
    private NodeCrida_funcio procedure;

    public NodeExprsimple(tipusexpr t, String v) {
        super("Exprsimple", 0);
        this.tipus = t;
        this.valor = v;
    }

    public NodeExprsimple(NodeCrida_funcio a) {
        super("Exprsimple", 0);
        this.tipus = tipusexpr.procediment;
        this.valor = a.getResult();
        this.procedure = a;
    }

    public void generateCodeProcedure(){
        procedure.generateCode();
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

    public NodeCrida_funcio getProcedure() {
        return procedure;
    }

    public void setProcedure(NodeCrida_funcio a) {
        this.procedure = a;
    }

    public String tipusProcediment(){
        String nom = procedure.getFunctionName();
        Procedure p = tp.getProc(nom);
        return p.getType_return().toString();
    }

    public String getNomProcediment(){
        return procedure.getFunctionName();
    }
    @Override
    public String toString() {
        return valor;
    }
}
