
package front_end.simbols;

import util.Util;

public class NodeEntrada extends NodeBase{
    
    private String id;
    private Simbol s;
    private int[] lineCode;
    
    public NodeEntrada(String a, int[] lc) {
        super("Entrada", 0);
        this.id = a;
        this.lineCode = lc;
    }

    public void generateCode(){
        s = Util.validateVariableExists(ts, id, lineCode);
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
            case "ENT": return "retInt";
            case "BOOL": return "retBool";
        }
        return null;
    }
    
}
