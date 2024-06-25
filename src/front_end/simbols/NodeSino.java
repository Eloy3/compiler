
package compilador.simbols;


public class NodeSino extends NodeBase{

        
    public NodeSino() {
        super("Sino", 0);

        String end_label = cta.newLabel();
        cta.push(cta.getEnd_stack(), end_label);
        cta.generateCode("goto " + end_label + "\n");
        String false_label = cta.getTop(cta.getFalse_stack());
        cta.generateCode(false_label + ":skip\n");
    }
    
    
}
