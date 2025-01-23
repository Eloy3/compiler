package front_end.simbols.Procedure;

import front_end.simbols.NodeBase;
import front_end.simbols.NodeExprsimple;

public class NodeArg extends NodeBase{
    private NodeArg2 arg;
    private NodeExprsimple exprsimple;
    
    public NodeArg(NodeExprsimple exprsimple, NodeArg2 a){
        super("Arg",0);
        this.arg = a;
        this.exprsimple = exprsimple;
    }

    public NodeArg(){
        super("Arg",0);
    }
    public NodeArg getNext() {
        return arg != null ? arg.getArg() : null;
    }

    public NodeArg2 getArg() {
        return arg;
    }

    public void setArg(NodeArg2 arg) {
        this.arg = arg;
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }

    public void setExprsimple(NodeExprsimple exprsimple) {
        this.exprsimple = exprsimple;
    }

    public NodeExprsimple.tipusexpr getTipus(){
        return exprsimple.getTipus();
    }
    
}
