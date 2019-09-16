%{
	#include <stdio.h>
	#include "y.tab.h"
%}

SUBJECT	"I"|"They"|"they"|"We"|"we"|"He"|"he"|"She"|she
VERB	"am"|"was"|"is"|"will"|"shall"|"shall be"|"will be"|"had been"|"has been"|"are"|"were"
CON	"and"|"or"
OBJECT	[a-zA-Z]+
EOS	"."

%%

{SUBJECT}	{return S;}
{VERB}		{return V;}
{CON}		{return C;}
{OBJECT}	{return O;}
{EOS}		{return E;}
[ \t] 		;
[\n] 		{return 0;}
. 		{}

%%

int yywrap()
{
	return 1;
}

