
package front_end.simbols;


public class NodeCondsino extends NodeBase{

    private NodeBlocf blocf;
       
    public NodeCondsino(NodeBlocf a){
        super("Condsino", 0);
        blocf = a;
        
    }
    
    public NodeCondsino(){
        super("Condsino",0);
        //cta.removeGotoElse();
    }

    public void generateCode(){
        blocf.generateCode();
    } 
}
