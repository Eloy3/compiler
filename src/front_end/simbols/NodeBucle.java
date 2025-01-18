package front_end.simbols;

import util.TacUtil;
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
        if (!Util.validateLoop(ts, condicio.getExpr(), condicio.getCond(), lc)) return;
        TacUtil.inicibucle(cta);
        TacUtil.etiquetacond(cta);
        if(!GenerateConditionCode()) return;
        TacUtil.condiciobot(cta,false);
        ts.incAmbit();
        blocf.generateCode();
        TacUtil.retornabucle(cta);
        ts.decAmbit();
        return;
    }

    public void generateCodeFor() {
        if (!Util.validateLoop(ts, condicio.getExpr(), condicio.getCond(), lc)) return;
        ts.incAmbit();
        decl_Variable.generateCode();
        TacUtil.inicibucle(cta);
        TacUtil.etiquetacond(cta);
        GenerateConditionCode();
        TacUtil.condiciobot(cta,false);
        comportamentv1.generateCode();
        blocf.generateCode();
        TacUtil.retornabucle(cta);
        ts.decAmbit();
        return;
    }

    private boolean GenerateConditionCode() {
        if (condicio.getOperador() != null) {
            if (!condicio.generateCodeOperador()) return false;
        } else {
            if (!condicio.generateCodeID()) return false;
        }
        return true;
    }

    public NodeComportamentv1 getComportamentv1() {
        return comportamentv1;
    }
}