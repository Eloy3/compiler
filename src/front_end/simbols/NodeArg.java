package front_end.simbols;

public class NodeArg extends NodeBase{
    private NodeArg2 arg;
    private String id;
    
    public NodeArg(String id, NodeArg2 a){
        super("Arg",0);
        this.arg = a;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
