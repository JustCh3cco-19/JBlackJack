package main.model;

import java.util.Random;

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

    // Metodo per ottenere un nome casuale dall'enum
    public static String getNomeRandom() {
        NomiBot[] names = NomiBot.values();
        int randomIndex = new Random().nextInt(names.length);
        return names[randomIndex].name();
    }
}