package front_end.simbols;

public class NodeInicibucle extends NodeBase {
    
    private String start_label;

    public NodeInicibucle() {
        super("Inicibucle", 0);
        start_label = cta.newLabel();
    }
    
    public void generateCode() {
        cta.push(cta.getStart_stack(), start_label);
        cta.generateCode(start_label + ":skip\n");
    }
}
