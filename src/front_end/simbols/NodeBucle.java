package front_end.simbols;

import front_end.simbols.NodeExprsimple.tipusexpr;
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

    public boolean generateCodeWhile() {
        if (!Util.validateCondicio(ts, condicio.getOperand1(), condicio.getOperand2(), condicio.getID(), lc)) return false;
        TacUtil.inicibucle(cta);
        TacUtil.etiquetacond(cta);
        if(!GenerateConditionCode()) return false;
        TacUtil.condiciobot(cta,false);
        ts.incAmbit();
        blocf.generateCode();
        TacUtil.retornabucle(cta);
        ts.decAmbit();
        return true;
    }

    public void generateCodeFor() {
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
    }

    /* private boolean generateConditionCode() {
        if (condicio.getOperador() != null) {
            if (!condicio.generateCodeOperador()) return false;
        } else {
            condicio.generateCodeID();
        }

        return true;
    } */

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