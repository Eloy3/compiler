package front_end.simbols.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import errors.ErrorLogger;
import front_end.simbols.NodeBase;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.NodeTipus;
import util.TacUtil;
import util.Util;

public class NodeDecl_taula extends NodeBase {
    private NodeTipus tipus;
    private NodeInicialitzacio_taula inicialitzacio_taula;
    private String id;
    private ArrayList<Integer> dimensions;
    private int[] lineCode;

    public NodeDecl_taula(NodeTipus tipus, String id, NodeInicialitzacio_taula inicialitzacio_taula, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.inicialitzacio_taula = inicialitzacio_taula;
        this.id = id;
        this.lineCode = l;
    }

    public NodeDecl_taula(NodeTipus tipus, String id, int[] l){
        super("Decl_taula", 0);
        this.tipus = tipus;
        this.id = id;
        this.lineCode = l;
    }

    public void generateCode(){
        if(ts.existeixTs(id)){
            ErrorLogger.logSemanticError(lineCode,"La variable '" + id + "' ja ha estat declarada.");
            return;
        }
        
        if(inicialitzacio_taula != null){
            if(inicialitzacio_taula.getAssignacio_memoria() == null){
                dimensions = new ArrayList<>();
                dimensions.add(1);
                ts.insertElement(id, tipus.getTipusAsString(), dimensions);
                handleArrayInitialization();
            }else{
                handleArrayInitialization();
                ts.insertElement(id, tipus.getTipusAsString(), dimensions);
            }
        }else{
            ts.insertElement(id, tipus.getTipusAsString());
        }
    }
    
    private void handleArrayInitialization() {
        if(inicialitzacio_taula.getLlistavalors() != null){
            ArrayList<NodeExprsimple> llistavalors = Util.getArrayList(inicialitzacio_taula.getLlistavalors());
            for(int i=0; i < llistavalors.size(); i++){
                NodeExprsimple valor = llistavalors.get(i);
                List<String> indexes = Arrays.asList(String.valueOf(i));
                if(valor.getTipus() == NodeExprsimple.tipusexpr.id){
                    if(Util.validateVariableExists(ts, id, lineCode)==null) return;
                    if(!Util.typeMatches(tipus.getTipusAsString(), ts.getTipus(valor.getValor()))) return;
                    TacUtil.generateInd_ass(cta, ts, id, valor.getValor(), tipus.getTipusAsString(), indexes);
                }else{
                    if(!Util.typeMatches(tipus.getTipusAsString(), valor.getTipusAsString())) return;
                    TacUtil.generateInd_ass(cta, ts, id, valor.getValor(), tipus.getTipusAsString(), indexes);
                }
            }
            cta.newVarArray(id, tipus.getTipusAsString(), llistavalors.size());

        }else if(inicialitzacio_taula.getAssignacio_memoria() != null){
            ArrayList<NodeExprsimple> llistavalors = inicialitzacio_taula.extractParamList();
            int size = 1;
            dimensions = new ArrayList<>();
            for(NodeExprsimple valor : llistavalors){
                if(valor.getTipus() == NodeExprsimple.tipusexpr.ent){
                    int sizeParam = Integer.parseInt(valor.getValor());
                    if(sizeParam < 1){
                        ErrorLogger.logSemanticError(lineCode,"La dimensió de la taula ha de ser un enter positiu.");
                        return;
                    }
                    size = size * sizeParam;
                    dimensions.add(sizeParam);
                }else{
                    ErrorLogger.logSemanticError(lineCode,"La declaració de la dimensió de la taula ha de ser un enter.");
                    return;
                }
            }
            cta.newVarArray(id, tipus.getTipusAsString(), size);
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