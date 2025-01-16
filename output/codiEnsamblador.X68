	org $1000
START:
* --> | SKIP | null | null | principal |
principal:
* --> | PMB | null | null | principal |
* --> | ASSIGNA | 3 | null | t0 |
	MOVE.W #3,t0_principal
* --> | ASSIGNA | t0 | null | y |
	MOVE.W t0_principal,y_principal
* --> | ASSIGNA | 6 | null | t2 |
	MOVE.W #6,t2_principal
* --> | IND_ASS | t2 | 0 | array |
	 LEA array_principal,A0
	 MOVE.W t2_principal,0(A0)
* --> | ASSIGNA | 7 | null | t3 |
	MOVE.W #7,t3_principal
* --> | IND_ASS | t3 | 1 | array |
	 LEA array_principal,A0
	 MOVE.W t3_principal,2(A0)
* --> | ASSIGNA | 8 | null | t4 |
	MOVE.W #8,t4_principal
* --> | IND_ASS | t4 | 2 | array |
	 LEA array_principal,A0
	 MOVE.W t4_principal,4(A0)
* --> | ASSIGNA | 1 | null | t6 |
	MOVE.W #1,t6_principal
* --> | IND_ASS | t6 | 0 | array |
	 LEA array_principal,A0
	 MOVE.W t6_principal,0(A0)
* --> | ASSIGNA | 2 | null | t7 |
	MOVE.W #2,t7_principal
* --> | IND_ASS | t7 | 1 | array |
	 LEA array_principal,A0
	 MOVE.W t7_principal,2(A0)
* --> | ASSIGNA | 3 | null | t8 |
	MOVE.W #3,t8_principal
* --> | IND_ASS | t8 | 2 | array |
	 LEA array_principal,A0
	 MOVE.W t8_principal,4(A0)
* --> | ASSIGNA | 7 | null | t10 |
	MOVE.W #7,t10_principal
* --> | IND_ASS | t10 | 0 | array |
	 LEA array_principal,A0
	 MOVE.W t10_principal,0(A0)
* --> | IND_VAL | array | 0 | t11 |
	 LEA array_principal,A0
	 MOVE.W 0(A0),t11_principal
* --> | PARAM_S | null | null | t11 |
	MOVE.W t11_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
	SIMHALT
 
t0_principal: DS.W 1
y_principal: DS.W 1
t2_principal: DS.W 1
t3_principal: DS.W 1
t4_principal: DS.W 1
array_principal: DS.W 3
t6_principal: DS.W 1
t7_principal: DS.W 1
t8_principal: DS.W 1
t10_principal: DS.W 1
t11_principal: DS.W 1
 
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
