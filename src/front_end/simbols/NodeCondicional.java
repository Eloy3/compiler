
package front_end.simbols;

import util.JumpUtil;

public class NodeCondicional extends NodeBase{

    private NodeCondicio condicio;
    private NodeBlocf blocf;
    private NodeCondsino condsino;
    
    public NodeCondicional(NodeCondicio a, NodeBlocf b, NodeCondsino c){
        super("Condicional",0);
        condicio = a;
        blocf = b;
        condsino = c;
    }

    public void generateCode(){
        JumpUtil.etiquetacond(cta);
        if (condicio.getOperador() != null) {
            condicio.generateCodeOperador();
        } else {
            condicio.generateCodeID();
        }
        JumpUtil.condiciobot(cta, false);
        blocf.generateCode();
        String falseLabel = cta.getTop(cta.getFalse_stack());
        cta.generateCode(falseLabel + ":skip\n");
    }
}
