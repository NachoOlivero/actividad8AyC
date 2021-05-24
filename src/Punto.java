
public class Punto {
	
	protected int X;
	protected int Y;
	
	
	public Punto(int x, int y) {
		X = x;
		Y = y;
	}
	
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}
	
	public static double distancia(Punto A, Punto B) {
		double dX = Math.pow((A.getX() - B.getX()),2);
		double dY = Math.pow((A.getY() - B.getY()),2); 
		return Math.sqrt(dX + dY);
	}
	
	public boolean mayor(Punto p) {
		return X > p.getX();
	}

}
