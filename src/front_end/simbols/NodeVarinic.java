/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package front_end.simbols;

public class NodeVarinic extends NodeBase{

    private NodeExprsimple exprsimple;
    
    public NodeVarinic(NodeExprsimple e){
        super("Varinic", 0);
        this.exprsimple = e;
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
}
