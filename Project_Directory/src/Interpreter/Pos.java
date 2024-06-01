package Interpreter;

public class Pos {
	
	private float x,y;

	public Pos(float posfloat1, float posfloat2) {
		if(posfloat1<0 || posfloat2<0) throw new IllegalArgumentException(" unexspected position ");
		x=posfloat1;
		y=posfloat2;
	}

	public float getX() { return x; }
	public float getY() { return y; }

}
