package nsOP2.rosliny;

import nsOP2.*;

import java.util.Random;
import java.awt.*;

public class Mlecz extends Roslina {
    private static final int SILA_MLECZ = 0;
    private static final int INICJATYWA_MLECZ = 0;
    private static final int ILE_PROB = 3;

    public Mlecz(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.MLECZ, swiat, pozycja,
                lifetime, SILA_MLECZ, INICJATYWA_MLECZ);
        setKolor(Color.YELLOW);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        for (int i = 0; i < ILE_PROB; i++) {
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < 25) {
                Punkt tmp1Punkt = this.LosujPoleNiezajete(getPozycja());
                if (tmp1Punkt.equals(getPozycja())) return;
                else {
                    Organizm tmpOrganizm = Swiat.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp1Punkt);
                    Swiat.DodajLogi("Wzrasta nowa roslina " + tmpOrganizm.OrganizmToSring());
                    getSwiat().DodajOrganizm(tmpOrganizm);
                }
            }
        }
    }


    @Override
    public String TypOrganizmuToString() {
        return "Mlecz";
    }
}
