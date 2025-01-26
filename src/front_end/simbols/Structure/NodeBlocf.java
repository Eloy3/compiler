package front_end.simbols.Structure;

import front_end.simbols.NodeBase;

/**
 *
 * @author Eloy
 */
public class NodeBlocf extends NodeBase{

    private NodeSentencia sentenciaf;
    private NodeBlocf2 blocf2;

    public NodeBlocf(NodeSentencia a, NodeBlocf2 b){
        super("Blocf", 0);
        sentenciaf = a;
        blocf2 = b;
    }

    public void generateCode() {
        if (sentenciaf != null) {
            sentenciaf.generateCode();
        }

        if (blocf2 != null) {
            blocf2.generateCode();
        }
    }
}
