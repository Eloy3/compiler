package front_end.simbols.Array;

import front_end.simbols.NodeBase;

public class NodeAssignacio_memoria extends NodeBase {
    
    private NodeEspecificacio_dimensio especificacio_dimensio;
    private int[] l;

    public NodeAssignacio_memoria(NodeEspecificacio_dimensio a, int[] l){
        super("AssignacioMemoria", 0);
        this.especificacio_dimensio = a;
        this.l = l;
    }

    public NodeEspecificacio_dimensio getEspecificacio_dimensio() {
        return especificacio_dimensio;
    }

    public void setEspecificacio_dimensio(NodeEspecificacio_dimensio especificacio_dimensio) {
        this.especificacio_dimensio = especificacio_dimensio;
    }

    public int[] getL() {
        return l;
    }

    public void setL(int[] l) {
        this.l = l;
    }

    
}
