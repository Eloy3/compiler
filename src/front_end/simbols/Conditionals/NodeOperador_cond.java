
package front_end.simbols.Conditionals;

import front_end.simbols.NodeBase;

public class NodeOperador_cond extends NodeBase {

    private String operator;
    private boolean isLogic;
    private boolean isNumeric;

    public NodeOperador_cond(String a, boolean isLogic, boolean isNumeric) {
        super("Operador_cond", 0);
        this.operator = a;
        this.isLogic = isLogic;
        this.isNumeric = isNumeric;

    }

    public String getOperador() {
        return operator;
    }

    public boolean isLogic() {
        return isLogic;
    }

    public void setLogic(boolean isLogic) {
        this.isLogic = isLogic;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean isNumeric) {
        this.isNumeric = isNumeric;
    }

    

}
