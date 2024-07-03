package nsOP2.zwierzeta;

import nsOP2.Zwierze;
import nsOP2.Swiat;
import nsOP2.Punkt;

import java.awt.*;

public class Owca extends Zwierze {
    private static final int ZASIEG_RUCHU_OWCY = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_OWCA = 1;
    private static final int SILA_OWCY = 4;
    private static final int INICJATYWA_OWCY = 4;

    public Owca(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.OWCA, swiat, pozycja, lifetime, SILA_OWCY, INICJATYWA_OWCY);
        this.setZasiegRuchu(ZASIEG_RUCHU_OWCY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_OWCA);
        setKolor(Color.lightGray);
    }

    @Override
    public String TypOrganizmuToString() {
        return "Owca";
    }
}
