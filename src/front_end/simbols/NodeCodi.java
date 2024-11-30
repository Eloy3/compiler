package front_end.simbols;

public class NodeCodi extends NodeBase {
    private NodeProcedures procedures;
    private NodePrincipal principal;

    public NodeCodi(NodeProcedures procedures, NodePrincipal principal) {
        super("Codi", 0);
        this.procedures = procedures;
        this.principal = principal;
        generateCode();
        tp.calculateLocalVarSize(tv);
        tancaFitxers();
    }

    public void generateCode() {
        if (procedures != null) {
            procedures.generateCode();
        }

        if (principal != null) {
            principal.generateCode();
        }
    }
}
