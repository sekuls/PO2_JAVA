package nsOP2.rosliny;

import nsOP2.Roslina;
import nsOP2.Organizm;

import nsOP2.Swiat;
import nsOP2.Punkt;

import java.awt.*;

public class Guarana extends Roslina {
    private static final int ZWIEKSZENIE_SILY = 3;
    private static final int SILA_GUARANY = 0;
    private static final int INICJATYWA_GUARANY = 0;

    public Guarana(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.GUARANA, swiat, pozycja,
                turaUrodzenia, SILA_GUARANY, INICJATYWA_GUARANY);
        setKolor(Color.CYAN);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Guarana";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        Punkt tmpPozycja = this.getPozycja();
        getSwiat().UsunOrganizm(this);
        atakujacy.WykonajRuch(tmpPozycja);
        Swiat.DodajLogi(atakujacy.OrganizmToSring() + " zjada " + this.OrganizmToSring()
                + " , sila + " + Integer.toString(ZWIEKSZENIE_SILY));
        atakujacy.setSila(atakujacy.getSila() + ZWIEKSZENIE_SILY);
        return true;
    }
}

