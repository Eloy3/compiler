
package front_end.simbols;


public class NodeBucle extends NodeBase{

    private NodeInicibucle inicibucle;
    private NodeIncambit incambit;
    private NodeDecl_Variable decl_Variable;
    private NodeEtiquetacond etiquetacond;
    private NodeCondicio condicio;
    private NodeCondiciobot condiciobot;
    private NodeComportamentv1 comportamentv1;

    private NodeBlocf blocf;
    private NodeRetornabucle retornabucle;

    //FOR
    public NodeBucle(NodeInicibucle inicibucle, NodeIncambit incambit,
            NodeDecl_Variable decl_Variable, NodeEtiquetacond etiquetacond, NodeCondicio condicio,
            NodeCondiciobot condiciobot, NodeComportamentv1 comportamentv1, NodeBlocf blocf,
            NodeRetornabucle retornabucle) {
        super("Buclefor", 0);
        this.inicibucle = inicibucle;
        this.incambit = incambit;
        this.decl_Variable = decl_Variable;
        this.etiquetacond = etiquetacond;
        this.condicio = condicio;
        this.condiciobot = condiciobot;
        this.comportamentv1 = comportamentv1;
        this.blocf = blocf;
        this.retornabucle = retornabucle;
    }

    //WHILE
    public NodeBucle(NodeInicibucle inicibucle, NodeEtiquetacond etiquetacond,
                 NodeCondicio condicio, NodeCondiciobot condiciobot, 
                 NodeIncambit incambit, NodeBlocf blocf, 
                 NodeRetornabucle retornabucle)
{
        super("Bucle", 0);
        this.inicibucle = inicibucle;
        this.etiquetacond = etiquetacond;
        this.condicio = condicio;
        this.condiciobot = condiciobot;
        this.incambit = incambit;
        this.blocf = blocf;
        this.retornabucle = retornabucle;
    }

    public void generateCodeWhile(){
        inicibucle();
        etiquetacond();
        if(condicio.getOperador() != null){
            condicio.generateCodeOperador();
        }else{
            condicio.generateCodeID();
        }
        condiciobot(false);
        ts.incAmbit();
        blocf.generateCode();
        retornabucle();
    }
    
    public void generateCodeFor(){
        ts.incAmbit();
        decl_Variable.generateCode();
        inicibucle();
        etiquetacond();
        if(condicio.getOperador() != null){
            condicio.generateCodeOperador();
        }else{
            condicio.generateCodeID();
        }
        condiciobot(false);
        comportamentv1.generateCode();
        blocf.generateCode();
        retornabucle();
    }

    public static void inicibucle(){
        String start_label = cta.newLabel();
        cta.push(cta.getStart_stack(), start_label);
        cta.generateCode(start_label + ":skip\n");
    }

    public static void etiquetacond(){
        cta.generateCode("if ");
        String true_label = cta.newLabel();
        cta.push(cta.getTrue_stack(), true_label);

        String false_label = cta.newLabel();
        cta.push(cta.getFalse_stack(), false_label);
    }

    public static void condiciobot(boolean inverter){
        if (inverter){
            String false_label = cta.getTop(cta.getFalse_stack());
            cta.generateCode("goto " + false_label + "\n");

            String true_label = cta.getTop(cta.getTrue_stack());
            cta.generateCode("goto " + true_label + "\n");
            cta.pop(cta.getTrue_stack());

            cta.generateCode(true_label + ":skip\n");
        } else {
            String true_label = cta.getTop(cta.getTrue_stack());
            cta.generateCode("goto " + true_label + "\n");
            cta.pop(cta.getTrue_stack());

            String false_label = cta.getTop(cta.getFalse_stack());
            cta.generateCode("goto " + false_label + "\n");

            cta.generateCode(true_label + ":skip\n");
        }
        cta.setTemp_id(null);
    }

    public static void retornabucle(){
        cta.generateCode("goto " + cta.getTop(cta.getStart_stack()) + "\n");
        String false_label = cta.getTop(cta.getFalse_stack());
        cta.generateCode(false_label + ":skip\n");
    }

    public NodeComportamentv1 getComportamentv1() {
        return comportamentv1;
    }
    
}
