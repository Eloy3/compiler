package front_end.simbols.Procedure;

import front_end.simbols.NodeBase;

public class NodeArg2 extends NodeBase{
    private NodeArg arg;
    
    public NodeArg2(NodeArg a){
        super("Arg2",0);
        this.arg = a;
    }

    public NodeArg2(){
        super("Arg2",0);
    }

    public NodeArg getArg() {
        return arg;
    }

    public void setArg(NodeArg arg) {
        this.arg = arg;
    }

}
