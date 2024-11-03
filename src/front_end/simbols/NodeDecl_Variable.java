
package front_end.simbols;

import errors.*;

public class NodeDecl_Variable extends NodeBase{
    private NodeTipus nt;
    private NodeVarinic varinic;
    private String id;
    private int[] lc;
    
    public NodeDecl_Variable(NodeTipus nt, String id, NodeVarinic varinic, int[] lc){
        super("Decl_variable", 0);
        this.nt = nt;
        this.varinic = varinic;
        this.id = id;
        this.lc = lc;
        
        if(ts.existeixTs(id)){
            new Error_VarJaDeclarada().printError(lc, id);
        }
    }
    
    public void generateCode(){
        if(varinic == null){
            ts.insertElement(id, nt.getTipus(), null);
        }else{
            String id2 = varinic.getValor();
            switch(varinic.getTipus()){
                case "id":

                    if(!ts.existeixTs(id2)){
                        //System.out.println("Error sintàctic, la variable '"+id2+"' no existeix");
                        new Error_VarNoExisteix().printError(lc, id);
                    }else{
                        //Comprovar a la taula de simbols si id2 té el mateix tipus que id.
                        Simbol param = ts.get(id2);
                        //System.out.println(param);
                        if(!nt.getTipus().equals(param.tipus)){
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
                    if(!nt.getTipus().equals("ent")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        ts.insertElement(id, nt.getTipus().toString(), varinic);
                        generaC3a();
                    }
                    break;

                case "boolea":
                    if(!nt.getTipus().equals("bool")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        ts.insertElement(id, nt.getTipus().toString(), varinic);
                        generaC3a();
                    }
                    break;

                case "tupla":
                    if(!nt.getTipus().equals("tupla")){
                        //System.out.println("Error semàntic, '"+id2+"' no té el mateix tipus que '"+id+"'");
                        new Error_DistintTipus().printError(lc, id);
                    }else{
                        ts.insertElement(id, nt.getTipus().toString(), varinic);

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