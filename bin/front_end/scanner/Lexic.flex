package front_end.scanner;

import java.io.*;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import front_end.parser.ParserSym;
import front_end.simbols.Simbol;

import java.util.logging.Level;
import java.util.logging.Logger;
%%

/****
 Indicació de quin tipus d'analitzador sintàctic s'utilitzarà. En aquest cas 
 es fa ús de Java CUP.
 ****/
%cup

// Per indicar que la classe és pública
%public        

//El nom de la classe
%class Lexic        

// El tipus dels tokens identificats
%int           
%char
%line
%column

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

//Declaracions
id = [A-Za-z_][A-Za-z0-9_]*
enter = [0-9]+
boolea = "cert" | "fals"

//operadors aritmètics
suma         = \+
resta        = \-
mul          = \*
div          = \/
mod          = %

//operadors relacionals
igual = "=="
diferent = "!="

//operadors lògics
OLi = "&&"
OLo = "||"
OLno = "!"


assign       = \=
eparen       = \(
dparen       = \)
eclaudator   = \[
dclaudator   = \]
eclau        = \{
dclau        = \}
punticoma    = ;
coma         = ,


principal = "principal"
ent = "ent"
bool = "bool"
const = "const"
tupla = "tupla"
funcio = "funcio"
si = "si"
si_no = "si_no"
mentre = "mentre"
per = "per"
retorna = "retorna"
buit = "buit"
sortida = "sortida"
sortidaln = "sortidaln"
entrada = "entrada"
ENDLINE   = [\r\n]+
ws = [' '|'\t']+

// El següent codi es copiarà també, dins de la classe. És a dir, si es posa res
// ha de ser en el format adient: mètodes, atributs, etc. 
%{
    
    /***
       Mecanismes de gestió de símbols basat en ComplexSymbol.
     ***/

    private ComplexSymbol symbol(int type) {
      return new ComplexSymbol(ParserSym.terminalNames[type], type);
    }

    private Symbol symbol(int type, Object value) {
      return new ComplexSymbol(ParserSym.terminalNames[type], type, value);
    }
    
    Writer bw;
    public void writeToken(String token) {
      try {
        bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output/tokens.txt", true), "utf-8"));
        bw.write(token+"\n");
        bw.close();
      } catch (FileNotFoundException ex) {
          Logger.getLogger(Lexic.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(Lexic.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(Lexic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    int numero_linea = 0;

    public int getLine(){
      return yyline + 1;
    }

    public int getColumn(){
      return yycolumn + 1;
    }

    public int [] getLineColumn() {
      int [] line_col = new int[2];

      line_col[0] = yyline + 1;
      line_col[1] = yycolumn + 1;

      return line_col;
    }
%}

%%
// Regles/accions
// És molt important l'ordre de les regles!!!

{suma}                   { writeToken(this.yytext()); return symbol(ParserSym.SUMA, this.yytext());   }
{resta}                  { writeToken(this.yytext()); return symbol(ParserSym.RESTA, this.yytext());  }
{mul}                    { writeToken(this.yytext()); return symbol(ParserSym.MUL, this.yytext());    }
{div}                    { writeToken(this.yytext()); return symbol(ParserSym.DIV, this.yytext());    }
{igual}                  { writeToken(this.yytext()); return symbol(ParserSym.IGUAL, this.yytext());    }
{diferent}               { writeToken(this.yytext()); return symbol(ParserSym.DIFERENT, this.yytext());    }
{OLi}                    { writeToken(this.yytext()); return symbol(ParserSym.OLI, this.yytext());    }
{OLo}                    { writeToken(this.yytext()); return symbol(ParserSym.OLO, this.yytext());    }
{eparen}                 { writeToken(this.yytext()); return symbol(ParserSym.EParen); }
{dparen}                 { writeToken(this.yytext()); return symbol(ParserSym.DParen); }
{eclaudator}             { writeToken(this.yytext()); return symbol(ParserSym.EClaudator); }
{dclaudator}             { writeToken(this.yytext()); return symbol(ParserSym.DClaudator); }
{eclau}                  { writeToken(this.yytext()); return symbol(ParserSym.EClau); }
{dclau}                  { writeToken(this.yytext()); return symbol(ParserSym.DClau); }
{enter}                  { writeToken(this.yytext()); return symbol(ParserSym.ENTER, this.yytext());  }
{boolea}                 { writeToken(this.yytext()); return symbol(ParserSym.BOOLEA, this.yytext());  }

{assign}                 { writeToken(this.yytext()); return symbol(ParserSym.ASSIGN, "="); }

{sortidaln}              { writeToken(this.yytext()); return symbol(ParserSym.SORTIDALN, "sortidaln"); }
{sortida}                { writeToken(this.yytext()); return symbol(ParserSym.SORTIDA, "sortida"); }
{entrada}                { writeToken(this.yytext()); return symbol(ParserSym.ENTRADA, "entrada"); }
{ent}                    { writeToken(this.yytext()); return symbol(ParserSym.ENT, "ent"); }
{bool}                   { writeToken(this.yytext()); return symbol(ParserSym.BOOL, "bool"); }
{const}                  { writeToken(this.yytext()); return symbol(ParserSym.CONST, "const"); }
{tupla}                  { writeToken(this.yytext()); return symbol(ParserSym.TUPLA, "tupla"); }
{funcio}                 { writeToken(this.yytext()); return symbol(ParserSym.FUNCIO, "funcio"); }
{buit}                   { writeToken(this.yytext()); return symbol(ParserSym.BUIT, "buit"); }
{retorna}                { writeToken(this.yytext()); return symbol(ParserSym.RETORNA, "retorna"); }
{mentre}                 { writeToken(this.yytext()); return symbol(ParserSym.MENTRE, "mentre"); }
{per}                    { writeToken(this.yytext()); return symbol(ParserSym.PER, "per"); }
{si}                     { writeToken(this.yytext()); return symbol(ParserSym.SI, "si"); }
{si_no}                  { writeToken(this.yytext()); return symbol(ParserSym.SINO, "si_no"); }
{principal}              { writeToken(this.yytext()); return symbol(ParserSym.PRINCIPAL, "principal"); }
{punticoma}              { writeToken(this.yytext()); return symbol(ParserSym.PUNTICOMA, ";"); }
{coma}                   { writeToken(this.yytext()); return symbol(ParserSym.COMA, ","); }
{id}                     { writeToken(this.yytext()); return symbol(ParserSym.ID, this.yytext()); }

{ENDLINE}                { /*return symbol(ParserSym.EOF);    */    numero_linea++;  }
{ws}                     { /* No fer res amb els espais */  }