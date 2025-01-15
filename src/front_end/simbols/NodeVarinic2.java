package front_end.simbols;

import errors.ErrorLogger;
import util.TacUtil;
import util.Util;
import front_end.simbols.NodeCrida_funcio;

import java.util.ArrayList;
import java.util.Optional;
import front_end.simbols.NodeExprsimple.tipusexpr;

public class NodeVarinic2 extends NodeBase {

    private NodeExprsimple exprsimple;
    private NodeExprcomposta exprcomposta;
    private NodeCrida_funcio crida_funcio;
    private String id;
    private int[] lc;

    public NodeVarinic2(String id, NodeExprsimple e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.exprsimple = e;
        this.lc = lc;
    }

    public NodeVarinic2(String id, NodeExprcomposta e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.exprcomposta = e;
        this.lc = lc;
    }

    public NodeVarinic2(String id, NodeCrida_funcio e, int[] lc) {
        super("Varinic2", 0);
        this.id = id;
        this.crida_funcio = e;
        this.lc = lc;
    }

    public void generateCode() {
        if(crida_funcio!=null){
            generateCodeProcedure();
            return;
        }else if(exprcomposta!=null){
            if(exprcomposta.getExprcomposta()!=null){
                generateCodeExprcomposta();
            }else{
                validateAndGenerate(id, exprcomposta.getExprsimple().getTipusAsString(), exprcomposta.getExprsimple().getValor(), Optional.empty());
            }
            return;
        }

        validateAndGenerate(id, exprsimple.getTipusAsString(), exprsimple.getValor(), Optional.empty());
    }

    public void generateCodeProcedure(){
        if (!crida_funcio.generateCode()) return;
            Simbol procedure = Util.validateVariableExists(ts, crida_funcio.getFunctionName(), lc);
            Simbol variable = Util.validateVariableExists(ts, id, lc);
            if(procedure == null) {
                ErrorLogger.logSemanticError(lc, "La funció '" + crida_funcio.getFunctionName() + "' no existeix.");
                return;
            }else if(variable == null) {
                ErrorLogger.logSemanticError(lc, "La variable '" + id + "' no existeix.");
                return;
            }else if(!Util.typeMatches(variable.getTipus(), procedure.getTipus())) {
                ErrorLogger.logSemanticError(lc, "La variable '" + id + "' no té el tipus esperat '" + procedure.getTipus() + "'.");
                return;
            }
            TacUtil.procedureResultToVariable(cta, ts, id, variable.getTipus());
    }
    
    public void generateCodeExprcomposta() {
/*         NodeExprsimple valueA = exprcomposta.getExprsimple();
        String targetVar;
        if (valueA.getTipus() == tipusexpr.id) {
            targetVar = Util.validateVariableExists(ts, valueA.getValor(), lc).toString();
        }
        else if(valueA.getTipus() == tipusexpr.ent) {
            targetVar = valueA.getValor();
        }else{
            ErrorLogger.logSemanticError(lc, "L'expressió " + valueA.getValor() + " no té un tipus vàlid per ser assignada de manera composta.");
            return;
        }
        
        if (targetVar == null) return; */

        Simbol left = Util.validateVariableExists(ts, id, lc);
        if (left == null) return;

        if(left.getTipus() == tipusexpr.bool.toString()){
            ErrorLogger.logSemanticError(lc, "No es poden fer operacions aritmètiques amb variables de tipus booleà.");
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
            Simbol target = Util.validateVariableExists(ts, exprA.getValor(), lc);
            if(target == null) {
                return null;
            }else if(!Util.typeMatches(tipusA, target.getTipus())) {
                ErrorLogger.logSemanticError(lc, "La variable '" + exprA.getValor() + "' no té el tipus esperat '" + tipusA + "'.");
                return null;
            }else{
                valueA = target.getNom();
            }
        }
        else if(exprA.getTipus() == tipusexpr.ent) {
            valueA = exprA.getValor();
        }else{
            ErrorLogger.logSemanticError(lc, "L'expressió " + exprA.getValor() + " no té un tipus vàlid per ser assignada de manera composta.");
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

    private void validateAndGenerate(String targetId, String typeA, String valueA, Optional<NodeExprsimple> optionalB) {
        Simbol target = Util.validateVariableExists(ts, targetId, lc);
        if (target == null) return;

        typeA = resolveType(typeA, valueA);
        if (typeA == null || !Util.typeMatches(target.getTipus(), typeA)) {
            ErrorLogger.logSemanticError(lc, target.getNom() + " i " + valueA + " no tenen el mateix tipus.");
            return;
        }

        if (optionalB.isPresent()) {
            NodeExprsimple b = optionalB.get();
            String typeB = resolveType(b.getTipusAsString(), b.getValor());
            if (typeB == null || !Util.typeMatches(target.getTipus(), typeB)) {
                ErrorLogger.logSemanticError(lc, "Variable '" + targetId + "' has mismatched types.");
                return;
            }
            calcOcupComposite(target, valueA, exprcomposta.getOperador().getTipus(), b.getValor());
        } else {
            calcOcup(target, valueA);
        }
    }

    private String resolveType(String type, String value) {
        if (Util.isIdentifier(type)) {
            Simbol operand = Util.validateVariableExists(ts, value, lc);
            if (operand == null) {
                ErrorLogger.logSemanticError(lc, "La variable '" + value + "' no està definida.");
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