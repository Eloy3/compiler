package front_end.simbols;

import front_end.simbols.NodeExprsimple.tipusexpr;
import util.TacUtil;

/**
 *
 * @author Eloy
 */
public class NodeCondicio extends NodeBase{
    
    private NodeExprsimple operand1;
    private NodeExprsimple operand2;
    private NodeOperador_cond operador;
    private int[] lc;
    
    private NodeExprsimple expr;
    
    public NodeCondicio(NodeExprsimple expr, int[] lc) {
        super("NodeCondicio", 0);
        this.expr = expr;
        this.lc = lc;
    }
    
    public NodeCondicio(NodeExprsimple v, NodeOperador_cond o, NodeExprsimple v1, int[] lc) {
        super("NodeCondicio", 0);
        operand1 = v;
        operand2 = v1;
        operador = o;
        this.lc = lc;
    }

    public boolean generateCodeID(){
        String cond = cta.newTempVar("bool");
        
        if(expr.getTipus() == tipusexpr.arrayValue){
            String ind = TacUtil.generateIndexes(expr.getValor(), expr.getPos());
            cta.generateCode("assign", cond, ind, ts);
            TacUtil.etiquetacond(cta);
            cta.generateCode(cond + " then ");
        }else{
            cta.generateCode("assign", cond, expr.getValor(), ts);
            TacUtil.etiquetacond(cta);
            cta.generateCode(expr.getValor() + " ");
        }
        
        return true;
    }

    public boolean generateCodeOperador(){
        cta.generateCode(operand1.getValor()+" ");
        cta.generateCode(operador.getOperador()+ " ");
        cta.generateCode(operand2.getValor() + " ");
        return true;
    }

    public NodeOperador_cond getOperador() {
        return operador;
    }

    public NodeExprsimple getOperand1() {
        return operand1;
    }

    public NodeExprsimple getOperand2() {
        return operand2;
    }

    public NodeExprsimple getExpr() {
        return expr;
    }

    public void setID(NodeExprsimple iD) {
        expr = iD;
    }
}
