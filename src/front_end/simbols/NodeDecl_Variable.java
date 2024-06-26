
package front_end.simbols;

import errors.*;

public class NodeDecl_Variable extends NodeBase{
    private NodeTipus tipus;
    private NodeVarinic varinic;
    private String id;
    
    public NodeDecl_Variable(NodeTipus nt, String id, NodeVarinic nv, int[] lc){
        super("Decl_variable", 0);
        this.tipus = nt;
        this.varinic = nv;
        this.id = id;
        
        if(ts.existeixTs(id)){
            new Error_VarJaDeclarada().printError(lc, id);
        }
        
        if(varinic == null){
            ts.insertElement(id, nt.getTipus(), null);
        }else{
            
        
            String id2 = varinic.getValor();
            switch(varinic.getTipus()){
                case "id":

                    //nova ts
                    if(!ts.existeixTs(id2)){
                        //System.out.println("Error sintàctic, la variable '"+id2+"' no existeix");
                        new Error_VarNoExisteix().printError(lc, id);
                    }else{
                        //Comprovar a la taula de simols si id2 té el mateix tipus que id.
                        Simbol param = ts.get(id2);
                        //System.out.println(param);
                        if(!tipus.getTipus().equals(param.tipus)){
                            //System.out.println("Error semàntic, la variable '"+id2+"' no te el mateix tipus que '"+id+"'");
                            new Error_DistintTipus().printError(lc, id);
                        }else{
                            ts.insertElement(id, nt.getTipus().toString(), param.valor);
                            generaC3a();
                        }
                    }   

                    break;
                case "enter":
                    //System.out.println(tipus.getTipus());
                    if(!tipus.getTipus().equals("ent")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        //tsim.introduir(id, nt.getTipus().toString(), nv);
                        ts.insertElement(id, nt.getTipus().toString(), nv);
                        generaC3a();
                    }
                    break;

                case "boolea":
                    if(!tipus.getTipus().equals("bool")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        //tsim.introduir(id, nt.getTipus().toString(), nv);
                        ts.insertElement(id, nt.getTipus().toString(), nv);
                        generaC3a();
                    }
                    break;

                case "tupla":
                    if(!tipus.getTipus().equals("tupla")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        //tsim.introduir(id, nt.getTipus().toString(), nv);
                        ts.insertElement(id, nt.getTipus().toString(), nv);

                    }
            }
        }
    }
    
    public void generaC3a(){
        Simbol operand = ts.get(id);
        String temp_var;

        temp_var = cta.newTempVar(operand.tipus.toString(), varinic.getValor());

        cta.generateCode(temp_var + " = ");
        cta.generateCode(operand.valor + "\n");

        cta.generateCode(cta.newVar(id, operand.tipus, varinic.getValor()) + " = " + temp_var + "\n");

        cta.setTemp_id(null);
    }
}