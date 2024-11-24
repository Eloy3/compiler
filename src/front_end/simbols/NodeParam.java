

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
    
    
    public NodeParam(NodeTipus tipus, String id, NodeParam2 p){
        super("Param",0);
        this.param = p;
        this.id = id;
        this.tipus = tipus;
    }
    public NodeParam(){
        super("Param",0);
    }

    public NodeParam getNext() {
        return param != null ? param.getParam() : null;
    }

    public NodeParam2 getParam() {
        return param;
    }
    public void setParam(NodeParam2 param) {
        this.param = param;
    }
    public NodeTipus getTipus() {
        return tipus;
    }
    public void setTipus(NodeTipus tipus) {
        this.tipus = tipus;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public NodeExprsimple getV() {
        return v;
    }
    public void setV(NodeExprsimple v) {
        this.v = v;
    }
}
