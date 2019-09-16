%{
	#include <stdio.h>
%}
%token S;
%token V;
%token C;
%token O;
%token E;
%start start;
%%
start: S V O E {printf("Simple");}
    | S V O E1  
    ;
E1: C S V O E {printf("Compound");}
    | C S V O E1 
    ;
%%

int main()
{
	yyparse();
	return 0;
}
int yyerror()
{
	printf("Invalid");
}
