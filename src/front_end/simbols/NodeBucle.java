
package front_end.simbols;


public class NodeBucle extends NodeBase{

    private NodeInicibucle inicibucle;
    private NodeIncambit incambit;
    private NodeDecl_Variable decl_Variable;
    private NodeEtiquetacond etiquetacond;
    private NodeCondicio condicio;
    private NodeCondiciobot condiciobot;
    private NodeComportamentv1 comportamentv1;
    private NodeBlocf blocf;
    private NodeRetornabucle retornabucle;

    //FOR
    public NodeBucle(NodeInicibucle inicibucle, NodeIncambit incambit,
            NodeDecl_Variable decl_Variable, NodeEtiquetacond etiquetacond, NodeCondicio condicio,
            NodeCondiciobot condiciobot, NodeComportamentv1 comportamentv1, NodeBlocf blocf,
            NodeRetornabucle retornabucle) {
        super("Bucle", 0);
        this.inicibucle = inicibucle;
        this.incambit = incambit;
        this.decl_Variable = decl_Variable;
        this.etiquetacond = etiquetacond;
        this.condicio = condicio;
        this.condiciobot = condiciobot;
        this.comportamentv1 = comportamentv1;
        this.blocf = blocf;
        this.retornabucle = retornabucle;
    }

    //WHILE
    public NodeBucle(NodeInicibucle inicibucle, NodeEtiquetacond etiquetacond,
            NodeCondicio condicio, NodeCondiciobot condiciobot, NodeIncambit incambit, NodeBlocf blocf,
            NodeRetornabucle retornabucle) {
        super("Bucle", 0);
        this.inicibucle = inicibucle;
        this.etiquetacond = etiquetacond;
        this.condicio = condicio;
        this.condiciobot = condiciobot;
        this.incambit = incambit;
        this.blocf = blocf;
        this.retornabucle = retornabucle;
    }

    
    
}
