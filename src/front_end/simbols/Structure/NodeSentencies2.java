package front_end.simbols.Structure;

import front_end.simbols.NodeBase;

public class NodeSentencies2 extends NodeBase {

    private NodeSentencies nodeSentencies;

    public NodeSentencies2(NodeSentencies nodeSentencies) {
        super("Sentencies2", 0);
        this.nodeSentencies = nodeSentencies;
    }

    public NodeSentencies2() {
        super("Sentencies2", 0);
        this.nodeSentencies = null;
    }

    public void generateCode() {
        if (nodeSentencies != null) {
            nodeSentencies.generateCode();
        }
    }
}
