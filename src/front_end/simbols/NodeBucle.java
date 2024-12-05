package front_end.simbols;

import util.Util;

public class NodeBucle extends NodeBase {

    private NodeDecl_Variable decl_Variable;
    private NodeCondicio condicio;
    private NodeComportamentv1 comportamentv1;
    private int[] lc;
    private NodeBlocf blocf;

    // FOR constructor
    public NodeBucle(NodeDecl_Variable decl_Variable, NodeCondicio condicio, NodeComportamentv1 comportamentv1, NodeBlocf blocf, int[] lc) {
        super("Buclefor", 0);
        this.decl_Variable = decl_Variable;
        this.condicio = condicio;
        this.comportamentv1 = comportamentv1;
        this.blocf = blocf;
        this.lc = lc;
    }

    // WHILE constructor
    public NodeBucle(NodeCondicio condicio, NodeBlocf blocf, int[] lc) {
        super("Buclewhile", 0);
        this.condicio = condicio;
        this.blocf = blocf;
        this.lc = lc;
    }

    public void generateCodeWhile() {
        inicibucle();
        etiquetacond();
        generateConditionCode();
        condiciobot(false);
        ts.incAmbit();
        blocf.generateCode();
        retornabucle();
        ts.decAmbit();
    }

    public void generateCodeFor() {
        ts.incAmbit();
        decl_Variable.generateCode();
        inicibucle();
        etiquetacond();
        validateAndGenerateConditionCode();
        condiciobot(false);
        comportamentv1.generateCode();
        blocf.generateCode();
        retornabucle();
        ts.decAmbit();
    }

    private void generateConditionCode() {
        if (condicio.getOperador() != null) {
            condicio.generateCodeOperador();
        } else {
            condicio.generateCodeID();
        }
    }

    private void validateAndGenerateConditionCode() {
        if (condicio.getOperador() != null) {
            Util.validateBinaryOperands(ts, condicio.getOperand1(), condicio.getOperand2(), lc);
            condicio.generateCodeOperador();
        } else {
            Util.validateUnaryOperand(ts, condicio.getOperand1(), lc);
            condicio.generateCodeID();
        }
    }

    public static void inicibucle() {
        String startLabel = cta.newLabel();
        cta.push(cta.getStart_stack(), startLabel);
        cta.generateCode(startLabel + ":skip\n");
    }

    public static void etiquetacond() {
        cta.generateCode("if ");
        String trueLabel = cta.newLabel();
        cta.push(cta.getTrue_stack(), trueLabel);

        String falseLabel = cta.newLabel();
        cta.push(cta.getFalse_stack(), falseLabel);
    }

    public static void condiciobot(boolean inverter) {
        String trueLabel = cta.getTop(cta.getTrue_stack());
        String falseLabel = cta.getTop(cta.getFalse_stack());

        if (inverter) {
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode("goto " + trueLabel + "\n");
            cta.pop(cta.getTrue_stack());
            cta.generateCode(trueLabel + ":skip\n");
        } else {
            cta.generateCode("goto " + trueLabel + "\n");
            cta.pop(cta.getTrue_stack());
            cta.generateCode("goto " + falseLabel + "\n");
            cta.generateCode(trueLabel + ":skip\n");
        }
        cta.setTemp_id(null);
    }

    public static void retornabucle() {
        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String falseLabel = cta.getTop(cta.getFalse_stack());
        cta.generateCode(falseLabel + ":skip\n");
    }

    public NodeComportamentv1 getComportamentv1() {
        return comportamentv1;
    }
}