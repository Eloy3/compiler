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
        
        public NodeSentencia(NodeBucle nodeBucle) {
            super("Sentencia",0);
            this.nodeBucle = nodeBucle;
        }

        public NodeSentencia(NodeDecl_Variable a){
            super("Sentencia",0);
            decl_variable = a;
        }
        
        public NodeSentencia(NodeVarinic2 a){
            super("Sentencia", 0);
            varinic2 = a;
        }
        
        public NodeSentencia(NodeCondicional a){
            super("Sentencia", 0);
            condicional = a;
        }
}
