package front_end.simbols.Structure;

import front_end.simbols.NodeBase;
import front_end.simbols.NodeDecl_Variable;
import front_end.simbols.NodeEntrada;
import front_end.simbols.NodeSortida;
import front_end.simbols.NodeVarinic2;
import front_end.simbols.Array.NodeDecl_taula;
import front_end.simbols.Array.NodeInic_element_taula;
import front_end.simbols.Conditionals.NodeCondicional;
import front_end.simbols.Loops.NodeBucle;
import front_end.simbols.Procedure.NodeCrida_funcio;
import front_end.simbols.Procedure.NodeDecl_funcio;
import front_end.simbols.Procedure.NodeRetorna;

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
            
            case "inic_element_taula":
                ((NodeInic_element_taula)node).generateCode();
                break;
        }
    }
}
