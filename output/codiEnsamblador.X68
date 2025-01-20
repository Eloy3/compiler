	org $1000
START:
* --> | SKIP | null | null | principal |
principal:
* --> | ASSIGNA | 0 | null | t0 |
	MOVE.W #0,t0_principal
* --> | MULTIPLICACIO | 0 | 1 | t1 |
	MOVE.W #0,D0
	EXT.L D0
	MOVE.W #1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t1_principal
	CLR.W D0
	CLR.W D1
* --> | SUMA | t0 | t1 | t0 |
	MOVE.W t0_principal,D0
	MOVE.W t1_principal,D1
	ADD.W D0,D1
	MOVE.W D1,t0_principal
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | 1 | null | t3 |
	MOVE.W #1,t3_principal
* --> | IND_ASS | t3 | t0 | tres |
	 LEA tres_principal,A0
	MOVE.W t0_principal, D1
	MULS #2, D1
	ADD.W D1,A0
	 MOVE.W t3_principal,(A0)
	CLR.W D1
* --> | ASSIGNA | 0 | null | t4 |
	MOVE.W #0,t4_principal
* --> | MULTIPLICACIO | 1 | 1 | t5 |
	MOVE.W #1,D0
	EXT.L D0
	MOVE.W #1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t5_principal
	CLR.W D0
	CLR.W D1
* --> | SUMA | t4 | t5 | t4 |
	MOVE.W t4_principal,D0
	MOVE.W t5_principal,D1
	ADD.W D0,D1
	MOVE.W D1,t4_principal
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | 2 | null | t7 |
	MOVE.W #2,t7_principal
* --> | IND_ASS | t7 | t4 | tres |
	 LEA tres_principal,A0
	MOVE.W t4_principal, D1
	MULS #2, D1
	ADD.W D1,A0
	 MOVE.W t7_principal,(A0)
	CLR.W D1
* --> | ASSIGNA | 0 | null | t8 |
	MOVE.W #0,t8_principal
* --> | MULTIPLICACIO | 2 | 1 | t9 |
	MOVE.W #2,D0
	EXT.L D0
	MOVE.W #1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t9_principal
	CLR.W D0
	CLR.W D1
* --> | SUMA | t8 | t9 | t8 |
	MOVE.W t8_principal,D0
	MOVE.W t9_principal,D1
	ADD.W D0,D1
	MOVE.W D1,t8_principal
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | 3 | null | t11 |
	MOVE.W #3,t11_principal
* --> | IND_ASS | t11 | t8 | tres |
	 LEA tres_principal,A0
	MOVE.W t8_principal, D1
	MULS #2, D1
	ADD.W D1,A0
	 MOVE.W t11_principal,(A0)
	CLR.W D1
* --> | ASSIGNA | 0 | null | t13 |
	MOVE.W #0,t13_principal
* --> | ASSIGNA | t13 | null | i |
	MOVE.W t13_principal,i_principal
* --> | SKIP | null | null | e0 |
e0:
* --> | IFDIFERENT | i | 3 | e1 |
	MOVE.W i_principal,D1
	MOVE.W #3,D0
	CMP.W D0,D1
	BNE e1
* --> | GOTO | null | null | e2 |
	JMP e2
* --> | SKIP | null | null | e1 |
e1:
* --> | ASSIGNA | 0 | null | t16 |
	MOVE.W #0,t16_principal
* --> | MULTIPLICACIO | i | 1 | t17 |
	MOVE.W i_principal,D0
	EXT.L D0
	MOVE.W #1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t17_principal
	CLR.W D0
	CLR.W D1
* --> | SUMA | t16 | t17 | t16 |
	MOVE.W t16_principal,D0
	MOVE.W t17_principal,D1
	ADD.W D0,D1
	MOVE.W D1,t16_principal
	CLR.W D0
	CLR.W D1
* --> | IND_VAL | tres | t16 | t15 |
	 LEA tres_principal,A0
	MOVE.W t16_principal, D1
	MULS #2, D1
	ADD.W D1,A0
	 MOVE.W (A0),t15_principal
	CLR.W D1
* --> | PARAM_S | null | null | t15 |
	MOVE.W t15_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | SUMA | i | 1 | t19 |
	MOVE.W i_principal,D0
	MOVE.W #1,D1
	ADD.W D0,D1
	MOVE.W D1,t19_principal
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | t19 | null | i |
	MOVE.W t19_principal,i_principal
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
	SIMHALT
 
t0_principal: DS.W 1
t1_principal: DS.W 1
t2_principal: DS.W 1
t3_principal: DS.W 1
t4_principal: DS.W 1
t5_principal: DS.W 1
t6_principal: DS.W 1
t7_principal: DS.W 1
t8_principal: DS.W 1
t9_principal: DS.W 1
t10_principal: DS.W 1
t11_principal: DS.W 1
tres_principal: DS.W 3
t13_principal: DS.W 1
i_principal: DS.W 1
t15_principal: DS.W 1
t16_principal: DS.W 1
t17_principal: DS.W 1
t18_principal: DS.W 1
t19_principal: DS.W 1
 
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
