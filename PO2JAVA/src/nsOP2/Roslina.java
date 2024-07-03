package nsOP2;

import java.util.Random;

public abstract class Roslina extends Organizm {

    protected Roslina(TypOrganizmu typOrganizmu, Swiat swiat,
                      Punkt pozycja, int lifetime, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, lifetime, sila, inicjatywa);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        System.out.println(tmpLosowanie);
        if (tmpLosowanie < 17) {
            Punkt tmp1Punkt = this.LosujPoleNiezajete(getPozycja());
            if (tmp1Punkt.equals(getPozycja())) return;
            else {
                Organizm tmpOrganizm = Swiat.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp1Punkt);
                Swiat.DodajLogi("Wzrasta nowa roslina " + tmpOrganizm.OrganizmToSring());
                getSwiat().DodajOrganizm(tmpOrganizm);
            }
        }
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return false;
    }


    @Override
    public void Kolizja(Organizm other) {
      /*  if( getSila() > other.getSila() )
        {
            Swiat.DodajKomentarz( getTypOrganizmu() + " Zatrula napastnika "  + other.getTypOrganizmu() );
            getSwiat().UsunOrganizm(other);
        }
        else if (getSila() < other.getSila())
        {
            Swiat.DodajKomentarz( getTypOrganizmu() + "Zostala zdeptana przez "  + other.getTypOrganizmu() );
            getSwiat().UsunOrganizm(this);
        }*/

    }

}
