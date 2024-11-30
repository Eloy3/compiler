package front_end.simbols;

public class NodeTipus extends NodeBase {
   
    private Tipus tipus;
    
    public NodeTipus(Tipus t){
        super("Tipus", 0);
        this.tipus = t;
    }

    public String getTipusAsString() {
        return tipus.toString();
    }

    public Tipus getTipus(){
        return tipus;
    }

}
