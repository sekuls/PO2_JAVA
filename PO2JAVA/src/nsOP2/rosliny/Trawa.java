package nsOP2.rosliny;



import nsOP2.Punkt;
import nsOP2.Roslina;
import nsOP2.Swiat;

import java.awt.*;

public class Trawa extends Roslina {
    private static final int SILA_TRAWY = 0;
    private static final int INICJATYWA_TRAWY = 0;

    public Trawa(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.TRAWA, swiat, pozycja, lifetime, SILA_TRAWY, INICJATYWA_TRAWY);
        setKolor(Color.GREEN);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Trawa";
    }
}
