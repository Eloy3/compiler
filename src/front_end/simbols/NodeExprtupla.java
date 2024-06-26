

package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeExprtupla extends NodeBase{
    
    private NodeParam param;
    
    public NodeExprtupla(NodeParam p){
        super("Exprtupla",0);
        this.param = p;
    }
}
