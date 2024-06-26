
package front_end.simbols;


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

}
