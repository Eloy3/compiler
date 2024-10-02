/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package front_end.simbols;

/**
 *
 * @author Eloy
 */
public class NodeSentenciaf extends NodeBase{

    private NodeDecl_Variable decl_variable;
        private NodeVarinic2 varinic2;
        private NodeCondicional condicional;
        private NodeBucle nodeBucle;
        private NodeEntrada entrada;
        private NodeSortida sortida;
        
        public NodeSentenciaf(NodeBucle nodeBucle) {
            super("Sentencia",0);
            this.nodeBucle = nodeBucle;
        }

        public NodeSentenciaf(NodeDecl_Variable a){
            super("Sentencia",0);
            decl_variable = a;
            decl_variable.generateCode();
        }
        
        public NodeSentenciaf(NodeVarinic2 a){
            super("Sentencia", 0);
            varinic2 = a;
            if(varinic2.getExprsimple()!=null){
                varinic2.generateCode_exprsimple();
            }else if(varinic2.getExprcomposta()!=null){
                varinic2.generateCode_exprcomposta();
            }
        }
        
        public NodeSentenciaf(NodeCondicional a){
            super("Sentencia", 0);
            condicional = a;
        }

        public NodeSentenciaf(NodeEntrada entrada){
            super("Sentencia", 0);
            this.entrada = entrada;
            entrada.generateCode();
        }

        public NodeSentenciaf(NodeSortida sortida){
            super("Sentencia", 0);
            this.sortida = sortida;
            sortida.generateCode();
        }
}