

package front_end.simbols;

import errors.Error_VarNoExisteix;
import front_end.simbols.NodeValor.exprsimple;


public class NodeSortida extends NodeBase{

    private NodeLlistaValors LlistaValors;
    private boolean linea;
    private int[] lc;
    
    public NodeSortida(NodeLlistaValors lv, boolean l, int[] lc){
        super("Sortida", 0);
        LlistaValors = lv;
        linea = l;
        this.lc = lc;
    }
    
    public void generateCode(){
        try {
            // Generate parameter type and call code
            cta.generateCode(paramType() + " " + LlistaValors.getValor().getValor() + "\n");
            cta.generateCode("call " + ((linea) ? "line" : "print") + "\n");

        } catch (Error_VarNoExisteix e) {
            // Handle undefined variable error
            e.printError(lc, LlistaValors.getValor().getValor());
        }
        cta.setTemp_id(null);
    }

    private String paramType() throws Error_VarNoExisteix{
        // Check if the value is an identifier (ID)
        if (LlistaValors.getValor().getTipus() == exprsimple.id) {
            Simbol id = ts.get(LlistaValors.getValor().getValor());

            // If the variable does not exist in the symbol table, throw an error
            if (id == null) {
                throw new Error_VarNoExisteix();
            }

            // Determine parameter type based on the variable's type
            String varType = id.getTipus();
            if (varType.equalsIgnoreCase("ent")) {
                return "param_s"; // Integer parameter
            } else if (varType.equalsIgnoreCase("bool")) {
                return "param_b"; // Boolean parameter
            } else {
                throw new Error_VarNoExisteix(); // Catch-all for unsupported types
            }
        } else {
            // If it's a constant value, assume "param_c"
            return "param_c";
        }
    }

}
