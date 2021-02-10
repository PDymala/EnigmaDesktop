import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Promien2 {

	public static void main(String[] args) {

		System.out.println("Kalkulator pola ko�a metod� Monte Carlo i standardow�.");	
		Scanner input = new Scanner(System.in); //Uruchomienie metody Scanner umo�liwia wpisanie warto�ci przez u�ytkownika
		System.out.println("Podaj promie� ko�a kt�rego chcesz obliczy� pole np. 3 lub 4,2");
		double R = input.nextDouble(); //Deklaracja zmiennej typu Double wpisywanej przez u�ytkownika
		System.out.println("Podaj ilo�� los�w do metody MC np. 100000 lub wi�cej");
		long n = input.nextLong();    //Deklaracja zmiennej typu Long wpisywanej przez u�ytkownika
		input.close();	//wy��czenie pola Scanner
		
		/*Utworzenie list  z parametrami typu Double, parametrA, parametrB odpowiadaj�cymi parametrami losowych punkt�w [A,B] 
		i typu Boolean parametrWPolu, odpowiadaj�cemu czy losowy punkt mie�ci si� w polu czy nie. */
		List<Double> losowyPunktA = new ArrayList<Double>();
		List<Double> losowyPunktB = new ArrayList<Double>();
		List<Boolean> losowyPunktWPolu = new ArrayList<Boolean>();
		
		// P�tla losuj�ca i sprawdzaj�ce ich parametry w ilo�ci wpisanej przez u�ytkownika
		for (int k=0; k<n; k++){
		
			losowyPunktA.add( R*Math.random()); //losujemy parametr A punktu z zakresu 0 - R (wierzcho�ek kwadratu opisanego na kole)
			losowyPunktB.add( R*Math.random()); //losujemy parametr B punktu z zakresu 0 - R (wierzcho�ek kwadratu opisanego na kole)
		
		/* Poniewa� nasze ko�o ma �rodek w punkcie [0,0], nasz zakres do losowania punkt�w powinien by� -R do R. 
		Jednak niniejsza warto�� i tak jest podnoszona do kwadratu przed u�yciem do kolejnych funkcji, mo�na si� ograniczy� do 0 - R */
	
		
		double a = 	losowyPunktA.get(k); //pobieramy wylosowany parametr A i przypisujemy do nowo utworzonej zmiennej lokalnej a
		double b =  losowyPunktB.get(k); //pobieramy wylosowany parametr B i przypisujemy do nowo utworzonej zmiennej lokalnej B
		double w = (a*a) + (b*b);	// ograniczenie pola, tj. wzor na okr�g x^2 + y^2 < R^2  (je�eli punkt ma sie miescic w �rodku okr�gu )
		double r = R*R; // j/w
		if (w<r && w < (R/2)) {  //je�eli dla punktu k(A,B) w/w wz�r jest spe�niony, dodawany jest wiersz do listy parametrWPolu. Je�eli nie, to nie jest.
			losowyPunktWPolu.add(true);
			}
		}

		double poleKwadratuNaKole = (2*R)*(2*R); //pole powierzchni kwadratu opisanego na kole
		double poleKolaStandardowe = (Math.PI*R*R)/2;   // pole ko�� metod� standardow�
		System.out.println("Pole ko�a wed�ug MC wynosi "+poleKwadratuNaKole * (double)losowyPunktWPolu.size() / n);  //mno�ymy pole kwadratu na kole przez ilo�� punkt�w trafionych w okr�g podzielon� przez ilo�� punkt�w losowanych
		System.out.println("Pole ko�a metod� standardow� wynosi "+poleKolaStandardowe);
		

		

	}

}
