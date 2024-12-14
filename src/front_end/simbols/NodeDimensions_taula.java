package front_end.simbols;

public class NodeDecl_dimensio extends NodeBase {
    private int dimensio = 0;
    private int[] lineCode;

    public NodeDecl_dimensio(NodeDecl_dimensio a, int[] l){
        super("Decl_dimensio", 0);
        this.dimensio += 1;
        if(a!=null){
            this.dimensio += a.getDimensio();
        }
        this.lineCode = l;
    }

    public int getDimensio() {
        return dimensio;
    }

    public void setDimensio(int dimensio) {
        this.dimensio = dimensio;
    }

    public int[] getLineCode() {
        return lineCode;
    }

    public void setLineCode(int[] lineCode) {
        this.lineCode = lineCode;
    }

    
}
