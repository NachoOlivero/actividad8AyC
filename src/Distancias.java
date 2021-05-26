import java.util.Arrays;
import java.util.Comparator;

public final class Distancias {
	
	private static final int SMALL_INSTANCE = 2;
	
	private Distancias() {}
	
/********************************	 Distancia Naive   ************************************/
	
	public static double distanciaNaive(Punto[] arr) {
		double menorDistancia = Double.POSITIVE_INFINITY;
		double distanciaAB;
		
		for(int i=0;i<arr.length-1;i++) {
			for(int j=i+1;j<arr.length;j++) {
				distanciaAB = Punto.distancia(arr[i],arr[j]);
				if (distanciaAB < menorDistancia )
					menorDistancia = distanciaAB; 
			}
		}
		
		return menorDistancia;
	}
	
	/*********************************   Ordenado por X   **************************************/
	
	public static double distanciaOrdX(Punto[] arr) {
		Punto[] arrCopia = Arrays.copyOf(arr,arr.length);
		ordenarPorX(arrCopia);
		return distanciaOrdXAux(arrCopia);
	}
	
	private static double distanciaOrdXAux(Punto[] arr) {
		if(arr.length <= SMALL_INSTANCE)
			return distanciaNaive(arr);
		
		double mitad = ((double)arr[arr.length-1].getX() + (double)arr[0].getX()) / 2;
		int tamanioIzquierdo = tamanioIzquierdo(arr,mitad);
		int tamanioDerecho = arr.length - tamanioIzquierdo;
		
		Punto[] mitad1 = new Punto[tamanioIzquierdo];
		Punto[] mitad2 = new Punto[tamanioDerecho];
		partir(arr,mitad1,mitad2,mitad);
		
		double d1 = distanciaOrdXAux(mitad1);
		double d2 = distanciaOrdXAux(mitad2);
		double d = Math.min(d1, d2);
		
		ordenarPorY(arr);
		Punto [] franja = crearFranja(arr,mitad,d);
		double d3 = barrido(franja);
		
		return Math.min(d, d3);
	}
	
	/********************************   Ordenado por X e Y   *********************************/
	
	public static double distanciaOrdXY(Punto[] arr) {
		Punto[] arrX = Arrays.copyOf(arr,arr.length);
		Punto[] arrY = Arrays.copyOf(arr,arr.length);
		ordenarPorX(arrX);
		ordenarPorY(arrY);
		return distanciaOrdXYAux(arrX,arrY);
	}
	
	private static double distanciaOrdXYAux(Punto[] ordenadoPorX, Punto[] ordenadoPorY) {
		if(ordenadoPorX.length <= SMALL_INSTANCE)
			return distanciaNaive(ordenadoPorX);
		
		double mitad = ((double)ordenadoPorX[ordenadoPorX.length-1].getX() + (double)ordenadoPorX[0].getX()) / 2;
		int tamanioIzquierdo = tamanioIzquierdo(ordenadoPorX,mitad);
		int tamanioDerecho = ordenadoPorX.length - tamanioIzquierdo;
		
		/*if(tamanioIzquierdo == 0 || tamanioIzquierdo == ordenadoPorX.length) {
			for(int i=0;i<ordenadoPorX.length;i++)
				System.out.println(ordenadoPorX[i].getX());
			System.out.println();
			System.out.println("0: "+ordenadoPorX[0].getX());
			System.out.println("ultimo: "+ordenadoPorX[ordenadoPorX.length-1].getX());
			System.out.println("mitad: "+mitad);
			System.out.println();
		}*/
		
		Punto[] mitad1X = new Punto[tamanioIzquierdo];
		Punto[] mitad2X = new Punto[tamanioDerecho];
		partir(ordenadoPorX,mitad1X,mitad2X,mitad);
		
		Punto[] mitad1Y = new Punto[mitad1X.length];
		Punto[] mitad2Y = new Punto[mitad2X.length];
		partir(ordenadoPorY,mitad1Y,mitad2Y,mitad);
		
		double d1 = distanciaOrdXYAux(mitad1X, mitad1Y);
		double d2 = distanciaOrdXYAux(mitad2X, mitad2Y);
		double d = Math.min(d1, d2);
		
		Punto [] franja = crearFranja(ordenadoPorX,mitad,d);
		double d3 = barrido(franja);
		
		return Math.min(d, d3);
	}
	
	/*****************************************************************************************/
	
	static void ordenarPorX(Punto[] arr) {
		Arrays.sort(arr, new Comparator<Punto>() {
		    public int compare(Punto a, Punto b) {
		        return Integer.compare(a.getX(), b.getX());
		    }
		});
	}
	
	static void ordenarPorY(Punto[] arr) {
		Arrays.sort(arr, new Comparator<Punto>() {
		    public int compare(Punto a, Punto b) {
		        return Integer.compare(a.getY(), b.getY());
		    }
		});
	}
	
	private static int tamanioIzquierdo(Punto[] arr, double mitad ) {
		int tamanio = 0;
		boolean ladoIzquierdo = false;
		
		for(int i=0;i<arr.length;i++) {		
			int comp = Double.compare(arr[i].getX(),mitad);
			if(comp == 0) {
				if(ladoIzquierdo)
					comp = -1;
				else comp = 1;
				ladoIzquierdo = !ladoIzquierdo;
			}
			if(comp == -1)
				tamanio++;
		}
		
		return tamanio;
	}
	
	private static void partir(Punto[] arr, Punto[] m1, Punto[] m2, double mitad ) {
		int j1 = 0;
		int j2 = 0;
		boolean ladoIzquierdo = false;
		
		for(int i=0;i<arr.length;i++) {		
			int comp = Double.compare(arr[i].getX(),mitad);
			if(comp == 0) {
				if(ladoIzquierdo)
					comp = -1;
				else comp = 1;
				ladoIzquierdo = !ladoIzquierdo;
			}
			if(comp == -1)
				m1[j1++]=arr[i];
			else m2[j2++]=arr[i];
		}
	}
	
	private static Punto[] crearFranja(Punto[] arr, double medio, double distanciaMinima) {
		int cantPuntosFranja = 0;
		for(int i=0;i<arr.length;i++)
			if(Math.abs((arr[i].getX() - medio)) < distanciaMinima )
				cantPuntosFranja++;
		
		Punto[] franja = new Punto[cantPuntosFranja];
		int indice = 0;
		for(int i=0;i<arr.length;i++)
			if(Math.abs((arr[i].getX() - medio)) < distanciaMinima )
				franja[indice++] = arr[i];
		
		return franja;
	}
	
	private static double barrido(Punto[] franja) {
		double menorDistancia = Double.POSITIVE_INFINITY;
		double distanciaAB;
		
		for(int i=0;i<franja.length;i++) 
			for(int j=i+1;j<franja.length && j<i+8;j++) {
				distanciaAB = Punto.distancia(franja[i],franja[j]);
				if (distanciaAB < menorDistancia ) 
					menorDistancia = distanciaAB;
			}
				
		return menorDistancia;
	}

}
