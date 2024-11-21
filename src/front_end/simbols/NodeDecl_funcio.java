package front_end.simbols;

import data_structures.Procedure;
import java_cup.terminal;
import errors.*;
import data_structures.Parameter;
import data_structures.Procedure;
import java.util.ArrayList;

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

        if (ts.existeixTs(functionName)) {
            // Log an error for duplicate function declaration
            ErrorLogger.logSemanticError(lc, "La funció '" + functionName + "' ja ha estat declarada.");
        } else {
            // Insert the function symbol into the symbol table
            ts.insertElement(functionName, tipus.getTipusString(), null);
        }

        // Add the function to the procedure table
        addProcedureToTable();
    }

    private void addProcedureToTable() {
        ArrayList<Parameter> params = new ArrayList<>();
        NodeParam currentParam = param;
    
        // Extract function parameters
        while (currentParam != null) {
            params.add(new Parameter(currentParam.getId(), currentParam.getTipus().getTipus()));
            currentParam = currentParam.getNext();
        }

        // Add a new procedure entry to the table
        tp.addRow(new Procedure(tp.getNewNumProc(), 0, functionName, params, 0, tipus.getTipus()));
    }

    public void generateCode() {
        // Generate the function label
        cta.generateCode(functionName + ":skip\n");
        cta.push(cta.getStart_stack(), functionName);

        // Generate the preamble
        cta.generateCode("pmb " + functionName + "\n");

        ts.incAmbit();
        cta.push(cta.getPproc(),functionName);
        

        // Generate parameter code
        NodeParam currentParam = param;
        while (currentParam != null) {
            String paramName = currentParam.getId();
            String paramType = currentParam.getTipus().toString();
            ts.insertElement(paramName, paramType, null);
            cta.generateCode("param_s " + paramName + "\n");
            currentParam = currentParam.getNext();
        }

        // Generate the function body
        if (blocf != null) {
            blocf.generateCode();
        }

        // Finalize the function with a return statement
        cta.generateCode("rtn " + functionName + "\n");
        ts.decAmbit();
        cta.pop(cta.getPproc());
    }

    
}
