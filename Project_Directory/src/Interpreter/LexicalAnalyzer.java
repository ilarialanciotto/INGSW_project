package Interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class LexicalAnalyzer {
	
	private StreamTokenizer input;
	private Symbols symbol;
	private StringBuilder sb=new StringBuilder(100);

	public LexicalAnalyzer(Reader in) {

		input = new StreamTokenizer(in);
		input.resetSyntax();
		input.eolIsSignificant(false);
		input.wordChars('a', 'z');
		input.wordChars('A', 'Z');
		input.wordChars('0', '9');
		input.wordChars('.', '.');
		input.wordChars(':','\\');
		input.wordChars(':','/');
		input.wordChars('-', '_');
		input.whitespaceChars('\u0000', ' ');
		input.ordinaryChar('(');
		input.ordinaryChar(')');
		input.ordinaryChar(',');
	}

	public String getString() { return input.sval; }
	public String toString() {
		return sb.toString();
	}
	
	public Symbols nextSymbol() {
		try {
			switch (input.nextToken()) {
			case StreamTokenizer.TT_EOF:
				symbol = Symbols.EOF;
				break;
			case StreamTokenizer.TT_WORD:
				sb.append(input.sval + " ");
				if (input.sval.equalsIgnoreCase("new")) symbol=Symbols.NEW;
				else if (input.sval.equalsIgnoreCase("del")) symbol=Symbols.DEL;
                else if (input.sval.equalsIgnoreCase("mv")) symbol=Symbols.MV;
                else if (input.sval.equalsIgnoreCase("mvoff")) symbol=Symbols.MVOFF;
                else if (input.sval.equalsIgnoreCase("scale")) symbol=Symbols.SCALE;
                else if (input.sval.equalsIgnoreCase("ls")) symbol=Symbols.LS;
                else if(input.sval.equalsIgnoreCase("grp")) symbol=Symbols.GRP;
                else if(input.sval.equalsIgnoreCase("ungrp")) symbol=Symbols.UNGRP;
                else if(input.sval.equalsIgnoreCase("area")) symbol=Symbols.AREA;
                else if(input.sval.equalsIgnoreCase("perimeter")) symbol=Symbols.PERIMETER;
                else if(input.sval.equalsIgnoreCase("all"))  symbol=Symbols.ALL;
                else if(input.sval.equalsIgnoreCase("groups")) symbol=Symbols.GROUPS; 
				else if (input.sval.equalsIgnoreCase("circle")) symbol=Symbols.CIRCLE;
				else if(input.sval.equalsIgnoreCase("rectangle")) symbol=Symbols.RECTANGLE;
				else if(input.sval.equalsIgnoreCase("img")) symbol=Symbols.IMG;
				else if (input.sval.endsWith(".jpg") 
						|| input.sval.contains("/")
						|| input.sval.contains("_")
						|| input.sval.endsWith(".jpeg") 
						|| input.sval.endsWith(".png")
						|| input.sval.endsWith(".gif"))
					    symbol=Symbols.PATH;
				else if(input.sval.startsWith("id")
					    || input.sval.startsWith("g")){
					    symbol=Symbols.NUMBER;
				}
				else if(input.sval.contains("."))
					symbol=Symbols.NUMBER;
				break;
			case '(':
				sb.append("("  + " ");
				symbol = Symbols.OPEN_PARENTHESIS;
				break;
			case ')':
				sb.append(")" + " ");
				symbol = Symbols.CLOSE_PARENTHESIS;
				break;
			case ',':
				sb.append("," + " ");
				symbol=Symbols.COMMA;
				break;
			default:
				symbol=Symbols.INVALID_CHAR;
			}
		} catch (IOException e) {
			symbol=Symbols.EOF;
		}
		return symbol;
	}
}
