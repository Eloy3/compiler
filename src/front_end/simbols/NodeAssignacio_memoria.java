package front_end.simbols;

public class NodeAssignacio_memoria extends NodeBase {
    
    private NodeEspecificacio_dimensio assignacio_dimensio;
    private int[] l;

    public NodeAssignacio_memoria(NodeEspecificacio_dimensio a, int[] l){
        super("AssignacioMemoria", 0);
        this.assignacio_dimensio = a;
        this.l = l;
    }
}
