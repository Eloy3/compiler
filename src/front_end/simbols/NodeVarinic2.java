package front_end.simbols;

import errors.ErrorLogger;
import util.TacUtil;
import util.Util;
import front_end.simbols.NodeCrida_funcio;
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
    
    public void generateCode_exprcomposta() {
        validateAndGenerate(id, exprcomposta.getExprsimple().getTipusAsString(), exprcomposta.getExprsimple().getValor(),
                Optional.of(exprcomposta.getB()));
    }

    private void validateAndGenerate(String targetId, String typeA, String valueA, Optional<NodeExprsimple> optionalB) {
        Simbol target = Util.validateVariableExists(ts, targetId, lc);
        if (target == null) return;

        typeA = resolveType(typeA, valueA);
        if (typeA == null || !Util.typeMatches(target.getTipus(), typeA)) {
            ErrorLogger.logSemanticError(lc, target.getNom() + " i " + valueA + " no tenen el mateix tipus.");
            return;
        }

        if (exprsimple != null && exprsimple.getProcedure() != null) {
            calcOcupProcedure(target);
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
        cta.generateCode(tempVar + " = " + value+"_"+ts.getCurrentProcedure() + "\n");
        cta.generateCode(cta.newVar(target.getNom()+"_"+ts.getCurrentProcedure(), target.getTipus(), value) + " = " + tempVar + "\n");
        cta.setTemp_id(null);
    }

    private void calcOcupComposite(Simbol target, String valueA, String operator, String valueB) {
        String tempVar = cta.newTempVar(target.getTipus(), valueA);
        cta.generateCode(tempVar + " = " + valueA+"_"+ts.getCurrentProcedure() + " " + operator + " " + valueB+"_"+ts.getCurrentProcedure() + "\n");
        cta.generateCode(cta.newVar(target.getNom()+"_"+ts.getCurrentProcedure(), target.getTipus(), valueA) + " = " + tempVar + "\n");
    }

    private void calcOcupProcedure(Simbol target) {
        String functionName = exprsimple.getProcedure().getFunctionName();
        if (!ts.existeixTs(functionName)) {
            ErrorLogger.logSemanticError(lc, "La funció '" + functionName + "' no existeix.");
            return;
        }
        String tempVar = cta.newTempVar(target.getTipus(), functionName);
        cta.generateCode(tempVar + " = ret" + target.getTipus().toUpperCase() + "\n");
        cta.generateCode(cta.newVar(target.getNom()+"_"+functionName, target.getTipus(), null) + " = " + tempVar + "\n");
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

}