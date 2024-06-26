
package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeCondicio extends NodeBase{
    
    private NodeExprsimple operand1;
    private NodeExprsimple operand2;
    private NodeOperador_cond operador;
    
    private String ID;
    
    public NodeCondicio(String id) {
        super("NodeCondicio", 0);
        ID = id;
        cta.generateCode(id + " then ");
    }
    
    public NodeCondicio(NodeExprsimple v, NodeOperador_cond o, NodeExprsimple v1) {
        super("NodeCondicio", 0);
        operand1 = v;
        operand2 = v1;
        operador = o;
        
        cta.generateCode(operand1.getValor()+" ");
        cta.generateCode(operador.getOperador()+ " ");
        cta.generateCode(operand2.getValor() + " ");
    }
    
    
}
