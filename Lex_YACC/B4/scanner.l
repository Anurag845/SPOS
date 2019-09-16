%{
	#include <stdio.h>
	#include "y.tab.h"
%}
digit	[0-9]
int	{digit}+
float	{digit}+(.{digit}+)?((e|E){digit}+)?
String	\".*\"
char	'.'
%%
=	{return ASSIGN;}
int	{return INTDECL;}
float	{return FLOATDECL;}
char	{return CHARDECL;}
String	{return STRINGDECL;}
{int}	{return INT;}
{float}	{return FLOAT;}
{char}	{return CHAR;}
{String}	{return STRING;}
;	{return SEMICOLON;}
,	{return COMMA;}
[a-zA-Z][a-zA-Z0-9]* {return ID;}
\n	{return 0;}
.	{}
%%
int yywrap(void)
{
	return 1;
}
