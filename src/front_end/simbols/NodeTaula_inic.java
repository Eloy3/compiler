package front_end.simbols;

public class NodeTaula_inic extends NodeBase{

    private NodeT_inic2 inic2;
    private NodeExprsimple exprsimple;
    private int[] lineCode;

    public NodeTaula_inic(NodeT_inic2 inic2, NodeExprsimple exprsimple, int[] l) {
        super("Taula_inic", 0);
        this.inic2 = inic2;
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }

}
