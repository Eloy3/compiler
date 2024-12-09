package front_end.simbols;

public class NodeT_inic2 extends NodeBase{

    private NodeExprsimple exprsimple;
    private int[] lineCode;
    private NodeT_inic2 inic2;

    public NodeT_inic2(NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }

    public NodeT_inic2(NodeT_inic2 inic2, NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
        this.inic2 = inic2;
    }
    
}
