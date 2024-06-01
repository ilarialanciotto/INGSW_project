package Interpreter;

import java.io.Reader;
import java.util.LinkedList;
import Exception.MyException;

public class CommandParser {
	
	private LexicalAnalyzer lexer;
	private Symbols symbol;
	private Cmd root;
	
	public CommandParser(Reader in) {
		lexer = new LexicalAnalyzer(in);
		root = command();
		waiting(Symbols.EOF);
	}
	
	public String toString() { return lexer.toString(); }
	
	//<cmd>::=<create>|<remove>|<move>|<scale>|<list>|<group>|<ungroup>|<area>|<perimeter>
	private Cmd command() {
        symbol = lexer.nextSymbol();
        if (symbol == Symbols.NEW) return create();
        else if (symbol == Symbols.DEL) return remove();
        else if(symbol == Symbols.MV) return move();
        else if(symbol == Symbols.MVOFF)  return moveoff();
        else if(symbol == Symbols.SCALE) return scale();
        else if(symbol==Symbols.LS) return list();
        else if(symbol==Symbols.GRP)  return group();
        else if(symbol==Symbols.UNGRP) return ungroup();
        else if(symbol==Symbols.AREA)  return area();
        else if(symbol==Symbols.PERIMETER) return perimeter();
        else  throw new MyException("Unexpected symbol: " + symbol);  
    }

	//<create>::= new <typeconstr> <pos>
    private Create create() {
        waiting(Symbols.NEW);
        TypeConstruct typecnstruct=typeconstruct();
        Pos pos = pos();
        return new Create(typecnstruct, pos);
    }
     
  //<typeconstr>::= circle (<posfloat>) | rectangle <pos> | img (<path>)
  	private TypeConstruct typeconstruct() {
  		if(symbol==Symbols.CIRCLE) {
  			waiting(Symbols.CIRCLE);
  			waiting(Symbols.OPEN_PARENTESIS);
  			float posfloat=posfloat();
  			waiting(Symbols.CLOSE_PARENTESIS);
  			return new TypeConstruct("circle",posfloat,null);
  		}else if(symbol==Symbols.RECTANGLE) {
  			waiting(Symbols.RECTANGLE);
  			Pos pos=pos();
  			return new TypeConstruct("rectangle",pos);
  		}else if(symbol==Symbols.IMG) {
  			waiting(Symbols.IMG);
  			waiting(Symbols.OPEN_PARENTESIS);
  			String path=path();
  			symbol=lexer.nextSymbol();
  			waiting(Symbols.CLOSE_PARENTESIS);
  			return new TypeConstruct("img",0.0f,path);
  		}
  		else throw new MyException("Unexpected type construct");
  	}
  	
  //<path>:= un percorso valido di file
  	private String path() {
  		if(symbol==Symbols.PATH) {
  			return lexer.getString(); 
  		} 
  		else throw new MyException("Expected path, found " + symbol);
  	}
  	
  //<posfloat>:= numero floating point non negativo
    private float posfloat() {
        if (symbol == Symbols.NUMBER) {
        	float number= Float.parseFloat(lexer.getString());
        	symbol=lexer.nextSymbol();
        	return number;  
        }
        else throw new MyException("Expected posfloat, found: " + symbol);
    }

    //<pos>::=( <posfloat> , <posfloat> )
    private Pos pos() {
		waiting(Symbols.OPEN_PARENTESIS);
		float posfloat1=posfloat();
		waiting(Symbols.COMMA);
		float posfloat2=posfloat();
		waiting(Symbols.CLOSE_PARENTESIS);
		return new Pos(posfloat1,posfloat2);
	}
    
    //<ungroup>::= ungrp <objID>
    private Ungroup ungroup() {
    	waiting(Symbols.UNGRP);
    	int objID=objID();
    	return new Ungroup(objID);
    }

    //<perimeter>::= perimeter <objID>| perimeter <type> | perimeter all
    private Perimeter perimeter() {
    	waiting(Symbols.PERIMETER);
		if(symbol==Symbols.NUMBER) {
			int objID=objID();
			return new Perimeter(objID);
		}else if(symbol==Symbols.CIRCLE || symbol==Symbols.RECTANGLE || symbol==Symbols.IMG) {
			String name= lexer.getString();
			symbol= lexer.nextSymbol();
			return new Perimeter(name); 
		}
		else if (symbol==Symbols.ALL) {
			String name= lexer.getString();
			symbol=lexer.nextSymbol();
			return new Perimeter(name);
		}
		else throw new MyException("Unexpected perimeter construct");
    }
    
	//<listID>::= <objID> { , <objID> }
	private LinkedList<Integer> listID() {
		int objID=objID();
		LinkedList <Integer>listID=new LinkedList<>();
		listID.add(objID);
		while(symbol==Symbols.COMMA) {
			symbol=lexer.nextSymbol();
			int number=objID();
			listID.add(number);
		}
		return listID;
	}
	
	//<area>::= area <objID>| area <type> | area all
	private Area area() {
		waiting(Symbols.AREA);
		if(symbol==Symbols.NUMBER) {
			int objID=objID();
			return new Area(objID);
		}else if(symbol==Symbols.CIRCLE || symbol==Symbols.RECTANGLE || symbol==Symbols.IMG) {
			String name=lexer.getString();
			symbol=lexer.nextSymbol();
			return new Area(name); 
		}
		else if (symbol==Symbols.ALL) {
			String name=lexer.getString();
			symbol=lexer.nextSymbol();
			return new Area(name);
		}
		else throw new MyException("Unexpected area construct");
	}
	
	//<remove>::= del <objID>
    private Remove remove() {
        waiting(Symbols.DEL);
        int idObj = objID();
        return new Remove(idObj);
    }
    
    //<group>::= grp <listID>
    private Group group() {
    	waiting(symbol.GRP);
    	LinkedList<Integer> listID=listID();
    	return new Group(listID);
    }
    
    //<move>::= mv <objID> <pos> | mvoff <objID> <pos> 
    private Move move() {
    	waiting(Symbols.MV); 
    	int objID=objID();
    	Pos pos=pos();
    	return new Move(objID,pos);
    }
    
    private MoveOff moveoff() {
    	waiting(Symbols.MVOFF); 
    	int objID=objID();
    	Pos pos=pos();
    	return new MoveOff(objID,pos);
    }
    
    //<scale>::= scale <objID> <posfloat>
    private Scale scale() {
    	waiting(Symbols.SCALE);
    	int objID=objID();
    	float posfloat=posfloat();
    	return new Scale(objID,posfloat);
    }
    
    //<list>::= ls <objID>| ls <type> | ls all | ls groups
    private List list() {
    	waiting(Symbols.LS);
    	if(symbol==Symbols.NUMBER) {
    		int number=Integer.parseInt(lexer.getString().substring(0,1));
    		symbol=lexer.nextSymbol();
    		return new List(number);
    	}
    	if(symbol==Symbols.ALL) {
    		symbol=lexer.nextSymbol();
    		return new List("all");
    	}
    	if(symbol==Symbols.GROUPS) {
    		symbol=lexer.nextSymbol();
    		return new List("groups");
    	}
    	if(symbol==Symbols.CIRCLE || symbol==Symbols.RECTANGLE || symbol==Symbols.IMG) {
    		String nameObj=lexer.getString();
    		symbol=lexer.nextSymbol();
    		return new List(nameObj); 
    	}
    	else throw new MyException("Unexpected list format");
    }

    //<objID>:= un identificatore  
    private int objID() {
        if (symbol == Symbols.NUMBER) { 
        	int number=Integer.parseInt(lexer.getString().substring(0,1));
        	symbol=lexer.nextSymbol();
            return number;  
         }
        else throw new MyException("Expected object id, found: " + symbol);
    }
	
    private void waiting(Symbols s) {
    	if (symbol != s) {
			throw new MyException("found " + symbol + " while waiting for " + s);
		}
		symbol = lexer.nextSymbol();
    }
    
    public Cmd getCommand() {
    	return root;
    }
    
}
