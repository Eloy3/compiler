package front_end.simbols;

import errors.*;
import java.util.Optional;
import util.Util;
/**
 * NodeVarinic2 handles variable initialization.
 */
public class NodeVarinic2 extends NodeBase {

    private NodeExprsimple exprsimple;
    private NodeExprcomposta exprcomposta;
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
    }

    public void generateCode_exprsimple() {
        validateAndGenerate(id, exprsimple.getTipusAsString(), exprsimple.getValor(), Optional.empty());
    }
    
    public void generateCode_exprcomposta() {
        validateAndGenerate(id, exprcomposta.getA().getTipusAsString(), exprcomposta.getA().getValor(), 
                            Optional.of(exprcomposta.getB()));
    }
    
    
    private void validateAndGenerate(String targetId, String typeA, String valueA, Optional<NodeExprsimple> optionalB) {

        Simbol target = Util.validateVariableExists(ts, targetId, lc);
        if (target == null){
            return;
        } 
    
        // Validate type of the target variable
        if (!Util.typeMatches(target.getTipus(), typeA)) {
            new Error_DistintTipus().printError(lc, targetId);
            return;
        }
    
        // Validate second operand if it exists
        if (optionalB.isPresent()) {
            NodeExprsimple b = optionalB.get();
            Simbol secondOperand = Util.validateOperand(ts, b, lc);
            if (secondOperand == null || !Util.typeMatches(target.getTipus(), secondOperand.getTipus())) {
                new Error_DistintTipus().printError(lc, targetId);
                return;
            }
        }
    
        // Generate code based on operand presence
        if (optionalB.isEmpty()) {
            calcOcup(target, valueA);
        } else {
            NodeExprsimple b = optionalB.get();
            calcOcupComposite(target, valueA, exprcomposta.getOperador().getTipus(), b.getValor());
        }
    }
    
    // Generate code for simple assignments
    private void calcOcup(Simbol target, String value) {
        String tempVar = cta.newTempVar(target.getTipus(), value);
        cta.generateCode(tempVar + " = " + value + "\n");
        cta.generateCode(cta.newVar(target.getNom(), target.getTipus(), value) + " = " + tempVar + "\n");
        cta.setTemp_id(null);
    }

    // Generate code for composite assignments
    private void calcOcupComposite(Simbol target, String valueA, String operator, String valueB) {
        String tempVar = cta.newTempVar(target.getTipus(), valueA);
        cta.generateCode(tempVar + " = " + valueA + " " + operator + " " + valueB + "\n");
        cta.generateCode(cta.newVar(target.getNom(), target.getTipus(), valueA) + " = " + tempVar + "\n");
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
}
