/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package compilador.simbols;

/**
 *
 * @author Eloy
 */
public class NodeSentenciaf extends NodeBase{

        private NodeDecl_Variable decl_variable;
        private NodeVarinic2 varinic2;
        private NodeCondicional condicional;
        
        public NodeSentenciaf(NodeDecl_Variable a){
            super("Sentenciaf",0);
            decl_variable = a;
        }
        
        public NodeSentenciaf(NodeVarinic2 a){
            super("Sentenciaf", 0);
            varinic2 = a;
        }
        
        public NodeSentenciaf(NodeCondicional a){
            super("Sentenciaf", 0);
            condicional = a;
        }
}