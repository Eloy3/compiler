
package compilador.simbols;

public class NodePrincipal extends NodeBase {

    private NodeSentencies sentencies;

    public NodePrincipal(NodeSentencies sentencies) {
        super("Principal", 0);
        this.sentencies = sentencies;
        //ts.insertElement(new Simbol("program", MAINPROG, "NULL", ts.getParams()));
        //ts.emptyParams();
        tp.calculateLocalVarSize(tv);
        tancaFitxers();
    }
}
