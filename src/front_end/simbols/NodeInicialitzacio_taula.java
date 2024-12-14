package front_end.simbols;

public class NodeInicialitzacio_taula extends NodeBase {
    private NodeValors_taula elements_dimensional;
    private NodeAssignacio_memoria assignacio_memoria;
    private int[] lineCode;

    public NodeInicialitzacio_taula (NodeValors_taula a, int[] l){
        super("Assignacio_dimensional", 0);
        this.elements_dimensional = a;
        this.lineCode = l;
    }

    public NodeInicialitzacio_taula(NodeAssignacio_memoria a, int[] l){
        super("Assignacio_dimensional", 0);
        this.assignacio_memoria = a;
        this.lineCode = l;
    }

}
