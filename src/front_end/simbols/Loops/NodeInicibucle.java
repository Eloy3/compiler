package front_end.simbols.Loops;

import front_end.simbols.NodeBase;

public class NodeInicibucle extends NodeBase {
    
    private String start_label;

    public NodeInicibucle() {
        super("Inicibucle", 0);
    }
    
    public void generateCode() {
        start_label = cta.newLabel();
        cta.push(cta.getStart_stack(), start_label);
        cta.generateCode(start_label + ":skip\n");
    }
}
