
package compilador.simbols;


public class NodeEtiquetacond extends NodeBase{
    
    public NodeEtiquetacond(){
        super("Etiquetacond",0);
        
        cta.generateCode("if ");
        String true_label = cta.newLabel();
        cta.push(cta.getTrue_stack(), true_label);

        String false_label = cta.newLabel();
        cta.push(cta.getFalse_stack(), false_label);
        
    }

}
