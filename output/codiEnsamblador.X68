	org $1000
START:
	JSR SCREENSIZE
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | suma |
suma:
* --> | PMB | null | null | suma |
	MOVE.W 4(A7),a_suma
	MOVE.W 6(A7),b_suma
* --> | SUMA | a_suma | b_suma | t2 |
	MOVE.W a_suma,D0
	MOVE.W b_suma,D1
	JSR ISUMA
	MOVE.W D1,t2
* --> | ASSIGNA | t2 | null | z_suma |
	MOVE.W t2,z_suma
* --> | ASSIGNA | z_suma | null | t4 |
	MOVE.W z_suma,t4
* --> | RTN | t4 | null | null |
	MOVE.W t4,D3
	RTS
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | resta |
resta:
* --> | PMB | null | null | resta |
	MOVE.W 4(A7),a_resta
	MOVE.W 6(A7),b_resta
* --> | RESTA | a_resta | b_resta | t7 |
	MOVE.W a_resta,D0
	MOVE.W b_resta,D1
	JSR IRESTA
	MOVE.W D1,t7
* --> | ASSIGNA | t7 | null | b_resta |
	MOVE.W t7,b_resta
* --> | ASSIGNA | b_resta | null | t9 |
	MOVE.W b_resta,t9
* --> | RTN | t9 | null | null |
	MOVE.W t9,D3
	RTS
* --> | SKIP | null | null | principal |
principal:
* --> | PMB | null | null | principal |
* --> | ASSIGNA | 5 | null | t10 |
	MOVE.W #5,t10
* --> | ASSIGNA | t10 | null | x_principal |
	MOVE.W t10,x_principal
* --> | ASSIGNA | 3 | null | t12 |
	MOVE.W #3,t12
* --> | ASSIGNA | t12 | null | y_principal |
	MOVE.W t12,y_principal
* --> | PARAM_S | null | null | x_principal |
	MOVE.W x_principal,-(A7)
* --> | PARAM_S | null | null | y_principal |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | suma |
	JSR suma
* --> | ASSIGNA | retInt | null | t14 |
	MOVE.W D3,t14
* --> | ASSIGNA | t14 | null | z_principal |
	MOVE.W t14,z_principal
* --> | PARAM_S | null | null | x_principal |
	MOVE.W x_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | y_principal |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | z_principal |
	MOVE.W z_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | x_principal |
	MOVE.W x_principal,-(A7)
* --> | PARAM_S | null | null | y_principal |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | resta |
	JSR resta
* --> | ASSIGNA | retInt | null | t16 |
	MOVE.W D3,t16
* --> | ASSIGNA | t16 | null | r_principal |
	MOVE.W t16,r_principal
* --> | PARAM_S | null | null | x_principal |
	MOVE.W x_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | y_principal |
	MOVE.W y_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | r_principal |
	MOVE.W r_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
	SIMHALT
 
a_suma: DS.W 1
b_suma: DS.W 1
t2: DS.W 1
z_suma: DS.W 1
t4: DS.W 1
a_resta: DS.W 1
b_resta: DS.W 1
t7: DS.W 1
t9: DS.W 1
t10: DS.W 1
x_principal: DS.W 1
t12: DS.W 1
y_principal: DS.W 1
t14: DS.W 1
z_principal: DS.W 1
t16: DS.W 1
r_principal: DS.W 1
 
strout: DS.B 100
	DC.W 0
 
SCREENSIZE:
	MOVE.L #1024*$10000+768,D1
	MOVE.B  #33,D0
	TRAP    #15
	RTS
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
