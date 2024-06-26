
package front_end.simbols;


public class NodeEntrada extends NodeBase{
    
    private String id;
    private Simbol s;
    
    public NodeEntrada(String a) {
        super("Entrada", 0);
        this.id = a;
        s = ts.get(id);
        cta.generateCode(paramType()+" " + id + "\n");
        cta.generateCode("call " + (paramType().equals("param_c")?"getStr":"getInt") + "\n");
        cta.generateCode(cta.newVar(s.getNom(), s.getTipus()) +" = " + returnType() + "\n");
        cta.setTemp_id(null);
    }

    private String paramType(){
        if (s.getTipus().equalsIgnoreCase("string")) return "param_c";
        return "param_s";
    }

    private String returnType() {
        switch (s.getTipus()){
            case "ent": return "retInt";
            case "bool": return "retBool";
        }
        return null;
    }
    
}
