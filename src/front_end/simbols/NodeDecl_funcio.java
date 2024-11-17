package front_end.simbols;

import data_structures.Procedure;
import java_cup.terminal;

public class NodeDecl_funcio extends NodeBase{

    private NodeTipus tipus;
    private NodeParam param;
    private NodeBlocf blocf;
    private String functionName;
    private int[] lc;

    public NodeDecl_funcio(NodeTipus t, String functionName, NodeParam p, NodeBlocf blocf, int[]lc) {
        super("decl_funcio", 0);
        tipus = t;
        param = p;
        this.blocf = blocf;
        this.functionName = functionName;
        this.lc = lc;

        if (ts.existeixTs(functionName)) {
            //new Error_FuncioJaDeclarada().printError(lc, functionName); 
        } else {
            ts.insertElement(functionName, tipus.getTipus(), null);
        }
    }
    
    public void generateCode(){

        cta.generateCode(functionName + ":skip\n");
        cta.push(cta.getStart_stack(), functionName);
        //tp.addRow(new Procedure(tp.getNewNumProc(), 0, functionName, getParams(), 0, tipus));
        cta.generateCode("pmb " + functionName + "\n"); // Preamble
        ts.incAmbit();

        NodeParam currentParam = param;
        while (currentParam != null) {
            String paramName = currentParam.getId();
            String paramType = currentParam.getTipus().toString();
            ts.insertElement(paramName, paramType, null);  
            cta.generateCode("param_s " + paramName + "\n");
            currentParam = currentParam.getNext();
        }

        // Generate code for the function body
        if (blocf != null) {
            blocf.generateCode();
        }

        // Finalize the function with a return
        cta.generateCode("rtn " + cta.getCur_prog() + " ");
        ts.decAmbit();
    }
}
