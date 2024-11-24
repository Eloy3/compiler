package front_end.simbols;

import errors.ErrorLogger;
import util.Util;

import java.util.Optional;

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
        this.lc = lc;
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
        if (target == null) {
            return;
        }

        // Resolve typeA if it is an identifier
        if (Util.isIdentifier(typeA)) {
            Simbol operandA = Util.validateVariableExists(ts, valueA, lc);
            if (operandA == null) {
                ErrorLogger.logSemanticError(lc, "La variable '" + valueA + "' no està definida.");
                return;
            }
            typeA = operandA.getTipus(); // Update typeA to the resolved type
        }

        // Validate type of the target variable
        if (!Util.typeMatches(target.getTipus(), typeA)) {
            ErrorLogger.logSemanticError(lc, target+" i "+valueA+" no tenen el mateix tipus.");
            return;
        }

        // Validate second operand if it exists
        if (optionalB.isPresent()) {
            NodeExprsimple b = optionalB.get();
            String typeB = b.getTipusAsString();
            String valueB = b.getValor();

            if (Util.isIdentifier(typeB)) {
                Simbol secondOperand = Util.validateVariableExists(ts, valueB, lc);
                if (secondOperand == null) {
                    ErrorLogger.logSemanticError(lc, "La variable '" + valueB + "' no està definida.");
                    return;
                }
                typeB = secondOperand.getTipus(); // Update typeB to the resolved type
            }

            if (!Util.typeMatches(target.getTipus(), typeB)) {
                ErrorLogger.logSemanticError(lc, "Variable '" + targetId + "' has mismatched types.");
                return;
            }

            // Generate code for composite expression
            calcOcupComposite(target, valueA, exprcomposta.getOperador().getTipus(), valueB);
        } else {
            // Generate code for single operand
            calcOcup(target, valueA);
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
