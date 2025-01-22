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
        // First, generate the code for the current sentencia
        if (sentenciaf != null) {
            sentenciaf.generateCode();
        }

        // Then, generate the code for the next set of sentencies (if any)
        if (blocf2 != null) {
            blocf2.generateCode();
        }
    }
}
