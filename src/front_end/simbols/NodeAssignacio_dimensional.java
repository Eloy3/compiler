package front_end.simbols;

public class NodeAssignacio_dimensional extends NodeBase {
    private NodeElements_dimensional elements_dimensional;
    private NodeAssignacio_memoria assignacio_memoria;
    private int[] lineCode;

    public NodeAssignacio_dimensional (NodeElements_dimensional a, int[] l){
        super("Assignacio_dimensional", 0);
        this.elements_dimensional = a;
        this.lineCode = l;
    }

    public NodeAssignacio_dimensional(NodeAssignacio_memoria a, int[] l){
        super("Assignacio_dimensional", 0);
        this.assignacio_memoria = a;
        this.lineCode = l;
    }

}
