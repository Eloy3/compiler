
package front_end.simbols;

public class NodeOperador_cond extends NodeBase {

    private String operator;

    public NodeOperador_cond(String a) {
        super("Operador_cond", 0);
        this.operator = a;

    }

    public String getOperador() {
        return operator;
    }


}
