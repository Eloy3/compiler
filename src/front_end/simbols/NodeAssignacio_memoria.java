package front_end.simbols;

public class NodeAssignacio_memoria extends NodeBase {
    
    private NodeAssignacio_dimensio assignacio_dimensio;
    private int[] l;

    public NodeAssignacio_memoria(NodeAssignacio_dimensio a, int[] l){
        super("AssignacioMemoria", 0);
        this.assignacio_dimensio = a;
        this.l = l;
    }
}
