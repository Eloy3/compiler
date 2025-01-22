package front_end.simbols;

import front_end.simbols.Procedure.NodeCrida_funcio;

public class NodeVarinic extends NodeBase{

    private NodeExprsimple exprsimple;
    private NodeCrida_funcio crida_funcio;
    
    public NodeVarinic(NodeExprsimple e){
        super("Varinic", 0);
        this.exprsimple = e;
    }
    public NodeVarinic(NodeCrida_funcio c){
        super("Varinic", 0);
        this.crida_funcio = c;
    }
    public NodeVarinic(){
        super("Varinic",0);
    }

    @Override
    public String toString() {
        return exprsimple.toString();
    }
    
    public String getTipus() {
        return exprsimple.getTipus().toString();
    }

    public String getValor() {
        return exprsimple.getValor();
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }

    public void setExprsimple(NodeExprsimple exprsimple) {
        this.exprsimple = exprsimple;
    }
    
    public String getNomProcedure(){
        return crida_funcio.getFunctionName();
    }
    public NodeCrida_funcio getCrida_funcio() {
        return crida_funcio;
    }
    public void setCrida_funcio(NodeCrida_funcio crida_funcio) {
        this.crida_funcio = crida_funcio;
    }

}
