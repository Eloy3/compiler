	org $1000
START:
* --> | SKIP | null | null | principal |
principal:
* --> | ASSIGNA | 3 | null | t0 |
	MOVE.W #3,t0_principal
* --> | ASSIGNA | t0 | null | a |
	MOVE.W t0_principal,a_principal
* --> | SKIP | null | null | e0 |
e0:
* --> | IFDIFERENT | a | 0 | e1 |
	MOVE.W a_principal,D1
	MOVE.W #0,D0
	CMP.W D0,D1
	BNE e1
* --> | GOTO | null | null | e2 |
	JMP e2
* --> | SKIP | null | null | e1 |
e1:
* --> | PARAM_S | null | null | a |
	MOVE.W a_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
* --> | RESTA | a | 1 | t2 |
	MOVE.W a_principal,D1
	MOVE.W #1,D0
	SUB.W D0,D1
	MOVE.W D1,t2_principal
* --> | ASSIGNA | t2 | null | a |
	MOVE.W t2_principal,a_principal
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
* --> | ASSIGNA | 0 | null | t4 |
	MOVE.W #0,t4_principal
* --> | ASSIGNA | t4 | null | i |
	MOVE.W t4_principal,i_principal
* --> | SKIP | null | null | e3 |
e3:
* --> | IFDIFERENT | i | 3 | e4 |
	MOVE.W i_principal,D1
	MOVE.W #3,D0
	CMP.W D0,D1
	BNE e4
* --> | GOTO | null | null | e5 |
	JMP e5
* --> | SKIP | null | null | e4 |
e4:
* --> | PARAM_S | null | null | i |
	MOVE.W i_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
* --> | SUMA | i | 1 | t6 |
	MOVE.W i_principal,D0
	MOVE.W #1,D1
	ADD.W D0,D1
	MOVE.W D1,t6_principal
* --> | ASSIGNA | t6 | null | i |
	MOVE.W t6_principal,i_principal
* --> | GOTO | null | null | e3 |
	JMP e3
* --> | SKIP | null | null | e5 |
e5:
	SIMHALT
 
t0_principal: DS.W 1
a_principal: DS.W 1
t2_principal: DS.W 1
t4_principal: DS.W 1
i_principal: DS.W 1
t6_principal: DS.W 1
 
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
