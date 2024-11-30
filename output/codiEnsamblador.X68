	org $1000
START:
	JSR SCREENSIZE
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | suma |
suma:
* --> | PMB | null | null | suma |
	MOVE.W 4(A7),x_suma
	MOVE.W 6(A7),y_suma
* --> | SUMA | x | y | t2 |
	MOVE.W x_suma,D0
	MOVE.W y_suma,D1
	JSR ISUMA
	MOVE.W D1,t2_suma
* --> | ASSIGNA | t2 | null | z |
	MOVE.W t2_suma,z_suma
* --> | RTN | z | null | null |
	MOVE.W z_suma,D3
	RTS
* --> | SKIP | null | null | principal |
principal:
* --> | PMB | null | null | principal |
* --> | ASSIGNA | 7 | null | t4 |
	MOVE.W #7,t4_principal
* --> | ASSIGNA | t4 | null | a |
	MOVE.W t4_principal,a_principal
* --> | ASSIGNA | 3 | null | t6 |
	MOVE.W #3,t6_principal
* --> | ASSIGNA | t6 | null | b |
	MOVE.W t6_principal,b_principal
* --> | SUMA | a | b | t8 |
	MOVE.W a_principal,D0
	MOVE.W b_principal,D1
	JSR ISUMA
	MOVE.W D1,t8_principal
* --> | ASSIGNA | t8 | null | test |
	MOVE.W t8_principal,test_principal
* --> | PARAM_S | null | null | test |
	MOVE.W test_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | PARAM_S | null | null | a |
	MOVE.W a_principal,-(A7)
* --> | PARAM_S | null | null | b |
	MOVE.W b_principal,-(A7)
* --> | CALL | null | null | suma |
	JSR suma
* --> | ASSIGNA | retENT | null | t10 |
	MOVE.W D3,t10_principal
* --> | ASSIGNA | t10 | null | d |
	MOVE.W t10_principal,d_principal
* --> | PARAM_S | null | null | d |
	MOVE.W d_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
	SIMHALT
 
x_suma: DS.W 1
y_suma: DS.W 1
t2_suma: DS.W 1
z_suma: DS.W 1
t4_principal: DS.W 1
a_principal: DS.W 1
t6_principal: DS.W 1
b_principal: DS.W 1
t8_principal: DS.W 1
test_principal: DS.W 1
t10_principal: DS.W 1
d_principal: DS.W 1
 
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
