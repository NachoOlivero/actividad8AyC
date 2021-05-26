import java.util.Random;

public final class Benchmarks {
	
	private Benchmarks() {}
	
	private static final int MAX_VALUE = 1000000000;
	
	private static final int NAIVE = 0;
	private static final int ORD_X = 1;
	private static final int ORD_XY = 2;
	
	public static final int N_NAIVE = 13;
	public static final int N_ORD = 20;
	
	private static final double ITERACIONES = 10;
	
	public static double[][] benchmarkNaive() {
		return resultados(NAIVE,N_NAIVE);
	}
	
	public static double[][] benchmarkOrdX() {
		return resultados(ORD_X,N_ORD);
	}
	
	public static double[][] benchmarkOrdXY() {
		return resultados(ORD_XY,N_ORD);
	}
	
	private static double[][] resultados(int algoritmo, int n) {
		double[][] results = new double[n][2];
		for(int i=0;i<n;i++) {
			int cantPuntos = (int) Math.pow(2, i+1);
			Punto[] arr = crearArreglo(cantPuntos);
			
			results[i][0] = cantPuntos;
			results[i][1] = testTime(arr,algoritmo);
		}
		return results;
	}
	
	private static Punto[] crearArreglo(int cantPuntos) {
		Punto[] arr = new Punto[cantPuntos];
		Random random = new Random();
		
		for(int i=0;i<cantPuntos;i++)
			arr[i] = new Punto(random.nextInt(MAX_VALUE + 1),random.nextInt(MAX_VALUE + 1));
		
		return arr;
	}
	
	private static double testTime(Punto[] arr, int algoritmo) {
		long time = 0;
		long inicio;
		long fin;
		
		for(int i=0;i<ITERACIONES;i++) {
			inicio = System.currentTimeMillis();
			switch(algoritmo) {
				case NAIVE: Distancias.distanciaNaive(arr); break;
				case ORD_X: Distancias.distanciaOrdX(arr); break;
				case ORD_XY: Distancias.distanciaOrdXY(arr); break;
			}
			fin = System.currentTimeMillis();
			time += fin-inicio;
		}
		
		return time/ITERACIONES;
	}

}
