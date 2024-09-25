package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeBlocf extends NodeBase{

    private NodeSentencia sentenciaf;
    private NodeBlocf2 blocf2;
    private NodeBucle nodeBucle;
    
    public NodeBlocf(NodeBucle nodeBucle) {
        super("Blocf", 0);
        this.nodeBucle = nodeBucle;
    }

    public NodeBlocf(NodeSentencia a, NodeBlocf2 b){
        super("Blocf", 0);
        sentenciaf = a;
        blocf2 = b;
    }
}
