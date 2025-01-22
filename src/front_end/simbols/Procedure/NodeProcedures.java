package front_end.simbols.Procedure;

import front_end.simbols.NodeBase;

public class NodeProcedures extends NodeBase {
    private NodeDecl_funcio declFunc;
    private NodeProcedures nextProcedures;

    public NodeProcedures(NodeDecl_funcio declFunc, NodeProcedures nextProcedures) {
        super("Procedures", 0);
        this.declFunc = declFunc;
        this.nextProcedures = nextProcedures;
    }

    public NodeProcedures() {
        super("Procedures", 0);
    }

    public void generateCode() {
        if (declFunc != null) {
            declFunc.generateCode();
        }

        if (nextProcedures != null) {
            nextProcedures.generateCode();
        }
    }
}
