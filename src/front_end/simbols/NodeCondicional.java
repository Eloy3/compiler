
package front_end.simbols;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.tipusexpr;
import util.TacUtil;
import util.Util;

public class NodeCondicional extends NodeBase{

    private NodeCondicio condicio;
    private NodeBlocf blocf;
    private NodeCondsino condsino;
    private int[] lc;
    
    public NodeCondicional(NodeCondicio a, NodeBlocf b, NodeCondsino c, int[] lc){
        super("Condicional",0);
        condicio = a;
        blocf = b;
        condsino = c;
        this.lc = lc;
    }

    public boolean generateCode(){
        if(condicio.getExpr()!=null){
            if(condicio.getExpr().getTipus() == tipusexpr.id){
                Simbol var = Util.validateVariableExists(ts, condicio.getExpr().getValor(), lc);
                if(var == null) return false;
                
                if(!Util.typeMatches(var.getTipus(), "bool")){
                    ErrorLogger.logSemanticError(lc,"La variable '" + condicio.getExpr() + "' no té el tipus esperat '" + var.getTipus() + "'.");
                }
            }else if(condicio.getExpr().getTipus() == tipusexpr.arrayValue){
                Simbol var = Util.validateVariableExists(ts, condicio.getExpr().getValor(), lc);
                if(var == null) return false;
                if(!Util.typeMatches(var.getTipus(), "bool")){
                    ErrorLogger.logSemanticError(lc,"La variable '" + condicio.getExpr() + "' no té el tipus esperat '" + var.getTipus() + "'.");
                }
            }
        }
        else if(!Util.validateBinaryOperands(ts, condicio.getOperand1(), condicio.getOperand2(), lc)) return false;
        
        String endLabel = cta.newLabel();
        if (condicio.getOperador() != null) {
            condicio.generateCodeOperador();
        } else {
            condicio.generateCodeID();
        }
        TacUtil.condiciobot(cta, false);
        blocf.generateCode();
        String falseLabel = cta.getTop(cta.getFalse_stack());
        cta.generateCode("goto " + endLabel + "\n");
        cta.generateCode(falseLabel + ":skip\n");
        if(condsino!=null){
            condsino.generateCode();
        }

        cta.generateCode(endLabel + ":skip\n");
        return true;
    }
}
