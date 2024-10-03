
package front_end.simbols;

public class NodePrincipal extends NodeBase {

    private NodeSentencies sentencies;

    public NodePrincipal(NodeSentencies sentencies) {
        super("Principal", 0);
        this.sentencies = sentencies;
        if (sentencies != null) {
            sentencies.generateCode();
        }
        tp.calculateLocalVarSize(tv);
        tancaFitxers();

        
    }
}
