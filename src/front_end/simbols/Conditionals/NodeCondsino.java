
package front_end.simbols.Conditionals;

import front_end.simbols.NodeBase;
import front_end.simbols.Structure.NodeBlocf;

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
