package front_end.simbols.Structure;

import front_end.simbols.NodeBase;

public class NodeSentencies extends NodeBase {

    private NodeSentencia sentencia;
    private NodeSentencies2 sentencies2;

    public NodeSentencies(NodeSentencia sentencia, NodeSentencies2 sentencies2) {
        super("Sentencies", 0);
        this.sentencia = sentencia;
        this.sentencies2 = sentencies2;
    }

    public void generateCode() {
        if (sentencia != null) {
            sentencia.generateCode();
        }

        if (sentencies2 != null) {
            sentencies2.generateCode();
        }
    }
}
