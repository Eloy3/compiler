
package front_end.simbols;

public class NodePrograma extends NodeBase{
    public NodePrograma() {
        super("Programa", 0);

        cta.generateCode("goto principal\n");
        
        String start_label = cta.getTemp_id();
        cta.push(cta.getStart_stack(), "principal");
        cta.push(cta.getPproc(), "principal");
        cta.generateCode("principal" + ":skip\n");
        cta.push(cta.getStart_stack(), start_label);
        cta.setTemp_id("principal");
    }
}
