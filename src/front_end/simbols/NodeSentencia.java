/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeSentencia extends NodeBase{

        private NodeDecl_Variable decl_variable;
        private NodeVarinic2 varinic2;
        private NodeCondicional condicional;
        private NodeBucle nodeBucle;
        private NodeEntrada entrada;
        private NodeSortida sortida;
        
        public NodeSentencia(NodeBucle nodeBucle) {
            super("Sentencia",0);
            this.nodeBucle = nodeBucle;
        }

        public NodeSentencia(NodeDecl_Variable a){
            super("Sentencia",0);
            decl_variable = a;
            decl_variable.generateCode();
        }
        
        public NodeSentencia(NodeVarinic2 a){
            super("Sentencia", 0);
            varinic2 = a;
            if(varinic2.getExprsimple()!=null){
                varinic2.generateCode_exprsimple();
            }else if(varinic2.getExprcomposta()!=null){
                varinic2.generateCode_exprcomposta();
            }
        }
        
        public NodeSentencia(NodeCondicional a){
            super("Sentencia", 0);
            condicional = a;
        }

        public NodeSentencia(NodeEntrada entrada){
            super("Sentencia", 0);
            this.entrada = entrada;
            entrada.generateCode();
        }

        public NodeSentencia(NodeSortida sortida){
            super("Sentencia", 0);
            this.sortida = sortida;
            sortida.generateCode();
        }
}
