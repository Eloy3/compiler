
package front_end.simbols.Procedure;

import front_end.simbols.NodeBase;

/**
 *
 * @author Eloy
 */
public class NodeParam2 extends NodeBase{
    private NodeParam param;
    
    public NodeParam2(NodeParam p){
        super("Param2",0);
        this.param = p;
    }
    public NodeParam2(){
        super("Param2",0);
    }
    
    public NodeParam getParam() {
        return param;
    }
}
