

package front_end.simbols.Structure;

import front_end.simbols.NodeBase;

public class NodeBlocf2 extends NodeBase{

    private NodeBlocf blocf;
        
    public NodeBlocf2(NodeBlocf a){
        super("Blocf2",0);
        blocf = a;
    }    
    
    public NodeBlocf2(){
        super("Blocf2",0);
        blocf = null;
    }   

    public void generateCode() {
        if (blocf != null) {
            blocf.generateCode();
        }
    }
}
