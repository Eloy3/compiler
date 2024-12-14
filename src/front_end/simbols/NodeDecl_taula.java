package front_end.simbols;

import errors.ErrorLogger;

public class NodeDecl_taula extends NodeBase {
    private NodeTipus tipus;
    private NodeDimensions_taula dimensions_taula;
    private NodeInicialitzacio_taula inicialitzacio_taula;
    private String id;
    private int[] lineCode;

    public NodeDecl_taula(NodeTipus tipus, NodeDimensions_taula dimensions_taula, String id, NodeInicialitzacio_taula inicialitzacio_taula, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.dimensions_taula = dimensions_taula;
        this.inicialitzacio_taula = inicialitzacio_taula;
        this.id = id;
        this.lineCode = l;
    }

    public NodeDecl_taula(NodeTipus tipus, NodeDimensions_taula dimensions_taula, String id, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.dimensions_taula = dimensions_taula;
        this.id = id;
        this.lineCode = l;
    }

    public void generateCode(){
        if(ts.existeixTs(id)){
            ErrorLogger.logSemanticError(lineCode,"La variable '" + id + "' ja ha estat declarada.");
            return;
        }

        if(inicialitzacio_taula == null){
            ts.insertElement(id, tipus.getTipusAsString(), null);
        }else{
            handleArrayInitialization();
        }
    }
    
    private void handleArrayInitialization() {
        
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}