package front_end.simbols;

public class NodeSentencia extends NodeBase {

    private NodeDecl_Variable decl_variable;
    private NodeVarinic2 varinic2;
    private NodeCondicional condicional;
    private NodeBucle bucle;
    private NodeEntrada entrada;
    private NodeSortida sortida;

    public NodeSentencia(NodeBucle nodeBucle) {
        super("Sentencia", 0);
        this.bucle = nodeBucle;
    }

    public NodeSentencia(NodeDecl_Variable a) {
        super("Sentencia", 0);
        this.decl_variable = a;
    }

    public NodeSentencia(NodeVarinic2 a) {
        super("Sentencia", 0);
        this.varinic2 = a;
    }

    public NodeSentencia(NodeCondicional a) {
        super("Sentencia", 0);
        this.condicional = a;
    }

    public NodeSentencia(NodeEntrada entrada) {
        super("Sentencia", 0);
        this.entrada = entrada;
    }

    public NodeSentencia(NodeSortida sortida) {
        super("Sentencia", 0);
        this.sortida = sortida;
    }

    public void generateCode() {
        if (decl_variable != null) {
            decl_variable.generateCode();  // Generate TAC for declaration
        } else if (varinic2 != null) {
            // Check whether it's a simple or compound expression
            if (varinic2.getExprsimple() != null) {
                varinic2.generateCode_exprsimple();
            } else if (varinic2.getExprcomposta() != null) {
                varinic2.generateCode_exprcomposta();
            }
        } else if (condicional != null) {
            //condicional.generateCode();
        } else if (bucle != null) {
            if (bucle.getComportamentv1()==null){
                bucle.generateCodeWhile();
            }else{
                bucle.generateCodeFor();
            }   
        } else if (entrada != null) {
            entrada.generateCode();  
        } else if (sortida != null) {
            sortida.generateCode();  
        }
    }
}
