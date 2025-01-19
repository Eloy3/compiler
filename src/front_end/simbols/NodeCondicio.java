package front_end.simbols;

import errors.ErrorLogger;
import front_end.simbols.NodeExprsimple.tipusexpr;
import util.TacUtil;
import util.Util;

/**
 *
 * @author Eloy
 */
public class NodeCondicio extends NodeBase{
    
    private NodeCondicio cond;
    private NodeOperador_cond operador;
    private int[] lc;
    
    private NodeExprsimple expr;
    
    public NodeCondicio(NodeExprsimple expr, int[] lc) {
        super("NodeCondicio", 0);
        this.expr = expr;
        this.lc = lc;
    }
    
    public NodeCondicio(NodeExprsimple v, NodeOperador_cond o, NodeCondicio cond, int[] lc) {
        super("NodeCondicio", 0);
        this.expr = v;
        this.cond = cond;
        this.operador = o;
        this.lc = lc;
    }

    public boolean generateCodeID(){
        String cond = cta.newTempVar("bool");
        
        if(expr.getTipus() == tipusexpr.arrayValue){
            String ind = TacUtil.generateIndexes(cta, ts, expr.getValor(), expr.getPos());
            cta.generateCode("assign", cond, ind, ts);
            TacUtil.etiquetacond(cta);
            cta.generateCode(cond + " then ");
        }else{
            cta.generateCode("assign", cond, expr.getValor(), ts);
            TacUtil.etiquetacond(cta);
            cta.generateCode(expr.getValor() + " then ");
        }
        
        return true;
    }

    public boolean generateCodeOperador(){

        Simbol left = Util.validateVariableExists(ts, expr.getValor(), lc);
        if (left == null) return false;

        String valueB = resolveCompositeExpression(left.getTipus(), cond);
        if (valueB == null) return false;
        String cond = left.getNom().toString() + " " + operador.getOperador() + " " + valueB;
        TacUtil.etiquetacond(cta);
        cta.generateCode(cond + " then ");
        return true;
    }

    private String resolveCompositeExpression(String tipusA, NodeCondicio compositeExpression) {

        NodeExprsimple exprA = compositeExpression.getExpr();
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

        if (compositeExpression.getCond()==null) { 
            return valueA;
        }
        
        String operator = compositeExpression.getOperador().getOperador();

        String tempVar = cta.newTempVar(tipusA);
        String valueB = resolveCompositeExpression(tipusA,compositeExpression.getCond());
        if (valueB == null) return null;
        cta.generateAssignComposite(tempVar, exprA.getValor(), operator, valueB , ts);

        return tempVar;
    }

    public NodeOperador_cond getOperador() {
        return operador;
    }

    public NodeCondicio getCond() {
        return cond;
    }

    public void setCond(NodeCondicio cond) {
        this.cond = cond;
    }

    public void setOperador(NodeOperador_cond operador) {
        this.operador = operador;
    }

    public int[] getLc() {
        return lc;
    }

    public void setLc(int[] lc) {
        this.lc = lc;
    }

    public void setExpr(NodeExprsimple expr) {
        this.expr = expr;
    }

    public NodeExprsimple getExpr() {
        return expr;
    }

    public void setID(NodeExprsimple iD) {
        expr = iD;
    }
}
