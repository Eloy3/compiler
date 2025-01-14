package front_end.simbols;

import org.w3c.dom.Node;

public class NodeSentencia extends NodeBase {

    private String sentencia;
    private NodeBase node;

    public NodeSentencia(NodeBase node, String sentencia) {
        super("Sentencia", 0);
        this.node = node;
        this.sentencia = sentencia;
    }

    public void generateCode() {

        switch(sentencia){

            case "decl_variable":
                ((NodeDecl_Variable) node).generateCode();
                break;

            case "varinic2":
                // Check whether it's a simple or compound expression
                ((NodeVarinic2) node).generateCode();
                break;
/*                 NodeVarinic2 varinic2 = (NodeVarinic2) node;
                if (varinic2.getExprsimple() != null || varinic2.getCrida_funcio() != null) {
                    varinic2.generateCode();
                } else if (varinic2.getExprcomposta() != null) {
                    varinic2.generateCodeExprcomposta();
                }
                break; */

            case "condicional":
                ((NodeCondicional) node).generateCode();
                break;

            case "bucle":
                NodeBucle bucle = (NodeBucle) node;
                if (bucle.getComportamentv1()==null){
                    bucle.generateCodeWhile();
                }else{
                    bucle.generateCodeFor();
                }  
                break;
            
            case "entrada":
                ((NodeEntrada)node).generateCode();
                break;
            
            case "sortida":
                ((NodeSortida)node).generateCode();
                break;
            
            case "decl_funcio":
                ((NodeDecl_funcio)node).generateCode();
                break;
            
            case "retorna":
                ((NodeRetorna)node).generateCode();
                break;
            
            case "crida_funcio":
                ((NodeCrida_funcio)node).generateCode();
                break;
            
            case "decl_taula":
                ((NodeDecl_taula)node).generateCode();
                break;
        }
    }
}
