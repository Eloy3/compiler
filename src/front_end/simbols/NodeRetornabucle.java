
package compilador.simbols;


public class NodeRetornabucle extends NodeBase{

    public NodeRetornabucle() {
        super("SymGSTARTLOOP", 0);

        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String false_label = cta.getTop(cta.getFalse_stack());
        cta.generateCode(false_label + ":skip\n");
    }
}
