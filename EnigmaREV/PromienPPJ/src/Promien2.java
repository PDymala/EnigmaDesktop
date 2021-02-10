import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Promien2 {

	public static void main(String[] args) {

		System.out.println("Kalkulator pola ko³a metod¹ Monte Carlo i standardow¹.");	
		Scanner input = new Scanner(System.in); //Uruchomienie metody Scanner umo¿liwia wpisanie wartoœci przez u¿ytkownika
		System.out.println("Podaj promieñ ko³a którego chcesz obliczyæ pole np. 3 lub 4,2");
		double R = input.nextDouble(); //Deklaracja zmiennej typu Double wpisywanej przez u¿ytkownika
		System.out.println("Podaj iloœæ losów do metody MC np. 100000 lub wiêcej");
		long n = input.nextLong();    //Deklaracja zmiennej typu Long wpisywanej przez u¿ytkownika
		input.close();	//wy³¹czenie pola Scanner
		
		/*Utworzenie list  z parametrami typu Double, parametrA, parametrB odpowiadaj¹cymi parametrami losowych punktów [A,B] 
		i typu Boolean parametrWPolu, odpowiadaj¹cemu czy losowy punkt mieœci siê w polu czy nie. */
		List<Double> losowyPunktA = new ArrayList<Double>();
		List<Double> losowyPunktB = new ArrayList<Double>();
		List<Boolean> losowyPunktWPolu = new ArrayList<Boolean>();
		
		// Pêtla losuj¹ca i sprawdzaj¹ce ich parametry w iloœci wpisanej przez u¿ytkownika
		for (int k=0; k<n; k++){
		
			losowyPunktA.add( R*Math.random()); //losujemy parametr A punktu z zakresu 0 - R (wierzcho³ek kwadratu opisanego na kole)
			losowyPunktB.add( R*Math.random()); //losujemy parametr B punktu z zakresu 0 - R (wierzcho³ek kwadratu opisanego na kole)
		
		/* Poniewa¿ nasze ko³o ma œrodek w punkcie [0,0], nasz zakres do losowania punktów powinien byæ -R do R. 
		Jednak niniejsza wartoœæ i tak jest podnoszona do kwadratu przed u¿yciem do kolejnych funkcji, mo¿na siê ograniczyæ do 0 - R */
	
		
		double a = 	losowyPunktA.get(k); //pobieramy wylosowany parametr A i przypisujemy do nowo utworzonej zmiennej lokalnej a
		double b =  losowyPunktB.get(k); //pobieramy wylosowany parametr B i przypisujemy do nowo utworzonej zmiennej lokalnej B
		double w = (a*a) + (b*b);	// ograniczenie pola, tj. wzor na okr¹g x^2 + y^2 < R^2  (je¿eli punkt ma sie miescic w œrodku okrêgu )
		double r = R*R; // j/w
		if (w<r && w < (R/2)) {  //je¿eli dla punktu k(A,B) w/w wzór jest spe³niony, dodawany jest wiersz do listy parametrWPolu. Je¿eli nie, to nie jest.
			losowyPunktWPolu.add(true);
			}
		}

		double poleKwadratuNaKole = (2*R)*(2*R); //pole powierzchni kwadratu opisanego na kole
		double poleKolaStandardowe = (Math.PI*R*R)/2;   // pole ko³¹ metod¹ standardow¹
		System.out.println("Pole ko³a wed³ug MC wynosi "+poleKwadratuNaKole * (double)losowyPunktWPolu.size() / n);  //mno¿ymy pole kwadratu na kole przez iloœæ punktów trafionych w okr¹g podzielon¹ przez iloœæ punktów losowanych
		System.out.println("Pole ko³a metod¹ standardow¹ wynosi "+poleKolaStandardowe);
		

		

	}

}
