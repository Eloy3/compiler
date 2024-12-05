package front_end.simbols;

import errors.*;

public class NodeDecl_Variable extends NodeBase{
    private NodeTipus nt;
    private NodeVarinic varinic;
    private String id;
    private int[] lc;
    
    public NodeDecl_Variable(NodeTipus nt, String id, NodeVarinic varinic, int[] lc){
        super("Decl_variable", 0);
        this.nt = nt;
        this.varinic = varinic;
        this.id = id;
        this.lc = lc;
    }
    
    public void generateCode(){
        if(ts.existeixTs(id)){
            ErrorLogger.logSemanticError(lc,"La variable '" + id + "' ja ha estat declarada.");
            return;
        }
        if(varinic == null){
            ts.insertElement(id, nt.getTipusAsString(), null);
        }else{
            handleVariableInitialization();
        }
    }

    private void handleVariableInitialization() {
        String id2 = varinic.getValor();
        switch(varinic.getTipus()){
            case "id":
                handleIdType(id2);
                break;
            case "ent":
                handleEnterType(id2);
                break;
            case "bool":
                handleBooleaType(id2);
                break;
            case "procediment":
                handleProcedimentType();
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
                ts.insertElement(id, nt.getTipusAsString().toString(), param.valor);
                generaC3a();
            }
        }
    }

    private void handleEnterType(String id2) {
        if(!nt.getTipusAsString().equals("ENT")){
            ErrorLogger.logSemanticError(lc,"Les variables " +id + " i " + id2 + " no tenen el mateix tipus");
        }else{
            ts.insertElement(id, nt.getTipusAsString().toString(), varinic);
            generaC3a();
        }
    }

    private void handleBooleaType(String id2) {
        if(!nt.getTipusAsString().equals("BOOL")){
            ErrorLogger.logSemanticError(lc,"Les variables " +id + " i " + id2 + " no tenen el mateix tipus");
        }else{
            ts.insertElement(id, nt.getTipusAsString().toString(), varinic);
            generaC3a();
        }
    }

    private void handleProcedimentType() {
        String nomProcedure = varinic.getNomProcedure();
        if (!ts.existeixTs(nomProcedure)) {
            ErrorLogger.logSemanticError(lc, "La funció '" + nomProcedure + "' no existeix.");
            return;
        }
        String tipusProcedure = varinic.getTipusProcedure();
        if(!nt.getTipusAsString().equalsIgnoreCase(tipusProcedure)){
            ErrorLogger.logSemanticError(lc,"La variable " +id + " i el procediment " + nomProcedure + " no tenen el mateix tipus");
        }else{
            varinic.getExprsimple().generateCodeProcedure();
            varinic.getExprsimple().generateCodeProcedure();
            String tempVar = cta.newTempVar(nt.getTipusAsString(), null);
            cta.generateCode(tempVar + " = retInt\n");
            ts.insertElement(id, nt.getTipusAsString(), tempVar); // Insert the variable declaration
            cta.generateCode(id + " = " + tempVar + "\n");
            cta.newVar(id, nt.getTipusAsString());
        }
    }
    
    public void generaC3a(){
        Simbol operand = ts.get(id);
        String temp_var;

        temp_var = cta.newTempVar(operand.tipus.toString(), varinic.getValor());

        cta.generateCode(temp_var + " = ");
        cta.generateCode(operand.valor + "\n");

        cta.generateCode(cta.newVar(id, operand.tipus, varinic.getValor()) + " = " + temp_var + "\n");

        cta.setTemp_id(null);
    }
}