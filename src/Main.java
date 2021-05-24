import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
	
	private static final int MAX_VALUE = 1000000000;
	private static final int SMALL_INSTANCE = 50;

	public static void main(String[] args) {
		int cantPuntos = 50;
		Punto[] arr = new Punto[cantPuntos];
		Random random = new Random();
		
		for(int i=0;i<cantPuntos;i++)
			arr[i] = new Punto(random.nextInt(MAX_VALUE),random.nextInt(MAX_VALUE));
		
		//for(Punto punto:arr)
			//System.out.println("X: " + punto.getX() + " Y: " + punto.getY());
		
		double distanciaMinimaNaive = distanciaNaive(arr);
		System.out.println("Distancia minima algoritmo naive: " + distanciaMinimaNaive);
		
		double distanciaMinimaOrdX = distanciaOrdX(arr);
		System.out.println("Distancia minima ordenados por x: " + distanciaMinimaOrdX);
		
		double distanciaMinimaOrdXY = distanciaOrdXY(arr);
		System.out.println("Distancia minima ordenados por x e y: " + distanciaMinimaOrdXY);
	}
	
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
		ordenarPorX(arr);
		return distanciaOrdXAux(arr);
	}
	
	private static double distanciaOrdXAux(Punto[] arr) {
		if(arr.length < SMALL_INSTANCE)
			return distanciaNaive(arr);
		
		int medio2 = arr[arr.length-1].getX()/2;
		int medio = ((arr.length - 1)/2);
		int tamanio1 = medio +1;
		int tamanio2 = arr.length - tamanio1;
		
		Punto[] mitad1 = new Punto[tamanio1];
		Punto[] mitad2 = new Punto[tamanio2];
		partirX(arr,mitad1,mitad2);
		
		double d1 = distanciaOrdXAux(mitad1);
		double d2 = distanciaOrdXAux(mitad2);
		double d = Math.min(d1, d2);
		
		ordenarPorY(arr);
		Punto [] franja = crearFranja(arr,medio,d);
		double d3 = barrido(franja);
		
		return Math.min(d, d3);
	}
	
	/********************************   Ordenado por X e Y   *********************************/
	
	public static double distanciaOrdXY(Punto[] arrX) {
		Punto[] arrY = Arrays.copyOf(arrX,arrX.length);
		ordenarPorX(arrX);
		ordenarPorY(arrY);
		return distanciaOrdXYAux(arrX,arrY);
	}
	
	private static double distanciaOrdXYAux(Punto[] ordenadoPorX, Punto[] ordenadoPorY) {
		if(ordenadoPorX.length < SMALL_INSTANCE)
			return distanciaNaive(ordenadoPorX);
		
		int medio = ((ordenadoPorX.length - 1)/2);
		int tamanio1 = medio +1;
		int tamanio2 = ordenadoPorX.length - tamanio1;
		
		Punto[] mitad1X = new Punto[tamanio1];
		Punto[] mitad2X = new Punto[tamanio2];
		partirX(ordenadoPorX,mitad1X,mitad2X);
		
		Punto[] mitad1Y = new Punto[tamanio1];
		Punto[] mitad2Y = new Punto[tamanio2];
		partirY(ordenadoPorY,mitad1Y,mitad2Y,ordenadoPorX[medio]);
		
		double d1 = distanciaOrdXYAux(mitad1X, mitad1Y);
		double d2 = distanciaOrdXYAux(mitad2X, mitad2Y);
		double d = Math.min(d1, d2);
		
		Punto [] franja = crearFranja(ordenadoPorX,medio,d);
		double d3 = barrido(franja);
		
		return Math.min(d, d3);
	}
	
	/*****************************************************************************************/
	
	private static void ordenarPorX(Punto[] arr) {
		Arrays.sort(arr, new Comparator<Punto>() {
		    public int compare(Punto a, Punto b) {
		        return Integer.compare(a.getX(), b.getX());
		    }
		});
	}
	
	private static void ordenarPorY(Punto[] arr) {
		Arrays.sort(arr, new Comparator<Punto>() {
		    public int compare(Punto a, Punto b) {
		        return Integer.compare(a.getY(), b.getY());
		    }
		});
	}
	
	private static void partirX(Punto[] arr, Punto[] m1, Punto[] m2 ) {
		int i=0;
		for(int j1=0;j1<m1.length;j1++)
			m1[j1]=arr[i++];
		for(int j2=0;j2<m2.length;j2++)
			m2[j2]=arr[i++];
	}
	
	private static void partirY(Punto[] arr, Punto[] m1, Punto[] m2, Punto ref ) {
		int j1 = 0;
		int j2 = 0;
		for(int i=0;i<arr.length;i++)
			if(arr[i].mayor(ref))
				m2[j2++] = arr[i];
			else m1[j1++] = arr[i];
	}
	
	private static Punto[] crearFranja(Punto[] arr, int medio, double distanciaMinima) {
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
			for(int j=i+1;j<7 && j<franja.length;j++) {
				distanciaAB = Punto.distancia(franja[i],franja[j]);
				if (distanciaAB < menorDistancia )
					menorDistancia = distanciaAB;
			}
				
		return menorDistancia;
	}
	
	/*****************************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
