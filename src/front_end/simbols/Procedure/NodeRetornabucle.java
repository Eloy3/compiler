
package front_end.simbols.Procedure;

import front_end.simbols.NodeBase;

public class NodeRetornabucle extends NodeBase{

    public NodeRetornabucle() {
        super("Retornabucle", 0);
    }

    public void generateCode(){
        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String false_label = cta.getTop(cta.getFalse_stack());
        cta.generateCode(false_label + ":skip\n");
    }
}
