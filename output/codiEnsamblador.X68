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
	MOVE.L #-1, (A7)
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
* --> | ASSIGNA | "test" | null | t3 |
* --> | ASSIGNA | t3 | null | test |
* --> | PARAM_T | test | null | null |
	MOVE.L  #test,-(A7)
* --> | CALL | null | null | line |
	JSR SLINE
	ADDA.L #4,A7
* --> | ASSIGNA | "even" | null | t5 |
* --> | ASSIGNA | t5 | null | even |
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
	MOVE.L  #even,-(A7)
* --> | CALL | null | null | line |
	JSR SLINE
	ADDA.L #4,A7
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
* --> | PARAM_T | odd | null | null |
	MOVE.L  #odd,-(A7)
* --> | CALL | null | null | line |
	JSR SLINE
	ADDA.L #4,A7
* --> | SKIP | null | null | e0 |
e0:
	SIMHALT
 
x_principal: DS.W 1
t1_principal: DS.W 1
y_principal: DS.W 1
t3: DC.B 't3',0
test: DC.B 'test',0
t5: DC.B 't5',0
even: DC.B 'even',0
odd: DC.B 'odd',0
 
strout: DS.B 100
	DC.W 0
 
ISUMA:
	BTST.L #15,D0
	BEQ ADD2
	NOT.W D0
	ADDQ.W #1,D0
	BTST.L #15,D1
	BEQ ADD1
	NOT.W D1
	ADDQ.W #1,D1
	ADD.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	JSR ADD4
ADD1:
	SUB.W D0,D1
	JSR ADD4
ADD2:
	BTST.L #15,D1
	BEQ ADD3
	NOT.W D1
	ADDQ.W #1,D1
	SUB.W D1,D0
	MOVE.W  D0,D1
	JSR ADD4
ADD3:
	ADD.W D0,D1
ADD4:
	RTS
IRESTA:
	BTST.L #15,D1
	BEQ SUB2
	NOT.W D1
	ADDQ.W #1,D1
	BTST.L #15,D0
	BEQ SUB1
	NOT.W D0
	ADDQ.W #1,D0
	SUB.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	RTS
SUB1:
	ADD.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	RTS
SUB2:
	BTST.L #15,D0
	BEQ SUB3
	NOT.W D0
	ADDQ.W #1,D0
	ADD.W D0,D1
	RTS
SUB3:
	SUB.W D0,D1
	RTS
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
STRCPY:
	MOVE.B (A0)+,(A1)+
	BNE STRCPY
	RTS
STRCMP:
	CMPM.B (A0)+,(A1)+
	BNE STRRET
	TST.B -1(A0)
	BNE STRCMP
STRRET:
	RTS
STRCON:
	CLR.W D0
	MOVE.W #100,D1
STRCON1:
	MOVE.B (A0)+,(A2)+
	BEQ STRCON2
	ADDQ.W #1,D0
	CMP.W D0,D1
	BEQ STRCON3
	JMP STRCON1
STRCON2:
	SUBA.L #1,A2
STRCON3:
	MOVE.B (A1)+,(A2)+
	BEQ STRCON4
	ADDQ.W #1,D0
	CMP.W D0,D1
	BEQ STRCON4
	JMP STRCON3
STRCON4:
	RTS
out_of_bounds:
	MOVE.L #error_msg,-(A7)
	JSR SLINE
	SIMHALT
error_msg:
	DC.B 'Matriu fora del lmit',0
	END START
