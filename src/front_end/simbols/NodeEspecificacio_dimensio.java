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
}
