package front_end.simbols.Structure;

import java.util.ArrayList;

import data_structures.Procedure;
import front_end.simbols.NodeBase;
import front_end.simbols.Tipus;

public class NodePrincipal extends NodeBase {
    private NodeSentencies sentencies;

    public NodePrincipal(NodeSentencies sentencies) {
        super("Principal", 0);
        this.sentencies = sentencies;
    }

    public void generateCode() {
        ts.insertElement("principal", "NULL", null);
        cta.setTemp_id("principal");
        String start_label = cta.getTemp_id();
        cta.push(cta.getStart_stack(), "principal");
        cta.push(cta.getPproc(), "principal");
        cta.generateCode("principal:skip\n");
        String startLabel = cta.getTemp_id();
        cta.setTemp_id(null);
        tp.addRow(new Procedure(tp.getNewNumProc(), ts.getProfunditat(), startLabel, new ArrayList<>(), 0, Tipus.NULL));

        cta.push(cta.getStart_stack(), start_label);
        cta.setTemp_id("principal");
        if (sentencies != null) {
            sentencies.generateCode();
        }
    }
}
