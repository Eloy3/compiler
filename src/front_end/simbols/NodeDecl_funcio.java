package front_end.simbols;

import data_structures.Procedure;
import java_cup.terminal;
import errors.*;
import data_structures.Parameter;
import data_structures.Procedure;
import java.util.ArrayList;
import java.util.Collections;

public class NodeDecl_funcio extends NodeBase {

    private NodeTipus tipus;
    private NodeParam param;
    private NodeBlocf blocf;
    private String functionName;
    private int[] lc;

    public NodeDecl_funcio(NodeTipus t, String functionName, NodeParam p, NodeBlocf blocf, int[] lc) {
        super("decl_funcio", 0);
        this.tipus = t;
        this.param = p;
        this.blocf = blocf;
        this.functionName = functionName;
        this.lc = lc;   
    }

    private void addProcedureToTable() {
        ArrayList<Parameter> params = new ArrayList<>();
        NodeParam currentParam = param;
    
        // Extract function parameters
        while (currentParam != null) {
            params.add(new Parameter(currentParam.getId(), currentParam.getTipus().getTipus(), functionName));
            currentParam = currentParam.getNext();
        }

        tp.addRow(new Procedure(tp.getNewNumProc(), ts.getProfunditat(), functionName, params, 0, tipus.getTipus()));
    }

    public void generateCode() {
        if(tp.getProc(functionName)!=null){
            ErrorLogger.logSemanticError(lc, "La funció '" + functionName + "' ja ha estat declarada.");
            return;
        }

        ArrayList<String> argumentNames = new ArrayList<>();
        NodeParam currentParam = param;

        while (currentParam != null) {
            argumentNames.add(currentParam.getId());
            currentParam = currentParam.getNext();
        }
        Collections.reverse(argumentNames);
        ts.setCurrentProcedure(functionName);
        ts.incAmbit();
        ts.insertElementWithArgs(functionName, tipus.getTipusAsString(), null, argumentNames);
        addProcedureToTable();

        //Skip subroutine if not called
        cta.generateCode("goto principal \n");

        // Generate function code
        cta.generateCode(functionName + ":skip\n");
        cta.push(cta.getStart_stack(), functionName);

        cta.generateCode("pmb " + functionName + "\n");

        
        cta.push(cta.getPproc(), functionName);

        // Generate parameter code
        currentParam = param;
        while (currentParam != null) {
            String paramName = currentParam.getId();
            String paramType = currentParam.getTipus().getTipus().toString();
            ts.insertElement(paramName, paramType, null);
            cta.newVar(paramName, paramType);
            //ta.generateCode("param_s " + paramName + "\n");
            currentParam = currentParam.getNext();
        }

        // Generate the function body
        if (blocf != null) {
            blocf.generateCode();
        }

        ts.setCurrentProcedure("principal");
        ts.decAmbit();
        cta.pop(cta.getPproc());
    }


    
}
