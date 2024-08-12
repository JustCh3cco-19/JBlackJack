package main.model;

// Classe in cui gestiamo le carte da usare nella partita
public class Carta {
	// Definisco l'enum necessario per il punteggio assegnato alla carta
	public enum Punteggio
	{
		ASSO(11), DUE(2), TRE(3), QUATTRO(4), CINQUE(5), SEI(6), SETTE(7), 
		OTTO(8), NOVE(9), DIECI(10), JACK(10), REGINA(10), RE(10);

		private final int valore;

		Punteggio(int valore)
		{
			this.valore = valore;
		}

		public int getValore()
		{
			return valore;
		}
	}
	
	private final Punteggio punteggio; // valore della carta

	// Costruttore della classe Carta
	public Carta(Punteggio punteggio)
	{
		this.punteggio = punteggio;
	}

	// Getter per il punteggio
	public Punteggio getPunteggio()
	{
		return punteggio;
	}

	public String toString()
	{
		return punteggio.toString();
	}
}
