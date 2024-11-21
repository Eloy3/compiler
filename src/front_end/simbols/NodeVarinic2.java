
package front_end.simbols;

import errors.*;
import java.util.ArrayList;

/**
 *
 * @author Eloy
 */
public class NodeVarinic2 extends NodeBase{

    private NodeExprsimple exprsimple;
    private NodeExprcomposta exprcomposta;
    private String id;
    private int[] lc;
    
    private NodeExprsimple a;
    private NodeExprsimple b;
    private Simbol operand1;
    private Simbol operand2;
    private Simbol op;
    
    public NodeVarinic2(String id, NodeExprsimple e, int[] lc){
        super("Varinic2", 0);
        this.id = id;
        this.exprsimple = e;
        this.lc = lc;
    }
    public void generateCode_exprsimple(){
        String id2 = exprsimple.getValor();
        Simbol var = ts.get(id);
        
        Simbol param = ts.get(id2);
        if(!ts.existeixTs(id)){
            new Error_VarNoExisteix().printError(lc, id); 
        }else{
            switch(exprsimple.getTipus().toString()){
                case "id":
                    if(!ts.existeixTs(id2)){
                        new Error_VarNoExisteix().printError(lc, id2);
                    }else{
                        //Comprovar a la taula de simbols si id2 té el mateix tipus que id.
                        if(!var.tipus.equals(param.tipus)){
                            new Error_DistintTipus().printError(lc, id);
                        }else{
                            calcOcup();
                        }
                    }             

                    break;
                case "enter":
                    if(!var.tipus.equals("ENT")){
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        calcOcup();
                    }
                    break;
                
                case "boolea":
                    if(!var.tipus.equals("BOOL")){
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        calcOcup();
                    }
                    break;
            }
        }
    }
    
    public NodeVarinic2(String id, NodeExprcomposta e, int[] lc){
        super("Varinic2",0);
        this.id = id;
        this.exprcomposta = e;
        this.a = e.getA();
        this.b = e.getB();
              
    }

    public void generateCode_exprcomposta(){
        op = ts.get(id);
        if(!ts.existeixTs(id)){
            new Error_VarNoExisteix().printError(lc, id); 
        }else{
            switch(op.tipus){
                case "ent":
                    
                    if(a.getTipus()==NodeExprsimple.exprsimple.id){
                        //El primer operand es un id, per tant ha de existir a la ts
                        if (ts.get(a.getValor()) == null) {
                            new Error_VarNoExisteix().printError(lc, a.getName());
                        }else{
                             operand1 = ts.get(a.getValor());
                             //EL primer operand també ha de ser un enter
                             if(!operand1.getTipus().equals("ENT")){
                                new Error_DistintTipus().printError(lc, a.getName());
                            }else{
                                //Miram el segon operand
                                if(b.getTipus()==NodeExprsimple.exprsimple.id){
                                    //El segon operand es un id, per tant ha de existir a la ts
                                    if (ts.get(b.getValor()) == null) {
                                        new Error_VarNoExisteix().printError(lc, b.getName());
                                    }else{
                                         operand2 = ts.get(b.getValor());
                                         //EL segon operand també ha de ser un enter
                                         if(!operand2.getTipus().equals("ENT")){
                                            new Error_DistintTipus().printError(lc, b.getName());
                                        }else{
                                             calcOcup2();
                                        }
                                    }
                                }else if(b.getTipus()==NodeExprsimple.exprsimple.enter){
                                    calcOcup2();
                                }else{
                                    new Error_DistintTipus().printError(lc, b.getName());
                                }
                            }
                        }
                    }else if(a.getTipus()==NodeExprsimple.exprsimple.enter){
                        
                        //Miram el seogn operand
                        if(b.getTipus()==NodeExprsimple.exprsimple.id){
                            //El segon operand es un id, per tant ha de existir a la ts
                            if (ts.get(b.getValor()) == null) {
                                new Error_VarNoExisteix().printError(lc, b.getName());
                            }else{
                                 operand2 = ts.get(b.getValor());
                                 //EL segon operand també ha de ser un enter
                                 if(!operand2.getTipus().equals("ENT")){
                                    new Error_DistintTipus().printError(lc, b.getName());
                                }else{
                                     calcOcup2();
                                }
                            }
                        }else if(b.getTipus()==NodeExprsimple.exprsimple.enter){
                            calcOcup2();
                        }else{
                            new Error_DistintTipus().printError(lc, b.getName());
                        }
                    }else{
                        new Error_DistintTipus().printError(lc, a.getName());
                    }
                    break;
                case "bool":
                    
                    if(a.getTipus()==NodeExprsimple.exprsimple.id){
                        //El primer operand es un id, per tant ha de existir a la ts
                        if (ts.get(a.getValor()) == null) {
                            new Error_VarNoExisteix().printError(lc, a.getName());
                        }else{
                             operand1 = ts.get(a.getValor());
                             //EL primer operand també ha de ser un boolea
                             if(!operand1.getTipus().equals("BOOL")){
                                new Error_DistintTipus().printError(lc, a.getName());
                            }else{
                                //Miram el segon operand
                                if(b.getTipus()==NodeExprsimple.exprsimple.id){
                                    //El segon operand es un id, per tant ha de existir a la ts
                                    if (ts.get(b.getValor()) == null) {
                                        new Error_VarNoExisteix().printError(lc, b.getName());
                                    }else{
                                         operand2 = ts.get(b.getValor());
                                         //EL segon operand també ha de ser un enter
                                         if(!operand2.getTipus().equals("BOOL")){
                                            new Error_DistintTipus().printError(lc, b.getName());
                                        }else{
                                             calcOcup2();
                                        }
                                    }
                                }else if(b.getTipus()==NodeExprsimple.exprsimple.boolea){
                                    calcOcup2();
                                }else{
                                    new Error_DistintTipus().printError(lc, b.getName());
                                }
                            }
                        }
                    }else if(a.getTipus()==NodeExprsimple.exprsimple.boolea){
                        
                        //Miram el seogn operand
                        if(b.getTipus()==NodeExprsimple.exprsimple.id){
                            //El segon operand es un id, per tant ha de existir a la ts
                            if (ts.get(b.getValor()) == null) {
                                new Error_VarNoExisteix().printError(lc, b.getName());
                            }else{
                                 operand2 = ts.get(b.getValor());
                                 //EL segon operand també ha de ser un enter
                                 if(!operand2.getTipus().equals("BOOL")){
                                    new Error_DistintTipus().printError(lc, b.getName());
                                }else{
                                     calcOcup2();
                                }
                            }
                        }else if(b.getTipus()==NodeExprsimple.exprsimple.boolea){
                            calcOcup2();
                        }else{
                            new Error_DistintTipus().printError(lc, b.getName());
                        }
                    }else{
                        new Error_DistintTipus().printError(lc, a.getName());
                    }
                    break;
            }
        }
    }
    private void calcOcup(){
        Simbol operand = ts.get(id);
        String temp_var;

        temp_var = cta.newTempVar(operand.tipus.toString(), exprsimple.getValor());

        cta.generateCode(temp_var + " = ");
        cta.generateCode(exprsimple.getValor() + "\n");

        cta.generateCode(cta.newVar(id, operand.tipus, exprsimple.getValor()) + " = " + temp_var + "\n");

        cta.setTemp_id(null);
    }
    
    private void calcOcup2(){
        Simbol operand = ts.get(id);
        String temp_var;
        temp_var = cta.newTempVar(operand.tipus.toString(), a.getValor());

        cta.generateCode(temp_var + " = ");
        cta.generateCode(a.getValor() + " " + exprcomposta.getOperador().getTipus()+ " " + b.getValor() + "\n");

        cta.generateCode(cta.newVar(id, operand.tipus, a.getValor()) + " = " + temp_var + "\n");
    }
    
    @Override
    public String toString() {
        return exprsimple.toString();
    }
    
    public String getTipus() {
        return exprsimple.getTipus().toString();
    }

    public String getValor() {
        return exprsimple.getValor();
    }

    public String getId() {
        return id;
    }

    public NodeExprsimple getExprsimple() {
        return exprsimple;
    }
    
    public NodeExprcomposta getExprcomposta() {
        return exprcomposta;
    }

}
