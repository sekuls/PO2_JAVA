package nsOP2.zwierzeta;

import nsOP2.Zwierze;
import nsOP2.Organizm;

import nsOP2.Swiat;
import nsOP2.Punkt;

import java.util.Random;
import java.awt.*;

public class Antylopa extends Zwierze {
    private static final int ZASIEG_RUCHU_ANTYLOPY = 2;
    private static final int SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY = 1;
    private static final int SILA_ANTYLOPY = 4;
    private static final int INICJATYWA_ANTYLOPY = 4;


    public Antylopa(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.ANTYLOPA, swiat, pozycja, lifetime, SILA_ANTYLOPY, INICJATYWA_ANTYLOPY);
        this.setZasiegRuchu(ZASIEG_RUCHU_ANTYLOPY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY);
        setKolor(Color.MAGENTA);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Antylopa";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < 50) {
            if (this == atakujacy) {
                Swiat.DodajLogi(OrganizmToSring() + " ucieka od " + ofiara.OrganizmToSring());
                Punkt tmpPozycja = LosujPoleNiezajete(ofiara.getPozycja());
                if (!tmpPozycja.equals(ofiara.getPozycja()))
                    WykonajRuch(tmpPozycja);
            } else if (this == ofiara) {
                Swiat.DodajLogi(OrganizmToSring() + " ucieka od " + atakujacy.OrganizmToSring());
                Punkt tmpPozycja = this.getPozycja();
                WykonajRuch(LosujPoleNiezajete(this.getPozycja()));
                if (getPozycja().equals(tmpPozycja)) {
                    getSwiat().UsunOrganizm(this);
                    Swiat.DodajLogi(atakujacy.OrganizmToSring() + " zabija " + OrganizmToSring());
                }
                atakujacy.WykonajRuch(tmpPozycja);
            }
            return true;
        } else return false;
    }
}
