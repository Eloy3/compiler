package front_end.simbols;

public class NodeValors_taula {
    private NodeExprsimple exprsimple;
    private NodeValors_taula elements_dimensionals;
    private int[] codeLine;

    public NodeValors_taula(NodeExprsimple a, NodeValors_taula b, int[] l){
        this.exprsimple = a;
        this.elements_dimensionals = b;
        this.codeLine = l;
    }

    public NodeValors_taula(NodeExprsimple a, int[] l){
        this.exprsimple = a;
        this.codeLine = l;
    }
}
