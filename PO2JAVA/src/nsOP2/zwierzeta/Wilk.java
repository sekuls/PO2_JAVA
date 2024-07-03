package nsOP2.zwierzeta;

import nsOP2.Zwierze;
import nsOP2.Swiat;
import nsOP2.Punkt;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int ZASIEG_RUCHU_WILKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_WILKA = 1;
    private static final int SILA_WILKA = 9;
    private static final int INICJATYWA_WILKA = 5;

    public Wilk(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.WILK, swiat, pozycja, turaUrodzenia, SILA_WILKA, INICJATYWA_WILKA);
        this.setZasiegRuchu(ZASIEG_RUCHU_WILKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_WILKA);
        setKolor(Color.WHITE);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Wilk";
    }
}
