package front_end.simbols;

public class NodeDecl_taula extends NodeBase {
    private NodeTipus tipus;
    private NodeDecl_dimensio decl_dimensio;
    private NodeAssignacio_dimensional assignacio_dimensional;
    private String id;
    private int[] lineCode;

    public NodeDecl_taula(NodeTipus tipus, NodeDecl_dimensio decl_dimensio, String id, NodeAssignacio_dimensional assignacio_dimensional, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.decl_dimensio = decl_dimensio;
        this.assignacio_dimensional = assignacio_dimensional;
        this.id = id;
        this.lineCode = l;
    }

    public NodeDecl_taula(NodeTipus tipus, NodeDecl_dimensio decl_dimensio, String id, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.decl_dimensio = decl_dimensio;
        this.id = id;
        this.lineCode = l;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}