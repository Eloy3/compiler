
package compilador.simbols;


public class NodeCondsino extends NodeBase{

    private NodeBlocf blocf;
       
    public NodeCondsino(NodeBlocf a){
        super("Condsino", 0);
        blocf = a;
        String etiquetaFi = cta.getTop(cta.getEnd_stack());
        cta.pop(cta.getFalse_stack());
        cta.generateCode(etiquetaFi + ":skip\n");
    }
    
    public NodeCondsino(){
        super("Condsino",0);
        cta.removeGotoElse();
    }
}
