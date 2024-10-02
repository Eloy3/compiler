

package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeCondiciobot extends NodeBase{
    private boolean inverter;
    public NodeCondiciobot(boolean inverter){
        super("Condiciobot",0);
        this.inverter = inverter;
    }

    public void generateCode(){
        if (inverter){
            String false_label = cta.getTop(cta.getFalse_stack());
            cta.generateCode("goto " + false_label + "\n");

            String true_label = cta.getTop(cta.getTrue_stack());
            cta.generateCode("goto " + true_label + "\n");
            cta.pop(cta.getTrue_stack());

            cta.generateCode(true_label + ":skip\n");
        } else {
            String true_label = cta.getTop(cta.getTrue_stack());
            cta.generateCode("goto " + true_label + "\n");
            cta.pop(cta.getTrue_stack());

            String false_label = cta.getTop(cta.getFalse_stack());
            cta.generateCode("goto " + false_label + "\n");

            cta.generateCode(true_label + ":skip\n");
        }
        cta.setTemp_id(null);
    }
}
