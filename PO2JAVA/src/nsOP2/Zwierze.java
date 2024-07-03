package nsOP2;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    private int zasiegRuchu;
    private double szansaWykonywaniaRuchu;

    public Zwierze(TypOrganizmu typOrganizmu, Swiat swiat,
                   Punkt pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setCzyRozmnazalSie(false);
    }

    @Override
    public void Akcja() {
        for (int i = 0; i < zasiegRuchu; i++) {
            Punkt przyszlaPozycja = ZaplanujRuch();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja)
                    && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) WykonajRuch(przyszlaPozycja);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
        if (getTypOrganizmu() == other.getTypOrganizmu()) {
            Random rand = new Random();
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < 25)
            {
                if (this.getCzyRozmnazalSie() || other.getCzyRozmnazalSie()) return;
                Punkt tmp1Punkt = this.LosujPoleNiezajete(getPozycja());
                if (tmp1Punkt.equals(getPozycja())) {
                    Punkt tmp2Punkt = other.LosujPoleNiezajete(other.getPozycja());
                    if (tmp2Punkt.equals(other.getPozycja())) return;
                    else {
                        Organizm tmpOrganizm = Swiat.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp2Punkt);
                        Swiat.DodajLogi("Urodzil sie " + tmpOrganizm.OrganizmToSring());
                        getSwiat().DodajOrganizm(tmpOrganizm);
                        setCzyRozmnazalSie(true);
                        other.setCzyRozmnazalSie(true);
                    }
                } else {
                    Organizm tmpOrganizm = Swiat.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp1Punkt);
                    Swiat.DodajLogi("Urodzil sie " + tmpOrganizm.OrganizmToSring());
                    getSwiat().DodajOrganizm(tmpOrganizm);
                    setCzyRozmnazalSie(true);
                    other.setCzyRozmnazalSie(true);
                }

            }
        } else {
            if (other.SpecjalneDzialaniePodczasAtaku(this, other)) return;
            if (SpecjalneDzialaniePodczasAtaku(this, other)) return;


            if (getSila() >= other.getSila()) {
                getSwiat().UsunOrganizm(other);
                WykonajRuch(other.getPozycja());
                Swiat.DodajLogi(OrganizmToSring() + " zabija " + other.OrganizmToSring());
            } else {
                getSwiat().UsunOrganizm(this);
                Swiat.DodajLogi(other.OrganizmToSring() + " zabija " + OrganizmToSring());
            }
        }
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return true;
    }

    protected Punkt ZaplanujRuch() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie >= (int) (szansaWykonywaniaRuchu * 100)) return getPozycja();
        else return LosujPoleDowolne(getPozycja());
    }


    public int getZasiegRuchu() {
        return zasiegRuchu;
    }

    public void setZasiegRuchu(int zasiegRuchu) {
        this.zasiegRuchu = zasiegRuchu;
    }

    public double getSzansaWykonywaniaRuchu() {
        return szansaWykonywaniaRuchu;
    }

    public void setSzansaWykonywaniaRuchu(double szansaWykonywaniaRuchu) {
        this.szansaWykonywaniaRuchu = szansaWykonywaniaRuchu;
    }
}
