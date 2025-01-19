	org $1000
START:
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | suma |
suma:
* --> | PMB | null | null | suma |
	MOVE.W 4(A7),a_suma
	MOVE.W 6(A7),b_suma
* --> | SUMA | a | b | t2 |
	MOVE.W a_suma,D0
	MOVE.W b_suma,D1
	ADD.W D0,D1
	MOVE.W D1,t2_suma
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | t2 | null | z |
	MOVE.W t2_suma,z_suma
* --> | ASSIGNA | z | null | t4 |
	MOVE.W z_suma,t4_suma
* --> | RTN | t4 | null | null |
	MOVE.W t4_suma,D3
	RTS
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | resta |
resta:
* --> | PMB | null | null | resta |
	MOVE.W 4(A7),a_resta
	MOVE.W 6(A7),b_resta
* --> | RESTA | a | b | t7 |
	MOVE.W a_resta,D0
	MOVE.W b_resta,D1
	SUB.W D0,D1
	MOVE.W D1,t7_resta
	CLR.W D0
	CLR.W D1
* --> | ASSIGNA | t7 | null | b |
	MOVE.W t7_resta,b_suma
* --> | ASSIGNA | b | null | t9 |
	MOVE.W b_suma,t9_resta
* --> | RTN | t9 | null | null |
	MOVE.W t9_resta,D3
	RTS
* --> | SKIP | null | null | principal |
principal:
* --> | ASSIGNA | 5 | null | t10 |
	MOVE.W #5,t10_principal
* --> | ASSIGNA | t10 | null | x |
	MOVE.W t10_principal,x_principal
* --> | ASSIGNA | 3 | null | t12 |
	MOVE.W #3,t12_principal
* --> | ASSIGNA | t12 | null | y |
	MOVE.W t12_principal,y_principal
* --> | PARAM_S | null | null | x |
	MOVE.W x_principal,-(A7)
* --> | PARAM_S | null | null | y |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | suma |
	JSR suma
* --> | ASSIGNA | retInt | null | t14 |
	MOVE.W D3,t14_principal
* --> | ASSIGNA | t14 | null | z |
	MOVE.W t14_principal,z_suma
* --> | PARAM_S | null | null | x |
	MOVE.W x_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | y |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | z |
	MOVE.W z_suma,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | x |
	MOVE.W x_principal,-(A7)
* --> | PARAM_S | null | null | y |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | resta |
	JSR resta
* --> | ASSIGNA | retInt | null | t16 |
	MOVE.W D3,t16_principal
* --> | ASSIGNA | t16 | null | r |
	MOVE.W t16_principal,r_principal
* --> | PARAM_S | null | null | x |
	MOVE.W x_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | y |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | r |
	MOVE.W r_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
	SIMHALT
 
a_suma: DS.W 1
b_suma: DS.W 1
t2_suma: DS.W 1
z_suma: DS.W 1
t4_suma: DS.W 1
a_resta: DS.W 1
b_resta: DS.W 1
t7_resta: DS.W 1
t9_resta: DS.W 1
t10_principal: DS.W 1
x_principal: DS.W 1
t12_principal: DS.W 1
y_principal: DS.W 1
t14_principal: DS.W 1
z_principal: DS.W 1
t16_principal: DS.W 1
r_principal: DS.W 1
 
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
