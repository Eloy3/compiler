
package front_end.simbols;


public class NodeBucle extends NodeBase{

    private NodeDecl_Variable decl_Variable;
    private NodeCondicio condicio;
    private NodeComportamentv1 comportamentv1;

    private NodeBlocf blocf;

    //FOR
    public NodeBucle(NodeDecl_Variable decl_Variable, NodeCondicio condicio,NodeComportamentv1 comportamentv1, NodeBlocf blocf) {
        super("Buclefor", 0);
        this.decl_Variable = decl_Variable;
        this.condicio = condicio;
        this.comportamentv1 = comportamentv1;
        this.blocf = blocf;
    }

    //WHILE
    public NodeBucle(NodeCondicio condicio, NodeBlocf blocf){
        super("Bucle", 0);
        this.condicio = condicio;
        this.blocf = blocf;
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
        ts.decAmbit();
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
        ts.decAmbit();
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
