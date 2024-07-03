package nsOP2.zwierzeta;

import nsOP2.Zwierze;
import nsOP2.Organizm;
import nsOP2.Swiat;
import nsOP2.Punkt;
import nsOP2.Umiejetnosc;

import java.awt.*;
import java.util.Random;

public class Czlowiek extends Zwierze {
    private int rodzjaPlanszy;
    private static final int ZASIEG_RUCHU_CZLOWIEKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA = 1;
    private static final int SILA_CZLOWIEKA = 5;
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private Kierunek kierunekRuchu;
    private Umiejetnosc umiejetnosc;

    public Czlowiek(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.CZLOWIEK, swiat, pozycja, lifetime, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA);
        this.rodzjaPlanszy = getSwiat().getRodzjaPlanszy();
        this.setZasiegRuchu(ZASIEG_RUCHU_CZLOWIEKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA);
        kierunekRuchu = Kierunek.BRAK_KIERUNKU;
        setKolor(Color.BLUE);
        umiejetnosc = new Umiejetnosc();
    }
    @Override
    protected Punkt ZaplanujRuch() {
        int x = getPozycja().getX();
        int y = getPozycja().getY();
        LosujPoleDowolne(getPozycja());
        if (kierunekRuchu == Kierunek.BRAK_KIERUNKU ||
                CzyKierunekZablokowany(kierunekRuchu)) return getPozycja();
        else {
            if (kierunekRuchu == Kierunek.DOL) return new Punkt(x, y + 1);
            if (kierunekRuchu == Kierunek.GORA) return new Punkt(x, y - 1);
            if (kierunekRuchu == Kierunek.LEWO) return new Punkt(x - 1, y);
            if (kierunekRuchu == Kierunek.PRAWO) return new Punkt(x + 1, y);
            if( rodzjaPlanszy == 1 ) {
                if (kierunekRuchu == Kierunek.GORA_HEX) return new Punkt(x - 1, y - 1);
                if (kierunekRuchu == Kierunek.DOL_HEX) return new Punkt(x + 1, y + 1);
            }
            return new Punkt(x, y);
        }
    }

    @Override
    public void Akcja() {

        if( umiejetnosc.getCzyJestAktywna() )
        {
            Swiat.DodajLogi( "czas trwania Tarczy Alzura: " + umiejetnosc.getCzasTrwania() );
        }
        for (int i = 0; i < getZasiegRuchu(); i++)
        {
            Punkt przyszlaPozycja = ZaplanujRuch();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja)
                    && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this)
            {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) WykonajRuch(przyszlaPozycja);
        }
        kierunekRuchu = Kierunek.BRAK_KIERUNKU;
        umiejetnosc.SprawdzWarunki();
    }



    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        if (umiejetnosc.getCzyJestAktywna() && atakujacy.CzyJestZwierzeciem()) {
            if (this == ofiara) {
                Swiat.DodajLogi("uzycie Tarczy Alzura na :  " + atakujacy.OrganizmToSring());
                return true;
            } else {
                Swiat.DodajLogi("uzycie Tarczy Alzura na : " + ofiara.OrganizmToSring());
                return true;
            }
        } else return false;



    }

    @Override
    public void Kolizja(Organizm other) {
        if( umiejetnosc.getCzyJestAktywna() && other.CzyJestZwierzeciem() ) {
            Swiat.DodajLogi( "uzycie Tarczy Alzura na : " + other.OrganizmToSring() );


            Punkt przyszlaPozycja = LosujPoleDowolne(other.getPozycja());
            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja) && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this)
            {
                other.Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
            }
            else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) other.WykonajRuch(przyszlaPozycja);

        }
        else if (getSila() >= other.getSila()) {
            getSwiat().UsunOrganizm(other);
            WykonajRuch(other.getPozycja());
            Swiat.DodajLogi(OrganizmToSring() + " zabija " + other.OrganizmToSring());
        } else {
            if(umiejetnosc.getCzyJestAktywna()) {
                Punkt przesuniecie = other.LosujPoleNiezajete(other.getPozycja());
                if( przesuniecie == this.getPozycja() ) przesuniecie = other.LosujPoleNiezajete(other.getPozycja());
                other.setPozycja(przesuniecie);
            }
            else
            {
                getSwiat().UsunOrganizm(this);
                Swiat.DodajLogi(other.OrganizmToSring() + " zabija " + OrganizmToSring());
            }
        }

    }



    @Override
    public String TypOrganizmuToString() {
        return "Czlowiek";
    }

    public Umiejetnosc getUmiejetnosc() {
        return umiejetnosc;
    }

    public void setKierunekRuchu(Kierunek kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }
}
