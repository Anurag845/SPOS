%{
	FILE* yyin;
%}
DIGIT [0-9]
NUMBER {DIGIT}
REAL {NUMBER}[.]{NUMBER}
DATATYPE "int"|"float"|"double"|"char"|"String"
TEXT [A-Za-z]
IDENTIFIER {TEXT}({DIGIT}|{TEXT}|"_")*
KEYWORD "class"|"static"|"return"
ACCESS_SPECIFIER "public"|"private"|"protected"|""
ARITHMETIC_OP "+"|"*"|"/"|"-"
LOGICAL_OP "&&"|"||"
UNARY_OP "++"|"--"
RELATIONAL_OP ">"|"<"|">="|"<="
ITERATIVE "for"|"while"|"do"
CONDITIONAL "if"|"else"|"else if"
SC ";"
%%
[ \t\n]+ ;
{ARITHMETIC_OP} {printf("%s\t==>ARITHMETIC OPERATOR\n",yytext);}
{LOGICAL_OP} {printf("%s\t==>LOGICAL OPERATOR\n",yytext);}
{RELATIONAL_OP} {printf("%s\t==>RELATIONAL OPERATOR\n",yytext);}
{UNARY_OP} {printf("%s\t==>UNARY OPERATOR\n",yytext);}
{NUMBER} {printf("%s\t==>INTEGER CONSTANT\n",yytext);}
{REAL} {printf("%s\t==>FLOATING POINT CONSTANT\n",yytext);}
{ACCESS_SPECIFIER} {printf("%s\t==>ACCESS SPECIFIER\n",yytext);}
{ITERATIVE} {printf("%s\t==>ITERATIVE\n",yytext);}
{CONDITIONAL} {printf("%s\t==>CONDITIONAL\n",yytext);}
{KEYWORD} {printf("%s\t==>KEYWORD\n",yytext);}
{IDENTIFIER} {printf("%s\t==>IDENTIFIER\n",yytext);}
{SC} {printf("%s\t==>DELIMITER\n",yytext);}
"(" {printf("%s\t==>OPENING PARENTHESIS\n",yytext);}
")" {printf("%s\t==>CLOSING PARENTHESIS\n",yytext);}
"{" {printf("%s\t==>OPENING BLOCK\n",yytext);}
"}" {printf("%s\t==>CLOSING BLOCK\n",yytext);}
"=" {printf("%s\t==>ASSIGNMENT OPERATOR\n",yytext);}
. ;
%%
int yywrap()
{
	return 1;
}
int main(int argc,char* argv[])
{
	yyin = fopen(argv[1],"r");
	yylex();
	fclose(yyin);
}
