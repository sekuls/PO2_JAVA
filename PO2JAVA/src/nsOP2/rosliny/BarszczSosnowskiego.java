package nsOP2.rosliny;

import nsOP2.*;


import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina {
    private static final int SILA_BARSZCZ_SOSNOWSKIEGO = 10;
    private static final int INICJATYWA_BARSZCZ_SOSNOWSKIEGO = 0;

    public BarszczSosnowskiego(Swiat swiat, Punkt pozycja, int lifetime) {

        super(TypOrganizmu.BARSZCZ_SOSNOWSKIEGO, swiat, pozycja,
                lifetime, SILA_BARSZCZ_SOSNOWSKIEGO, INICJATYWA_BARSZCZ_SOSNOWSKIEGO);
        setKolor(Color.RED);

    }

    @Override
    public void Akcja() {
        int pozX = getPozycja().getX();
        int pozY = getPozycja().getY();
        LosujPoleDowolne(getPozycja());
        for (int i = 0; i < 4; i++) {
            Organizm tmpOrganizm = null;
            if (i == 0 && !CzyKierunekZablokowany(Kierunek.DOL))
                tmpOrganizm = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX, pozY + 1));
            else if (i == 1 && !CzyKierunekZablokowany(Kierunek.GORA))
                tmpOrganizm = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX, pozY - 1));
            else if (i == 2 && !CzyKierunekZablokowany(Kierunek.LEWO))
                tmpOrganizm = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX - 1, pozY));
            else if (i == 3 && !CzyKierunekZablokowany(Kierunek.PRAWO))
                tmpOrganizm = getSwiat().CoZnajdujeSieNaPolu(new Punkt(pozX + 1, pozY));

            if (tmpOrganizm != null && tmpOrganizm.CzyJestZwierzeciem()) {
                getSwiat().UsunOrganizm(tmpOrganizm);
                Swiat.DodajLogi(OrganizmToSring() + " zabija " + tmpOrganizm.OrganizmToSring());
            }
        }
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < 25) {
            Punkt tmp1Punkt = this.LosujPoleNiezajete(getPozycja());
            if (tmp1Punkt.equals(getPozycja())) return;
            else {
                Organizm tmpOrganizm = Swiat.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp1Punkt);
                Swiat.DodajLogi("uroslo " + tmpOrganizm.OrganizmToSring());
                getSwiat().DodajOrganizm(tmpOrganizm);
            }
        }
    }


    @Override
    public String TypOrganizmuToString() {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        if (atakujacy.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(this);
            getSwiat().UsunOrganizm(atakujacy);
            Swiat.DodajLogi(atakujacy.OrganizmToSring() + " zjadl barszczyk i nie zyje  ");
        }
        if (ofiara.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(ofiara);
            getSwiat().UsunOrganizm(this);
            Swiat.DodajLogi(ofiara.OrganizmToSring() + " zjadl barszczyk i nie zyje  ");
        }
        return true;
    }
}
