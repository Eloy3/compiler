package compilador.simbols;

/**
 *
 * @author Eloy
 */
public class NodeBlocf extends NodeBase{

    private NodeSentenciaf sentenciaf;
    private NodeBlocf2 blocf2;
    
    public NodeBlocf(NodeSentenciaf a, NodeBlocf2 b){
        super("Blocf", 0);
        sentenciaf = a;
        blocf2 = b;
    }
}
