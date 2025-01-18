	org $1000
START:
* --> | SKIP | null | null | principal |
principal:
* --> | ASSIGNA | cert | null | t0 |
	MOVE.B #-1,t0_principal
* --> | IND_ASS | t0 | 0 | xx |
	 LEA xx_principal,A0
	 MOVE.B t0_principal,0(A0)
* --> | ASSIGNA | cert | null | t1 |
	MOVE.B #-1,t1_principal
* --> | IND_ASS | t1 | 1 | xx |
	 LEA xx_principal,A0
	 MOVE.B t1_principal,2(A0)
* --> | ASSIGNA | fals | null | t2 |
	MOVE.B #0,t2_principal
* --> | IND_ASS | t2 | 2 | xx |
	 LEA xx_principal,A0
	 MOVE.B t2_principal,4(A0)
* --> | IND_VAL | xx | 1 | t4 |
	 LEA xx_principal,A0
	 MOVE.B 2(A0),t4_principal
* --> | IF | t4 | null | e1 |
	MOVE.B t4_principal,D0
	MOVE.B #-1,D1
	CMP.B D0,D1
	BEQ e1
* --> | GOTO | null | null | e2 |
	JMP e2
* --> | SKIP | null | null | e1 |
e1:
* --> | PARAM_C | 11 | null | null |
	MOVE.W #11,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
* --> | SKIP | null | null | e0 |
e0:
	SIMHALT
 
t0_principal: DS.B 1
t1_principal: DS.B 1
t2_principal: DS.B 1
xx_principal: DS.B 3
t4_principal: DS.B 1
 
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
	END START
