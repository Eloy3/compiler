package front_end.simbols.Structure;

import front_end.simbols.NodeBase;

public class NodeSentencies2 extends NodeBase {

    private NodeSentencies nodeSentencies; // Lowercase for consistency

    public NodeSentencies2(NodeSentencies nodeSentencies) {
        super("Sentencies2", 0);
        this.nodeSentencies = nodeSentencies;
    }

    public NodeSentencies2() {
        super("Sentencies2", 0);
        this.nodeSentencies = null; // Explicitly set null when no more sentences
    }

    public void generateCode() {
        // Only generate code if there's more sentences to process
        if (nodeSentencies != null) {
            nodeSentencies.generateCode();
        }
    }
}
