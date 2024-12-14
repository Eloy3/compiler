package front_end.simbols;

public class NodeValors_taula {
    private NodeExprsimple exprsimple;
    private NodeValors_taula valors_taula;
    private int[] codeLine;

    public NodeValors_taula(NodeExprsimple a, NodeValors_taula b, int[] l){
        this.exprsimple = a;
        this.valors_taula = b;
        this.codeLine = l;
    }

    public NodeValors_taula(NodeExprsimple a, int[] l){
        this.exprsimple = a;
        this.codeLine = l;
    }
}
