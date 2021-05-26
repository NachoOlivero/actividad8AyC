import java.util.Random;

public class Main {
	
	private static final int MAX_VALUE = 1000000000;

	public static void main(String[] args) {
		
		int check = 4;
		
		switch (check) {
			case 1: check1(); break;
			case 2: check2(); break;
			case 3: check3(); break;
			case 4: check4(); break;
		}
	}
	
	private static void check1() { // para ver si dan los mismo
		int cantPuntos = 2000;
		Punto[] arr = new Punto[cantPuntos];
		Random random = new Random();
		
		double distanciaMinimaNaive = 0;
		double distanciaMinimaOrdX = 0;
		double distanciaMinimaOrdXY = 0;
	
		while(true) { 
			do {
				for(int i=0;i<cantPuntos;i++)
					arr[i] = new Punto(random.nextInt(MAX_VALUE + 1),random.nextInt(MAX_VALUE + 1));
				
				distanciaMinimaNaive = Distancias.distanciaNaive(arr);
				distanciaMinimaOrdX = Distancias.distanciaOrdX(arr);
				distanciaMinimaOrdXY = Distancias.distanciaOrdXY(arr);
			}
			while(distanciaMinimaNaive == distanciaMinimaOrdX && distanciaMinimaNaive == distanciaMinimaOrdXY );
			System.out.println("Distancia minima algoritmo naive: " + distanciaMinimaNaive);
			System.out.println("Distancia minima ordenados por x: " + distanciaMinimaOrdX);
			System.out.println("Distancia minima ordenados por x e y: " + distanciaMinimaOrdXY + "\n");
		}
	}
	
	private static void check2() { // para ver cuanto tardan
		
		
		//int cantPuntos = (int) Math.pow(2, 13);
		int cantPuntos = (int) Math.pow(2, 20);
		
		Punto[] arr = new Punto[cantPuntos];
		Random random = new Random();
		
		for(int i=0;i<cantPuntos;i++)
			arr[i] = new Punto(random.nextInt(MAX_VALUE + 1),random.nextInt(MAX_VALUE + 1));
	
		long startTime = System.currentTimeMillis();
		double distanciaMinimaOrdXY = Distancias.distanciaOrdXY(arr);
		long endTime = System.currentTimeMillis();
		System.out.println("Distancia minima ordenados por x e y: " + distanciaMinimaOrdXY + " Tiempo: " + (endTime-startTime));
		
		startTime = System.currentTimeMillis();
		double distanciaMinimaOrdX = Distancias.distanciaOrdX(arr);
		endTime = System.currentTimeMillis();
		System.out.println("Distancia minima ordenados por x: " + distanciaMinimaOrdX + " Tiempo: " + (endTime-startTime));
		
		if(cantPuntos == (int) Math.pow(2, 13)) {
			startTime = System.currentTimeMillis();
			double distanciaMinimaNaive = Distancias.distanciaNaive(arr);
			endTime = System.currentTimeMillis();
			System.out.println("Distancia minima algoritmo naive: " + distanciaMinimaNaive + " Tiempo: " + (endTime-startTime));
		}
	}
	
	private static void check3() { // para ver si los ordenaba bien
		Random random = new Random();
		int cantPuntos = 100;
		Punto[] arr = new Punto[cantPuntos];
		
		for(int i=0;i<cantPuntos;i++)
			arr[i] = new Punto(random.nextInt(MAX_VALUE + 1),random.nextInt(MAX_VALUE + 1));
		
		Distancias.ordenarPorY(arr);
		
		for(int i=0;i<cantPuntos;i++)
			System.out.println("X: " + arr[i].getX() + " Y: " + arr[i].getY() + " I:  " + i);
	}
	
	private static void check4() { // para ver los tiempos promedio para las cantidades de puntos 
		
		double [][] r = Benchmarks.benchmarkNaive();
		//double [][] r = Benchmarks.benchmarkOrdX();
		//double [][] r = Benchmarks.benchmarkOrdXY();
		
		int cantPuntos = Benchmarks.N_NAIVE;
		//int cantPuntos = Benchmarks.N_ORD;
		
		for(int i=0;i<cantPuntos;i++)
			System.out.println((int)r[i][0]+" "+r[i][1]);
			
	}
}
