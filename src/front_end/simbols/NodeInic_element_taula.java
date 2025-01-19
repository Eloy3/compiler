package front_end.simbols;

import java.util.ArrayList;
import java.util.List;

import errors.ErrorLogger;
import util.TacUtil;
import util.Util;

public class NodeInic_element_taula extends NodeBase{

    private NodeEspecificacio_dimensio especificacio_dimensio;
    private NodeExprsimple exprsimple;
    private int[] lineCode;
    private String id;

    public NodeInic_element_taula(String id, NodeEspecificacio_dimensio especificacio_dimensio, NodeExprsimple exprsimple, int[] l) {
        super("Taula_inic", 0);
        this.id = id;
        this.especificacio_dimensio = especificacio_dimensio;
        this.exprsimple = exprsimple;
        this.lineCode = l;
    }
//    x[0] = 1
    public void generateCode() {
        Simbol idSimbol = Util.validateVariableExists(ts, id, lineCode);
        if(idSimbol == null) return;

        ArrayList<Integer> dimensions = idSimbol.getArrayDimensions();
        ArrayList<NodeExprsimple> llistavalors = extractParamList();
        if(dimensions.size() != llistavalors.size()){
            ErrorLogger.logSemanticError(lineCode,"La dimensió de la taula ha de ser igual a la dimensió de l'indexació.");
            return;
        }
        List<String> indexes = new ArrayList<>();

        // Check if the index is a valid index
        for(NodeExprsimple valor: llistavalors){
            if(valor.getTipus() == NodeExprsimple.tipusexpr.id){
                Simbol indexSimbol = Util.validateVariableExists(ts, valor.getValor(), lineCode);
                if(indexSimbol == null) return;

                if(!indexSimbol.getTipus().equalsIgnoreCase("ent")){
                    ErrorLogger.logSemanticError(lineCode,"L'index ha de ser del tipus enter.");
                    return;
                }
                String indextmp = cta.newTempVar("ent");
                cta.generateCode("assign", indextmp, indexSimbol.getNom(), ts);
                indexes.add(indextmp);

            }else if(valor.getTipus() == NodeExprsimple.tipusexpr.ent){
                indexes.add(valor.getValor());

            }else{
                ErrorLogger.logSemanticError(lineCode, "L'index ha de ser un enter.");
                return;
            }
        }

        if(exprsimple.getTipus() == NodeExprsimple.tipusexpr.id){
            Simbol exprSimbol = Util.validateVariableExists(ts, exprsimple.getValor(), lineCode);
            if(exprSimbol == null) return;
            if(!Util.typeMatches(exprSimbol.getTipus(), idSimbol.getTipus())) return;
            TacUtil.generateInd_ass(cta, ts, id, exprSimbol.getValor().toString(), exprSimbol.getTipus(), indexes);

        }else if(Util.typeMatches(idSimbol.getTipus(), exprsimple.getTipusAsString())){
            TacUtil.generateInd_ass(cta, ts, id, exprsimple.getValor(), idSimbol.getTipus(), indexes);
        }else{
            ErrorLogger.logSemanticError(lineCode, "La variable '" + id + "' no té el tipus esperat '" + exprsimple.getTipusAsString() + "'.");
            return;
        }
    }

    public ArrayList<NodeExprsimple> extractParamList() {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeEspecificacio_dimensio current = especificacio_dimensio;
        while (current != null) {
            list.add(current.getExprsimple());
            current = current.getAssignacio_dimension();
        }
        return list;
    }


}
