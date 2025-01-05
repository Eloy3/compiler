package front_end.simbols;

import java.util.ArrayList;

import errors.ErrorLogger;
import util.Util;

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
        ts.insertElement(id, tipus.getTipusAsString(), null);
        if(inicialitzacio_taula != null){
            handleArrayInitialization();
        }
    }
    
    private void handleArrayInitialization() {
        if(inicialitzacio_taula.getLlistavalors() != null){
            ArrayList<NodeExprsimple> llistavalors = Util.getArrayList(inicialitzacio_taula.getLlistavalors());
            for(int i=0; i < llistavalors.size(); i++){
                NodeExprsimple valor = llistavalors.get(i);
                if(valor.getTipus() == NodeExprsimple.tipusexpr.id){
                    if(Util.validateVariableExists(ts, id, lineCode)==null) return;
                    if(!Util.typeMatches(tipus.getTipusAsString(), ts.getTipus(valor.getValor()))) return;
                    generateC3A(valor, tipus, i);
                }else{
                    if(!Util.typeMatches(tipus.getTipusAsString(), valor.getTipusAsString())) return;
                    generateC3A(valor, tipus, i);
                }
            }
            cta.newVarArray(id, tipus.getTipusAsString(), llistavalors.size());

        }else if(inicialitzacio_taula.getAssignacio_memoria() != null){
            ArrayList<NodeExprsimple> llistavalors = inicialitzacio_taula.extractParamList();
            int size = 0;
            for(NodeExprsimple valor : llistavalors){
                if(valor.getTipus() == NodeExprsimple.tipusexpr.id){
                    if(Util.validateVariableExists(ts, valor.getValor(), lineCode) == null) return;
                    if(!Util.typeMatches(tipus.getTipusAsString(), ts.getTipus(valor.getValor()))) return;
                    size++;
                }else if(valor.getTipus() == NodeExprsimple.tipusexpr.ent){
                    if(!Util.typeMatches(tipus.getTipusAsString(), valor.getTipusAsString())) return;
                    size += Integer.parseInt(valor.getValor());
                }
            }

        }
    }

    public void generateC3A(NodeExprsimple valor, NodeTipus tipus, int i){
        String tempVar = cta.newTempVar(tipus.getTipusAsString(), valor.getValor());
        cta.generateCode(tempVar + " = " + valor + "\n");
        cta.generateCode("assign", id+"["+i+"]", tempVar,ts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}