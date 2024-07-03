package nsOP2;

import nsOP2.rosliny.*;
import nsOP2.zwierzeta.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface implements ActionListener, KeyListener {
    static int HEX = 1;
    static int REC = 0;

    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame jFrame;

    private JMenuItem newGame;
    private PlanszaGraphics planszaGraphics = null;
    private LogGraphic logiGraphics = null;
    private JPanel mainPanel;
    private Swiat swiat;
    public GraphicInterface(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setLayout(null);

        JLabel infoLabel = new JLabel("Podaj wysokość i szerokość:");
        infoLabel.setBounds(10, 10, 200, 20); // Ustaw położenie i rozmiar

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(20, 20));
        textField.setBounds(10, 40, 100, 20);

        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(20, 20));
        textField2.setBounds(10, 70, 100, 20);

        JButton hex = new JButton("HEX");
        hex.setBounds(10, 100, 100, 20);

        JButton rec = new JButton(" REC");
        rec.setBounds(10, 130, 100, 20);

        hex.addActionListener(e -> {
            String inputText1 = textField.getText();
            String inputText2 = textField2.getText();

            try {
                int sizeX = Integer.parseInt(inputText1);
                int sizeY = Integer.parseInt(inputText2);
                swiat = new Swiat(sizeX, sizeY, this,HEX);
                swiat.GenerujSwiat();
                if (planszaGraphics != null)
                    mainPanel.remove(planszaGraphics);
                if (logiGraphics != null)
                    mainPanel.remove(logiGraphics);


                mainPanel.removeAll();
                mainPanel.repaint();
                startGame(HEX);


            } catch (NumberFormatException ex) {
                System.err.println("Niepoprawny format danych. Wprowadź liczbę całkowitą.");
            }
        });
        rec.addActionListener(e -> {
            String inputText1 = textField.getText();
            String inputText2 = textField2.getText();

            try {
                int sizeX = Integer.parseInt(inputText1);
                int sizeY = Integer.parseInt(inputText2);
                swiat = new Swiat(sizeX, sizeY, this,REC);
                swiat.GenerujSwiat();
                if (planszaGraphics != null)
                    mainPanel.remove(planszaGraphics);
                if (logiGraphics != null)
                    mainPanel.remove(logiGraphics);


                mainPanel.removeAll();
                mainPanel.repaint();
                startGame(REC);


            } catch (NumberFormatException ex) {
                System.err.println("Niepoprawny format danych. Wprowadź liczbę całkowitą.");
            }
        });

        JButton loadGame = new JButton("LoadGame");
        loadGame.setBounds(110, 100, 100, 20);

        //tu co jak zaladuje gre
        loadGame.addActionListener(e -> {
            swiat = Swiat.OdtworzSwiat();
            mainPanel.removeAll();
            mainPanel.repaint();
            swiat.setGraphicInterface(this);
            planszaGraphics = new PlanszaGraphics(swiat,REC);
            logiGraphics = new LogGraphic();

            startGame(swiat.getRodzjaPlanszy());
        });


        mainPanel.add(textField);
        mainPanel.add(textField2);
        mainPanel.add(hex);
        mainPanel.add(rec);
        mainPanel.add(loadGame);
        mainPanel.add(infoLabel);

        jFrame = new JFrame(title);
        jFrame.setBounds(10, 10, 1400, 800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
        jFrame.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null && swiat.isPauza()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
            }else if (keyCode == KeyEvent.VK_D) {
                logiGraphics.odswiezLogi();
                swiat.ZapiszSwiat();
                Swiat.DodajLogi("zapisano gre!");
                logiGraphics.odswiezLogi();
            }
            else if (swiat.getCzyCzlowiekZyje()) {
                if (keyCode == KeyEvent.VK_UP) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.LEWO);
                }else if (keyCode == KeyEvent.VK_RIGHT) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.PRAWO);
                } else if (keyCode == KeyEvent.VK_T) {
                    //GORA
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA_HEX);
                } else if (keyCode == KeyEvent.VK_R) {
                    //LEWO GORA
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.LEWO);
                }else if (keyCode == KeyEvent.VK_Y) {
                    //PRAWA GORA
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA);
                }else if (keyCode == KeyEvent.VK_F) {
                    //LEWO DOL
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL);
                } else if (keyCode == KeyEvent.VK_G) {
                    //DOL
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL_HEX);
                }else if (keyCode == KeyEvent.VK_H) {
                    //PRAWO DOL
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.PRAWO);
                }else if (keyCode == KeyEvent.VK_S) {
                    Umiejetnosc tmpUmiejetnosc = swiat.getCzlowiek().getUmiejetnosc();
                    if (tmpUmiejetnosc.getCzyMoznaAktywowac()) {
                        tmpUmiejetnosc.Aktywuj();
                        Swiat.DodajLogi("tarcza aktywowana na  " + tmpUmiejetnosc.getCzasTrwania() + " tur)");

                    }  else {
                        Swiat.DodajLogi("cooldown koniczy sie za  "
                                + tmpUmiejetnosc.getCooldown() + " tur");
                        logiGraphics.odswiezLogi();
                        return;
                    }
                } else {
                    Swiat.DodajLogi("nie klikaj tego!! ");
                    logiGraphics.odswiezLogi();
                    return;
                }
            } else {
                Swiat.DodajLogi("nie klikaj tego!!");
                logiGraphics.odswiezLogi();
                return;
            }
            Swiat.WyczyscLogi();
            swiat.setPauza(false);
            swiat.WykonajTure();
            odswiezSwiat();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class PlanszaGraphics extends JPanel {
        private final int sizeX;
        private final int sizeY;
        private PolePlanszy[][] polaPlanszy;
        private Swiat SWIAT;
        private JPanel[] kolumny;

        //DO ZWYKLEJ PLANSZY
        public void recPlansza()
        {
            polaPlanszy = new PolePlanszy[sizeY][sizeX];

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    polaPlanszy[i][j] = new PolePlanszy(j, i);
                    polaPlanszy[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PolePlanszy) {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if (tmpPole.isEmpty == true) {
                                    ListaOrganizmow listaOrganizmow = new ListaOrganizmow
                                            (tmpPole.getX() + jFrame.getX(),
                                                    tmpPole.getY() + jFrame.getY(),
                                                    new Punkt(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(polaPlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }



        //DO HEXA
        private void dodajKolumny( int szerokoscPaneluPx, int wysokoscPaneluPx) {
            kolumny = new JPanel[sizeX * 2 ];

            int szerokoscKolumnyPx = szerokoscPaneluPx / (sizeX * 2 - 1);
            //int wysokoscPx = wysokoscPaneluPx / sizeY;

            dodajPolaGry(kolumny, szerokoscKolumnyPx,  wysokoscPaneluPx);
            for (JPanel kolumna : kolumny) {
                if (kolumna != null) {
                    this.add(kolumna);
                }
            }
        }

        public void dodajPolaGry(JPanel[] kolumny, int szerokoscKolumnyPx, int wysokoscPx) {
            int srodkowaKolumna = kolumny.length / 2;
            int offsetWDol = (wysokoscPx - (sizeY + 1) * szerokoscKolumnyPx) / 2;

            for (int i = srodkowaKolumna - sizeX + 1; i < srodkowaKolumna + sizeX; i++) {
                kolumny[i] = new JPanel();
                int wysokoscH = szerokoscKolumnyPx * (sizeY - Math.abs(srodkowaKolumna - i));
                int pozycjaY = offsetWDol + Math.abs(srodkowaKolumna - i) * szerokoscKolumnyPx / 2;
                kolumny[i].setBounds((i-1) * szerokoscKolumnyPx, pozycjaY, szerokoscKolumnyPx, wysokoscH);
                kolumny[i].setLayout(new GridLayout(0, 1, 0, 0));
            }

            polaPlanszy = new PolePlanszy[sizeY][sizeX];
            for (int w = 0; w < sizeY; w++) {
                for (int s = 0; s < sizeX; s++) {
                    polaPlanszy[w][s] = new PolePlanszy(w, s);
                    kolumny[srodkowaKolumna + s - w].add(polaPlanszy[w][s]);
                    polaPlanszy[w][s].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PolePlanszy) {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if (tmpPole.isEmpty == true) {
                                    ListaOrganizmow listaOrganizmow = new ListaOrganizmow
                                            (tmpPole.getX() + jFrame.getX(), tmpPole.getY() + jFrame.getY(),
                                                    new Punkt(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }
        }
        public PlanszaGraphics(Swiat swiat, int rodzajPlanszy) {
            super(null);
            setBounds(mainPanel.getX(), mainPanel.getY(),
                    mainPanel.getWidth() * 5 / 6, mainPanel.getHeight());
            SWIAT = swiat;
            this.sizeX = swiat.getSizeX();
            this.sizeY = swiat.getSizeY();
            if (rodzajPlanszy == HEX)
                dodajKolumny(mainPanel.getWidth() * 5 / 6, mainPanel.getHeight());
            else {
                recPlansza();
            }
        }

        private class PolePlanszy extends JButton {
            private boolean isEmpty;
            private Color kolor;
            private final int pozX;
            private final int pozY;

            public PolePlanszy(int X, int Y) {
                super();
                kolor = Color.GRAY;
                setBackground(kolor);
                isEmpty = true;
                pozX = X;
                pozY = Y;

            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }


            public Color getKolor() {
                return kolor;
            }

            public void setKolor(Color kolor) {
                this.kolor = kolor;
                setBackground(kolor);
            }

            public int getPozX() {
                return pozX;
            }

            public int getPozY() {
                return pozY;
            }
        }

        public void odswiezPlansze() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                    if (tmpOrganizm != null) {
                        polaPlanszy[i][j].setEmpty(false);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(tmpOrganizm.getKolor());
                    } else {
                        polaPlanszy[i][j].setEmpty(true);
                        polaPlanszy[i][j].setEnabled(true);
                        polaPlanszy[i][j].setKolor(Color.GRAY);
                    }
                }
            }
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public PolePlanszy[][] getPolaPlanszy() {
            return polaPlanszy;
        }
    }

    private class LogGraphic extends JPanel {
        private String tekst;
        private JTextArea textArea;

        public LogGraphic() {
            super();
            setBounds(planszaGraphics.getX() + planszaGraphics.getWidth()  ,
                    mainPanel.getY() ,
                    mainPanel.getWidth() - planszaGraphics.getWidth() ,
                    mainPanel.getHeight() );
            tekst = Swiat.getTekst();
            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void odswiezLogi() {
            tekst = Swiat.getTekst();
            textArea.setText(tekst);
        }
    }

    private class ListaOrganizmow extends JFrame {
        private String[] listaOrganizmow;
        private Organizm.TypOrganizmu[] typOrganizmuList;
        private JList jList;

        public ListaOrganizmow(int x, int y, Punkt punkt) {
            super("Lista organizmow");
            setBounds(x, y, 250, 300);
            listaOrganizmow = new String[]{"Barszcz Sosnowskiego", "Guarana", "Mlecz", "Trawa",
                    "Wilcze jagody", "Antylopa", "Lis", "Owca", "Wilk", "Zolw"};
            typOrganizmuList = new Organizm.TypOrganizmu[]{Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO,
                    Organizm.TypOrganizmu.GUARANA, Organizm.TypOrganizmu.MLECZ, Organizm.TypOrganizmu.TRAWA,
                    Organizm.TypOrganizmu.WILCZE_JAGODY, Organizm.TypOrganizmu.ANTYLOPA,
                    Organizm.TypOrganizmu.LIS,
                    Organizm.TypOrganizmu.OWCA, Organizm.TypOrganizmu.WILK,
                    Organizm.TypOrganizmu.ZOLW
            };

            jList = new JList(listaOrganizmow);
            jList.setVisibleRowCount(listaOrganizmow.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Organizm.TypOrganizmu tmpOrganizm = typOrganizmuList[jList.getSelectedIndex()];
                    if( tmpOrganizm == Organizm.TypOrganizmu.WILK ) {
                        swiat.DodajOrganizm(new Wilk(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.ANTYLOPA ) {
                        swiat.DodajOrganizm(new Antylopa(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.ZOLW ) {
                        swiat.DodajOrganizm(new Zolw(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO ) {
                        swiat.DodajOrganizm(new BarszczSosnowskiego(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.GUARANA ) {
                        swiat.DodajOrganizm(new Guarana(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.LIS ) {
                        swiat.DodajOrganizm(new Lis(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.MLECZ ) {
                        swiat.DodajOrganizm(new Mlecz(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.TRAWA ) {
                        swiat.DodajOrganizm(new Trawa(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.WILCZE_JAGODY ) {
                        swiat.DodajOrganizm(new WilczeJagody(swiat, punkt, swiat.getNumerTury()));
                    }
                    else  if( tmpOrganizm == Organizm.TypOrganizmu.OWCA ) {
                        swiat.DodajOrganizm(new Owca(swiat, punkt, swiat.getNumerTury()));
                    }

                    //System.out.println(typOrganizmuList[jList.getSelectedIndex()]);

                    Swiat.DodajLogi("nowy gracz: " + tmpOrganizm);
                    odswiezSwiat();
                    dispose();

                }
            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);

            setVisible(true);
        }
    }

    private void startGame(int rodzajPlanszy) {
        if( rodzajPlanszy == HEX)
        {
            planszaGraphics = new PlanszaGraphics(swiat,HEX);
            mainPanel.add(planszaGraphics);
        }
        else
        {
            planszaGraphics = new PlanszaGraphics(swiat,REC);
            mainPanel.add(planszaGraphics);
        }

        logiGraphics = new LogGraphic();
        mainPanel.add(logiGraphics);


        odswiezSwiat();
    }
    public void odswiezSwiat() {
        planszaGraphics.odswiezPlansze();
        logiGraphics.odswiezLogi();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public PlanszaGraphics getPlanszaGraphics() {
        return planszaGraphics;
    }

    public LogGraphic getLogiGraphics() {
        return logiGraphics;
    }
}