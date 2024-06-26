

package front_end.simbols;

import front_end.simbols.NodeValor.exprsimple;


public class NodeSortida extends NodeBase{

    private NodeLlistaValors LlistaValors;
    private boolean linea;
    
    public NodeSortida(NodeLlistaValors lv, boolean l){
        super("Sortida", 0);
        LlistaValors = lv;
        linea = l;

        cta.generateCode(paramType()+" " + LlistaValors.getValor().getValor() + "\n");
        cta.generateCode("call "+((linea)?"line" : "print")+"\n");

        cta.setTemp_id(null);
        
    }
    
    private String paramType(){
        
        if(LlistaValors.getValor().getTipus()==exprsimple.id){
            Simbol id = ts.get(LlistaValors.getValor().getValor());
            if (id.getTipus().equalsIgnoreCase("string")) return "param_c";
            else return "param_s";
        }else{
            return "param_c";
        }

    }

}
