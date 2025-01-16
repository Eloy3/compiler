package front_end.simbols;

public class NodeIndexos_taula extends NodeBase{

    private NodeExprsimple exprsimple;
    private int[] lineCode;
    private NodeIndexos_taula index;

    public NodeIndexos_taula(NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }

    public NodeIndexos_taula(NodeIndexos_taula inic2, NodeExprsimple exprsimple, int[] l) {
        super("T_inic2", 0);
        this.exprsimple = exprsimple;
        this.lineCode = l;
        this.index = inic2;
    }
    
}
