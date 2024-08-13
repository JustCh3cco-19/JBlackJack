package main.model;

import java.util.Random;
public class Bots extends Player {

    public enum NomiBot {
        CharlesLeclerc,
        MaxVerstappen,
        LewisHamilton,
        ValtteriBottas,
        KimiRaikkonen,
        MichaelSchumacher,
        AyrtonSenna,
        DamonHill,
        SebastianVettel,
        AlainProst,
        FernandoAlonso,
        NigelMansell,
        JackieStewart,
        NikiLauda,
        JimClark,
        JuanManuelFangio,
        JuanPabloMontoya,
        PierreGasly,
        DanielRicciardo,
        YukiTsunoda,
        JamesHunt;

        // Ottenere un nome casuale
        public static String getNomeCasuale() {
            NomiBot[] nomi = NomiBot.values();
            int indiceCasuale = new Random().nextInt(nomi.length);
            return nomi[indiceCasuale].name();
        }
    }

    public Bots(String nome) {
        super(NomiBot.getNomeCasuale());
    }

    @Override
    public void addCarta(Carta carta) {
        if (carta.getPunteggio() == Carta.Punteggio.ASSO) {
            int valoreAsso = decidiValoreAsso();
            getMano().addCartaConValoreAsso(carta, valoreAsso);
        } else {
            getMano().addCarta(carta);
        }
    }
    
    // Secondo le regole il bot
    public boolean devePescare() {
        return calcolaPunteggio() < 17;
    }

    // Decidiamo se l'asso vale 1 o 11 basato sul punteggio attuale
    private int decidiValoreAsso() {
        int punteggioCorrente = calcolaPunteggio();

        // Se aggiungere 11 non fa superare 21, aggiungi 11 altrimenti aggiungi 1
        if (punteggioCorrente + 11 <= 21) {
            return 11;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Bot " + getNome() + " con mano: " + getMano().toString() + " - Punteggio: " + calcolaPunteggio();
    }
}
