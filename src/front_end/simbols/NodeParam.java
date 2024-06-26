

package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeParam extends NodeBase{
    private NodeParam2 param;
    private NodeTipus tipus;
    private String id;
    private NodeExprsimple v;
    
    public NodeParam(NodeTipus tipus, String id, NodeExprsimple v, NodeParam2 p){
        super("Param2",0);
        this.param = p;
        this.id = id;
        this.tipus = tipus;
        this.v = v;
    }
    public NodeParam(){
        super("Param2",0);
    }
}
