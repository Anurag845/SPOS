%{
	FILE* yyin;
	int charCount = 0;
	int wordCount = 0;
	int lineCount = 0;
%}
word [^ \t\n]+
eol \n
%%
{word} {wordCount++;charCount+=yyleng;}
{eol} {lineCount++;}
. {charCount++;}
%%
int yywrap()
{
	return 1;
}
int main(int argc,char* argv[])
{
	yyin = fopen(argv[1],"r");
	yylex();
	printf("Character count is %d\n",charCount);
	printf("Word count is %d\n",wordCount);
	printf("Line count is %d\n",lineCount);
	fclose(yyin);
}
