	org $1000
START:
	JSR SCREENSIZE
* --> | GOTO | null | null | principal |
	JMP principal
* --> | SKIP | null | null | principal |
principal:
* --> | PMB | null | null | principal |
* --> | PARAM_S | null | null | a |
	MOVE.W a_principal,-(A7)
* --> | CALL | null | null | getInt |
	SUBA.L #2,A7
	JSR GETINT
* --> | ASSIGNA | retInt | null | a |
	MOVE.W (A7)+,a_principal
* --> | ASSIGNA | 0 | null | t1 |
	MOVE.W #0,t1_principal
* --> | ASSIGNA | t1 | null | i |
	MOVE.W t1_principal,i_principal
* --> | SKIP | null | null | e0 |
e0:
* --> | IFDIFERENT | i | a | e1 |
	MOVE.W i_principal,D1
	MOVE.W a_principal,D0
	CMP.W D0,D1
	BNE e1
* --> | GOTO | null | null | e2 |
	JMP e2
* --> | SKIP | null | null | e1 |
e1:
* --> | SUMA | i | 1 | t3 |
	MOVE.W i_principal,D0
	MOVE.W #1,D1
	JSR ISUMA
	MOVE.W D1,t3_principal
* --> | ASSIGNA | t3 | null | i |
	MOVE.W t3_principal,i_principal
* --> | PARAM_S | null | null | i |
	MOVE.W i_principal,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
* --> | GOTO | null | null | e0 |
	JMP e0
* --> | SKIP | null | null | e2 |
e2:
* --> | GOTO | null | null | e3 |
	JMP e3
* --> | SKIP | null | null | suma |
suma:
* --> | PMB | null | null | suma |
	MOVE.W 6(A7),x_suma
	MOVE.W 8(A7),y_suma
* --> | PARAM_S | null | null | x |
	MOVE.W x_suma,-(A7)
* --> | PARAM_S | null | null | y |
	MOVE.W y_suma,-(A7)
* --> | SUMA | x | y | t6 |
	MOVE.W x_suma,D0
	MOVE.W y_suma,D1
	JSR ISUMA
	MOVE.W D1,t6_suma
* --> | ASSIGNA | t6 | null | z |
	MOVE.W t6_suma,z_suma
* --> | RTN | null | null | z |
	RTS
* --> | SKIP | null | null | e3 |
e3:
* --> | ASSIGNA | 7 | null | t8 |
	MOVE.W #7,t8_principal
* --> | ASSIGNA | t8 | null | y |
	MOVE.W t8_principal,y_suma
* --> | PARAM_C | 7 | null | null |
	MOVE.W #7,-(A7)
* --> | CALL | null | null | line |
	JSR ILINE
	ADDA.L #2,A7
	SIMHALT
 
a_principal: DS.W 1
t1_principal: DS.W 1
i_principal: DS.W 1
t3_principal: DS.W 1
x_suma: DS.W 1
y_suma: DS.W 1
t6_suma: DS.W 1
z_suma: DS.W 1
t8_principal: DS.W 1
 
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

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
