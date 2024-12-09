package front_end.simbols;

public class NodeAssignacio_dimensio extends NodeBase{

    private NodeExprsimple exprsimple;
    private NodeAssignacio_dimensio assignacio_dimension;
    private int[] l;

    public NodeAssignacio_dimensio(NodeExprsimple a, int[] l){
        super("Assignacio_dimensio",0);
        this.exprsimple = a;
        this.l = l;
    }

    public NodeAssignacio_dimensio(NodeAssignacio_dimensio a, NodeExprsimple b, int[] l){
        super("Assignacio_dimensio",0);
        this.assignacio_dimension = a;
        this.exprsimple = b;
        this.l = l;
    }
}
