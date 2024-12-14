package front_end.simbols;

public class NodeInic_element_taula extends NodeBase{

    private Node_Indexos_taula inic2;
    private NodeExprsimple exprsimple;
    private int[] lineCode;

    public NodeInic_element_taula(Node_Indexos_taula inic2, NodeExprsimple exprsimple, int[] l) {
        super("Taula_inic", 0);
        this.inic2 = inic2;
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }

}
