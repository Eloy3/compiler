
package front_end.simbols;

import errors.ErrorLogger;
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
        if(condicio.getID()!=null){
            Simbol var = Util.validateVariableExists(ts, condicio.getID(), lc);
            if(var == null) return false;
            if(!Util.typeMatches(var.getTipus(), "bool")){
                ErrorLogger.logSemanticError(lc,"La variable '" + condicio.getID() + "' no té el tipus esperat '" + var.getTipus() + "'.");
            }
        }
        else if(!Util.validateCondicio(ts, condicio.getOperand1(), condicio.getOperand2(), condicio.getID(), lc)) return false;
        TacUtil.etiquetacond(cta);
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
