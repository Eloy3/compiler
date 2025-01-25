package front_end.simbols;

import data_structures.Procedure;
import errors.*;
import util.TacUtil;
import util.Util;

public class NodeDecl_Variable extends NodeBase{
    private NodeTipus nt;
    private NodeVarinic varinic;
    private String id;
    private int[] lc;
    private boolean isConstant = false;
    
    public NodeDecl_Variable(NodeTipus nt, String id, NodeVarinic varinic, int[] lc){
        super("Decl_variable", 0);
        this.nt = nt;
        this.varinic = varinic;
        this.id = id;
        this.lc = lc;
    }

    public NodeDecl_Variable(boolean constant, NodeTipus nt, String id, NodeVarinic varinic, int[] lc){
        super("Decl_variable", 0);
        this.isConstant = true;
        this.nt = nt;
        this.varinic = varinic;
        this.id = id;
        this.lc = lc;
    }
    
    public void generateCode(){
        if(ts.existeixTsambit(id)){
            ErrorLogger.logSemanticError(lc,"La variable '" + id + "' ja ha estat declarada.");
            return;
        }
        ts.insertElement(id, nt.getTipusAsString(), isConstant);

        if(isConstant && varinic == null){
            ErrorLogger.logSemanticError(lc,"Les constants s'han d'inicialitzar a la declaració.");
            return;
        }

        if(varinic!=null){
            if(varinic.getCrida_funcio()!=null){
                handleProcedimentType();
            }else if(varinic.getExprsimple()!=null){
                handleVariableInitialization();
            }
        }
    }

    private void handleVariableInitialization() {
        String id2 = varinic.getValor();
        switch(varinic.getTipus()){
            case "id":
                handleIdType(id2);
                break;
            case "ent":
            case "bool":
                if(!Util.typeMatches(varinic.getTipus(), nt.getTipusAsString())){
                    ErrorLogger.logSemanticError(lc,"Les variables " +id + " i " + id2 + " no tenen el mateix tipus");
                }else{
                    generaC3a();
                }
                break;
            case "text":
                if(!Util.typeMatches(varinic.getTipus(), nt.getTipusAsString())){
                    ErrorLogger.logSemanticError(lc,"Les variables " +id + " i " + id2 + " no tenen el mateix tipus");
                }else{
                    cta.newVar(id, varinic.getTipus(), varinic.getValor());
                }
                break;
        }
    }

    private void handleIdType(String id2) {
        if(!ts.existeixTs(id2)){
            ErrorLogger.logSemanticError(lc,"La variable '" + id2 + "' no existeix.");
        }else{
            Simbol param = ts.get(id2);
            if(!nt.getTipusAsString().equals(param.tipus)){
                ErrorLogger.logSemanticError(lc,"Les variables " +id + " i " + id2 + " no tenen el mateix tipus");
            }else{
                ts.insertElement(id, nt.getTipusAsString().toString(), isConstant);
                generaC3a();
            }
        }
    }

    private void handleProcedimentType() {
        String nomProcedure = varinic.getNomProcedure();
        Procedure procedure = Util.validateProcedureExists(tp, nomProcedure, lc);
        if(procedure == null){
            return;
        }
        String tipusProcedure = procedure.getType_return().toString();
        if(!Util.typeMatches(nt.getTipusAsString(), tipusProcedure)) return;
        varinic.getCrida_funcio().generateCode();
        TacUtil.procedureResultToVariable(cta, ts, id, nt.getTipusAsString());
    }
    
    public void generaC3a(){
        Simbol operand = ts.get(id);
        String temp_var;

        temp_var = cta.newTempVar(operand.tipus.toString());
        cta.generateCode(temp_var + " = " + varinic.getValor() + "\n");
        cta.generateCode(cta.newVar(id, operand.tipus, varinic.getValor()) + " = " + temp_var + "\n");
        cta.setTemp_id(null);
    }

    public boolean isConstant() {
        return isConstant;
    }

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }

}