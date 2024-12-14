package front_end.simbols;

public class Node_Indexos_taula extends NodeBase{

    private NodeExprsimple exprsimple;
    private int[] lineCode;
    private Node_Indexos_taula inic2;

    public Node_Indexos_taula(NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }

    public Node_Indexos_taula(Node_Indexos_taula inic2, NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
        this.inic2 = inic2;
    }
    
}
