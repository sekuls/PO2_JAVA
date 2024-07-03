package nsOP2.rosliny;

import nsOP2.Roslina;
import nsOP2.Organizm;

import nsOP2.Swiat;
import nsOP2.Punkt;

import java.awt.*;

public class WilczeJagody extends Roslina {
    private static final int SILA_WILCZE_JAGODY = 99;
    private static final int INICJATYWA_WILCZE_JAGODY = 0;

    public WilczeJagody(Swiat swiat, Punkt pozycja, int lifetime) {
        super(TypOrganizmu.WILCZE_JAGODY, swiat, pozycja, lifetime, SILA_WILCZE_JAGODY, INICJATYWA_WILCZE_JAGODY);
        setKolor(new Color(76, 33, 145));
    }


    @Override
    public String TypOrganizmuToString() {
        return "Wilcze jagody";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        if (atakujacy.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(atakujacy);
            getSwiat().UsunOrganizm(ofiara);
            Swiat.DodajLogi(atakujacy.OrganizmToSring() + " zjadl jagodke i nie zyje");
        }
        else
        {
            getSwiat().UsunOrganizm(ofiara);
            getSwiat().UsunOrganizm(atakujacy);
            Swiat.DodajLogi(ofiara.OrganizmToSring() + " zjadl jagodke i nie zyje");
        }
        return true;
    }
}
