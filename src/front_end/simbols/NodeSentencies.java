/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package compilador.simbols;

/**
 *
 * @author Eloy
 */
public class NodeSentencies extends NodeBase{
    
    private final NodeSentencia Sentencia;
    private final NodeSentencies2 Sentencies2;

    public NodeSentencies(NodeSentencia a, NodeSentencies2 b) {
        super("SENTLISTSEP", 0);
        this.Sentencia = a;
        this.Sentencies2 = b;
    }

}
