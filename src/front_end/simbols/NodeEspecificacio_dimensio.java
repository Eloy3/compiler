package front_end.simbols;

public class NodeEspecificacio_dimensio extends NodeBase{

    private NodeExprsimple exprsimple;
    private NodeEspecificacio_dimensio assignacio_dimension;
    private int[] l;

    public NodeEspecificacio_dimensio(NodeExprsimple a, int[] l){
        super("Assignacio_dimensio",0);
        this.exprsimple = a;
        this.l = l;
    }

    public NodeEspecificacio_dimensio(NodeEspecificacio_dimensio a, NodeExprsimple b, int[] l){
        super("Assignacio_dimensio",0);
        this.assignacio_dimension = a;
        this.exprsimple = b;
        this.l = l;
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }

    public void setExprsimple(NodeExprsimple exprsimple) {
        this.exprsimple = exprsimple;
    }

    public NodeEspecificacio_dimensio getAssignacio_dimension() {
        return assignacio_dimension;
    }

    public void setAssignacio_dimension(NodeEspecificacio_dimensio assignacio_dimension) {
        this.assignacio_dimension = assignacio_dimension;
    }

    public int[] getL() {
        return l;
    }

    public void setL(int[] l) {
        this.l = l;
    }

    
}
