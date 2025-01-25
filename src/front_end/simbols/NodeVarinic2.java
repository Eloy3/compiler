package front_end.simbols;

import errors.ErrorLogger;
import util.TacUtil;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import front_end.simbols.Array.NodeInicialitzacio_taula;
import front_end.simbols.NodeExprsimple.tipusexpr;
import front_end.simbols.Procedure.NodeCrida_funcio;

public class NodeVarinic2 extends NodeBase {
    private NodeExprsimple exprsimple;
    private NodeExprcomposta exprcomposta;
    private NodeCrida_funcio crida_funcio;
    private NodeInicialitzacio_taula inicialitzacio_taula;
    private String id;
    private int[] lineCode;

    public NodeVarinic2(String id, NodeExprsimple e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.exprsimple = e;
        this.lineCode = lc;
    }

    public NodeVarinic2(String id, NodeExprcomposta e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.exprcomposta = e;
        this.lineCode = lc;
    }

    public NodeVarinic2(String id, NodeCrida_funcio e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.crida_funcio = e;
        this.lineCode = lc;
    }

    public NodeVarinic2(String id, NodeInicialitzacio_taula e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.inicialitzacio_taula = e;
        this.lineCode = lc;
    }

    public void generateCode() {
        if(crida_funcio!=null){
            generateCodeProcedure();
            return;
        }else if(inicialitzacio_taula!=null){
            generateCodeInicialitzacioTaula();
            return;
        }

        Simbol left = Util.validateVariableExists(ts, id, lineCode);
        if (left == null) return;
        if(left.isConstant()){
            ErrorLogger.logSemanticError(lineCode, "No es poden fer reassignacions a constants.");
        }

        if(exprcomposta!=null){
            if(exprcomposta.getExprcomposta()!=null){
                generateCodeExprcomposta();
            }else{
                validateAndGenerate(left, exprcomposta.getExprsimple().getTipusAsString(), exprcomposta.getExprsimple().getValor(), Optional.empty());
            }
            return;
        }
        
        validateAndGenerate(left, exprsimple.getTipusAsString(), exprsimple.getValor(), Optional.empty());
    }

    public void generateCodeProcedure(){
        if (!crida_funcio.generateCode()) return;
            Simbol procedure = Util.validateVariableExists(ts, crida_funcio.getFunctionName(), lineCode);
            Simbol variable = Util.validateVariableExists(ts, id, lineCode);
            if(procedure == null) {
                ErrorLogger.logSemanticError(lineCode, "La funció '" + crida_funcio.getFunctionName() + "' no existeix.");
                return;
            }else if(variable == null) {
                ErrorLogger.logSemanticError(lineCode, "La variable '" + id + "' no existeix.");
                return;
            }else if(!Util.typeMatches(variable.getTipus(), procedure.getTipus())) {
                ErrorLogger.logSemanticError(lineCode, "La variable '" + id + "' no té el tipus esperat '" + procedure.getTipus() + "'.");
                return;
            }
            TacUtil.procedureResultToVariable(cta, ts, id, variable.getTipus());
    }
    
    public void generateCodeExprcomposta() {
        Simbol left = Util.validateVariableExists(ts, id, lineCode);
        if (left == null) return;

        if(left.getTipus().equalsIgnoreCase("bool")){
            ErrorLogger.logSemanticError(lineCode, "No es poden fer operacions aritmètiques amb variables de tipus booleà.");
            return;
        }
        
        if(left.getTipus().equalsIgnoreCase("text")){
            ErrorLogger.logSemanticError(lineCode, "No es poden fer operacions aritmètiques amb variables de tipus text.");
            return;
        }

        String valueB = resolveCompositeExpression(left.getTipus(), exprcomposta);
        if (valueB == null) return;

        cta.generateNewVarAssign(left, valueB, ts);
    }

    private String resolveCompositeExpression(String tipusA, NodeExprcomposta compositeExpression) {

        NodeExprsimple exprA = compositeExpression.getExprsimple();
        String valueA;
        if (exprA.getTipus() == tipusexpr.id) {
            Simbol target = Util.validateVariableExists(ts, exprA.getValor(), lineCode);
            if(target == null) {
                return null;
            }else if(!Util.typeMatches(tipusA, target.getTipus())) {
                ErrorLogger.logSemanticError(lineCode, "La variable '" + exprA.getValor() + "' no té el tipus esperat '" + tipusA + "'.");
                return null;
            }else{
                valueA = target.getNom();
            }
        }
        else if(exprA.getTipus() == tipusexpr.ent) {
            valueA = exprA.getValor();
        }else{
            ErrorLogger.logSemanticError(lineCode, "L'expressió " + exprA.getValor() + " no té un tipus vàlid per ser assignada de manera composta.");
            return null;
        }

        if (compositeExpression.getExprcomposta()==null) { 
            return valueA;
        }
        
        String operator = compositeExpression.getOperador().getTipus();

        String tempVar = cta.newTempVar(tipusA);
        String valueB = resolveCompositeExpression(tipusA,compositeExpression.getExprcomposta());
        if (valueB == null) return null;
        cta.generateAssignComposite(tempVar, exprA.getValor(), operator, valueB , ts);

        return tempVar;
    }

    private void validateAndGenerate(Simbol target, String typeA, String valueA, Optional<NodeExprsimple> optionalB) {
/*         Simbol target = Util.validateVariableExists(ts, targetId, lineCode);
        if (target == null) return; */

        if(target.getTipus().equals("text")) {
            ErrorLogger.logSemanticError(lineCode, "Les cadenes de text son immutables");
            return;
        }

        if(typeA.equalsIgnoreCase("arrayValue")){
            typeA = resolveType(typeA, valueA);
            if (typeA == null || !Util.typeMatches(target.getTipus(), typeA)) {
                ErrorLogger.logSemanticError(lineCode, target.getNom() + " i " + valueA + " no tenen el mateix tipus.");
                return;
            }
            NodeExprsimple expressio = exprcomposta.getExprsimple();
            if(expressio == null || expressio.getPos() == null || expressio.getValor() == null){
                ErrorLogger.logSemanticError(lineCode, "L'array es invàlid.");
                return;
            }
            TacUtil.generateInd_val(cta, ts, expressio.getValor(), id, typeA, expressio.getPos(), lineCode);
            return;
        }
        
        typeA = resolveType(typeA, valueA);
        if (typeA == null || !Util.typeMatches(target.getTipus(), typeA)) {
            ErrorLogger.logSemanticError(lineCode, target.getNom() + " i " + valueA + " no tenen el mateix tipus.");
            return;
        }

        if (optionalB.isPresent()) {
            NodeExprsimple b = optionalB.get();
            String typeB = resolveType(b.getTipusAsString(), b.getValor());
            if (typeB == null || !Util.typeMatches(target.getTipus(), typeB)) {
                ErrorLogger.logSemanticError(lineCode, "Variable '" + target.getNom() + "' has mismatched types.");
                return;
            }
            calcOcupComposite(target, valueA, exprcomposta.getOperador().getTipus(), b.getValor());
        } else {
            calcOcup(target, valueA);
        }
    }

    private String resolveType(String type, String value) {
        if (type.equalsIgnoreCase("id") || type.equalsIgnoreCase("arrayValue")) {
            Simbol operand = Util.validateVariableExists(ts, value, lineCode);
            if (operand == null) {
                ErrorLogger.logSemanticError(lineCode, "La variable '" + value + "' no està definida.");
                return null;
            }
            return operand.getTipus();
        }
        return type;
    }

    private void calcOcup(Simbol target, String value) {
        String tempVar = cta.newTempVar(target.getTipus(), value);
        cta.generateCode("assign", tempVar, value, ts);
        cta.generateNewVarAssign(target, tempVar, value, ts);
        cta.setTemp_id(null);
    }

    private void calcOcupComposite(Simbol left, String valueA, String operator, String valueB) {
        String tempVar = cta.newTempVar(left.getTipus(), valueA);
        cta.generateAssignComposite(tempVar, valueA, operator, valueB, ts);
        cta.generateNewVarAssign(left, tempVar, ts);
    }

    private void generateCodeInicialitzacioTaula() {
        Simbol var = Util.validateVariableExists(ts, id, lineCode);
        if(var == null) return;
        String tipus = var.getTipus();

        if(inicialitzacio_taula.getLlistavalors() != null){
            ArrayList<Integer> dimensions = new ArrayList<>();
            dimensions.add(1);
            var.setArrayDimensions(dimensions);
            ArrayList<NodeExprsimple> llistavalors = Util.getArrayList(inicialitzacio_taula.getLlistavalors());
            for(int i=0; i < llistavalors.size(); i++){
                NodeExprsimple valor = llistavalors.get(i);
                List<String> indexes = Arrays.asList(String.valueOf(i));
                if(valor.getTipus() == NodeExprsimple.tipusexpr.id){
                    if(Util.validateVariableExists(ts, id, lineCode)==null) return;
                    if(!Util.typeMatches(tipus, ts.getTipus(valor.getValor()))) return;
                    TacUtil.generateInd_ass(cta, ts, id, valor.getValor(), tipus, indexes);
                }else{
                    if(!Util.typeMatches(tipus, valor.getTipusAsString())) return;
                    TacUtil.generateInd_ass(cta, ts, id, valor.getValor(), tipus, indexes);
                }
            }
            cta.newVarArray(id, tipus, llistavalors.size());

        }else if(inicialitzacio_taula.getAssignacio_memoria() != null){
            ArrayList<NodeExprsimple> llistavalors = inicialitzacio_taula.extractParamList();
            int size = 1;
            ArrayList<Integer> dimensions = new ArrayList<>();
            for(NodeExprsimple valor : llistavalors){
                if(valor.getTipus() == NodeExprsimple.tipusexpr.ent){
                    if(!Util.typeMatches(tipus, valor.getTipusAsString())) return;
                    int sizeParam = Integer.parseInt(valor.getValor());
                    if(sizeParam < 1){
                        ErrorLogger.logSemanticError(lineCode,"La dimensió de la taula ha de ser un enter positiu.");
                    }
                    size = size * sizeParam;
                    dimensions.add(sizeParam);
                }else{
                    ErrorLogger.logSemanticError(lineCode,"La declaració de la dimensió de la taula ha de ser un enter.");
                }
            }
            ts.insertElement(id, tipus, dimensions);
            cta.newVarArray(id, tipus, size);
        }
    }

    @Override
    public String toString() {
        return exprsimple != null ? exprsimple.toString() : exprcomposta.toString();
    }

    public String getTipus() {
        return exprsimple != null ? exprsimple.getTipusAsString() : null;
    }

    public String getValor() {
        return exprsimple != null ? exprsimple.getValor() : null;
    }

    public String getId() {
        return id;
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }

    public NodeExprcomposta getExprcomposta() {
        return exprcomposta;
    }

    public NodeCrida_funcio getCrida_funcio() {
        return crida_funcio;
    }

    public void setCrida_funcio(NodeCrida_funcio crida_funcio) {
        this.crida_funcio = crida_funcio;
    }

    public ArrayList<NodeExprsimple> extractParamList() {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeExprcomposta current = exprcomposta.getExprcomposta();
        while (current != null) {
            list.add(current.getExprsimple());
            current = current.getExprcomposta();
        }
        return list;
    }

}