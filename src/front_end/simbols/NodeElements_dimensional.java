package front_end.simbols;

public class NodeElements_dimensional {
    private NodeExprsimple exprsimple;
    private NodeElements_dimensional elements_dimensionals;
    private int[] codeLine;

    public NodeElements_dimensional(NodeExprsimple a, NodeElements_dimensional b, int[] l){
        this.exprsimple = a;
        this.elements_dimensionals = b;
        this.codeLine = l;
    }

    public NodeElements_dimensional(NodeExprsimple a, int[] l){
        this.exprsimple = a;
        this.codeLine = l;
    }
}
