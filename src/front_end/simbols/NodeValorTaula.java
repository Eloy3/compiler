package front_end.simbols;
import front_end.simbols.NodeExprsimple.tipusexpr;

public class NodeValorTaula extends NodeBase {
    private NodeExprsimple exprismple;
    private String id;
    private final int[] lineCode;

    public NodeValorTaula(String id, NodeExprsimple e, int[] l){
        super("ValorTaula", 0);
        this.id = id;
        this.exprismple = e;
        this.lineCode = l;
    }

    public String getValue() {
        return exprismple.getValor();
    }
    public NodeExprsimple getExprismple() {
        return exprismple;
    }

    public void setExprismple(NodeExprsimple exprismple) {
        this.exprismple = exprismple;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int[] getLineCode() {
        return lineCode;
    }

    

}
