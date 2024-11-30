package front_end.simbols;

public class NodeSentencia extends NodeBase {

    private String sentencia;
    private NodeDecl_Variable decl_variable;
    private NodeVarinic2 varinic2;
    private NodeCondicional condicional;
    private NodeBucle bucle;
    private NodeEntrada entrada;
    private NodeSortida sortida;
    private NodeDecl_funcio decl_funcio;
    private NodeCrida_funcio crida_funcio;
    private NodeRetorna retorna;

    public NodeSentencia(NodeBucle nodeBucle, String sentencia) {
        super("Sentencia", 0);
        this.bucle = nodeBucle;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeDecl_Variable a, String sentencia) {
        super("Sentencia", 0);
        this.decl_variable = a;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeVarinic2 a, String sentencia) {
        super("Sentencia", 0);
        this.varinic2 = a;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeCondicional a, String sentencia) {
        super("Sentencia", 0);
        this.condicional = a;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeEntrada entrada, String sentencia) {
        super("Sentencia", 0);
        this.entrada = entrada;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeSortida sortida, String sentencia) {
        super("Sentencia", 0);
        this.sortida = sortida;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeDecl_funcio decl_funcio, String sentencia) {
        super("Sentencia", 0);
        this.decl_funcio = decl_funcio;
        this.sentencia = sentencia;
    }
    public NodeSentencia(NodeCrida_funcio crida_funcio, String sentencia) {
        super("Sentencia", 0);
        this.crida_funcio = crida_funcio;
        this.sentencia = sentencia;
    }

    public NodeSentencia(NodeRetorna retorna, String sentencia) {
        super("Sentencia", 0);
        this.retorna = retorna;
        this.sentencia = sentencia;
    }

    public void generateCode() {

        switch(sentencia){

            case "decl_variable":
                decl_variable.generateCode();
                break;

            case "varinic2":
                // Check whether it's a simple or compound expression
                if (varinic2.getExprsimple() != null) {
                    varinic2.generateCode_exprsimple();
                } else if (varinic2.getExprcomposta() != null) {
                    varinic2.generateCode_exprcomposta();
                }
                break;

            case "condicional":
                //condicional.generateCode();
                break;

            case "bucle":
                if (bucle.getComportamentv1()==null){
                    bucle.generateCodeWhile();
                }else{
                    bucle.generateCodeFor();
                }  
                break;
            
            case "entrada":
                entrada.generateCode();
                break;
            
            case "sortida":
                sortida.generateCode();
                break;
            
            case "decl_funcio":
                decl_funcio.generateCode();
                break;
            
            case "retorna":
                retorna.generateCode();
                break;
            
            case "crida_funcio":
                crida_funcio.generateCode();
                break;
        }
    }
}
