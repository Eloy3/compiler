
package compilador.simbols;

public class NodeInicibucle extends NodeBase{
    
    public NodeInicibucle(){
        super("Inicibucle", 0);

        String start_label = cta.newLabel();
        cta.push(cta.getStart_stack(), start_label);

        cta.generateCode(start_label + ":skip\n");
    }
    
}
