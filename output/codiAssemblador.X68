	org $1000
START:
* --> | SKIP | null | null | principal |
principal:
* --> | PARAM_S | null | null | x |
	MOVE.W x_principal,-(A7)
* --> | CALL | null | null | getInt |
	JSR GETINT
* --> | ASSIGNA | retInt | null | x |
	MOVE.W (A7),x_principal
* --> | MODUL | x | 2 | t1 |
	MOVE.W x_principal,D1
	EXT.L D1
	MOVE.W #2,D0
	EXT.L D0
	DIVS.W D0,D1
	SWAP D1
	MOVE.W D1,t1_principal
* --> | ASSIGNA | t1 | null | y |
	MOVE.W t1_principal,y_principal
* --> | IFIGUAL | y | 0 | e1 |
	MOVE.W y_principal,D1
	MOVE.W #0,D0
	CMP.W D0,D1
	BEQ e1
* --> | GOTO | null | null | e2 |
	JMP e2
* --> | SKIP | null | null | e1 |
e1:
* --> | PARAM_T | even | null | null |
	MOVE.L  #even_principal,-(A7)
* --> | CALL | null | null | line |
	JSR SLINE
	ADDA.L #4,A7
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
* --> | PARAM_T | odd | null | null |
	MOVE.L  #odd_principal,-(A7)
* --> | CALL | null | null | line |
	JSR SLINE
	ADDA.L #4,A7
* --> | SKIP | null | null | e0 |
e0:
	SIMHALT
 
x_principal: DS.W 1
t1_principal: DS.W 1
y_principal: DS.W 1
even_principal: DC.B 'even',0
odd_principal: DC.B 'odd',0
 
strout: DS.B 100
	DC.W 0
 
SPRINT:
	MOVE.L 4(A7),A1
	MOVE.L #1,D0
	MOVE.W #100,D1
	TRAP #15
	RTS
SLINE:
	MOVE.L 4(A7),A1
	MOVE.L A1,-(A7)
	JSR SPRINT
	ADDA.L #4,A7
	MOVE.L #11,D0
	MOVE.W #$00FF,D1
	TRAP #15
	ADD.W #1,D1
	AND.W #$00FF,D1
	TRAP #15
	RTS
IPRINT:
	CLR.L D1
	MOVE.W 4(A7),D1
	EXT.L D1
	MOVE.L #3,D0
	TRAP #15
	RTS
ILINE:
	MOVE.W 4(A7),D1
	MOVE.W D1,-(A7)
	JSR IPRINT
	ADDA.W #2,A7
	MOVE.L #11,D0
	MOVE.W #$00FF,D1
	TRAP #15
	ADD.W #1,D1
	AND.W #$00FF,D1
	TRAP #15
	RTS
GETINT:
	MOVE.L #4,D0
	TRAP #15
	MOVE.W D1,4(A7)
	RTS
GETSTR:
	MOVEA.L 4(A7),A1
	MOVE.L #2,D0
	TRAP #15
	RTS
STRCMP:
	CMPM.B (A0)+,(A1)+
	BNE STRRET
	TST.B -1(A0)
	BNE STRCMP
STRRET:
	RTS
out_of_bounds:
	MOVE.L #error_msg,-(A7)
	JSR SLINE
	SIMHALT
error_msg:
	DC.B 'Matriu fora del lmit',0
	END START
