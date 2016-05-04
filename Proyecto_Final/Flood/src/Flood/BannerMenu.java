package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Left & Right
 *
 */
public class BannerMenu extends JPanel implements MouseListener {

    private Image imaBackground; //Imagen de fondo

    //Bases Menu principal
    private Base basCatUno; //boton categoria uno
    private Base basCatDos; //boton categoria dos
    private Base basCatTres; // boton categoria tres
    private Base basCatCustom; // boton custom

    /**
     *
     */
    protected Base basPlay; //Boton de play
    private Base basHelp; //Boton de question

    //Bases Pantalla de Help
    private Base basInstrucciones; //boton
    private Base basCreditos; //boton de creditos
    private Base basRecords; //boton de records
    private Base basBackToMenu; //boton de back to menu
    private Base basBackToPlay; //boton de regreso al juego
    private Base basSound; //boton de sound y mute

    //Imagenes Menu principal
    Image imaMenuBackground; //Background menu
    private Image imaBackToMenu2; //imagen de animacion del boton de back to menu2
    private Image imaBackToMenu; //imagen de animacion del boton de back to menu2
    private Image imaPantallaInstrucciones; //imagen de animacion del boton de back to menu2
    private Image imaInstrucciones; //texto de instrucciones
    private Image imaCredits; //texto de los credits

    //Offsets
    private int iSecondaryMenuOffsetX; //Para menu secundario
    private int iSecondaryMenuOffsetY; //Para menu secundario

    private int iOffsetX; //Para menu principal
    private int iOffsetY; //Para menu Principal

    private int iPosBackPlayX;
    private int iPosBackPlayY;

    //Posicion del mouse
    int iMouseX;
    int iMouseY;
    int iMouseYOffSet;

    //banderas que controlan vistas

    /**
     *
     */
    protected boolean bPrincipal; //boton que lleva a menu principal

    /**
     *
     */
    protected boolean bSecundario;//indica si esta en el menu secundario
    private boolean bInstrucciones; //boton que lleva a instrucciones
    private boolean bCreditos; //boton que lleva a creditos
    private boolean bRecords; //boton que lleva a records
    private boolean bPlay; //boton que lleva a al juego

    //Booleanas para saber que categoria esta seleccionada
    private boolean bCatUno;
    private boolean bCatDos;
    private boolean bCatTres;
    private boolean bCatCustom;
    private boolean bCurrentCat; //booleana que contiene la categoria seleccionada
    private boolean bSound;//controla booleana del sonido en menu principal

    //booleans para el hover y animacion
    private boolean bHoverReturn;

    //Font a usar
    private Font fonFuenteMenu;

    //Instrucciones
    private String sObjetivo;

    //CUSTOM CATEGORY
    //Custom category click
    private String sCustomCat;

    //dile that is clicked by the user (that is choosen)
    String sCustomFile;

    //boolean bCustomCategoryClicked
    private boolean bCustomCategoryClicked;

    /**
     * Creates the flood instance.
     */
    private Flood tarGame;

    /**
     * Crates a new BannerMenu instance.
     *
     * @param floodGame
     * @throws java.awt.FontFormatException
     * @throws java.io.IOException
     */
    public BannerMenu(Flood floodGame) throws FontFormatException, IOException {

        this.tarGame = floodGame;

        initVars();
        creaBases();
        creaImagenes();
        addMouseListener(this);

    }

    /**
     *
     */
    public void creaImagenes() {

        imaMenuBackground = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/PantallaJuego.png"));

        imaBackToMenu = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/backMenu.png"));

        imaBackToMenu2 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/backMenu.png"));

        imaPantallaInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/PantallaInstrucciones.png"));

        imaInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/Instrucciones.png"));

        imaCredits = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/Credits.png"));
    }

    /**
     *
     * @param bPlay
     */
    public void setPlay(boolean bPlay) {

        this.bPlay = bPlay;

    }

    /**
     *
     * @param bSecundario
     */
    public void setSecundario(boolean bSecundario) {

        this.bSecundario = bSecundario;

    }

    /**
     *
     * @return
     */
    public boolean getSecundario() {

        return bSecundario;

    }

    /**
     *
     * @param bInstrucciones
     */
    public void setInstrucciones(boolean bInstrucciones) {

        this.bInstrucciones = bInstrucciones;

    }

    /**
     *
     * @return
     */
    public boolean getCustomCategoryClicked() {

        return bCustomCategoryClicked;

    }

    /**
     *
     * @param bCustomCategoryClicked
     */
    public void setCustomCategoryClicked(boolean bCustomCategoryClicked) {

        this.bCustomCategoryClicked = bCustomCategoryClicked;

    }

    /**
     *
     * @return
     */
    public boolean getPlay() {

        return bPlay;

    }

    /**
     *
     * @return
     */
    public boolean getInstrucciones() {

        return bInstrucciones;

    }

    /**
     *
     * @param sCustomCat
     */
    public void setCustomCat(String sCustomCat) {

        this.sCustomCat = sCustomCat;

    }

    /**
     *
     * @return
     */
    public String getCustomCat() {

        return sCustomCat;

    }

    /**
     *
     * @return
     */
    public boolean getCurrentCat() {

        return bCurrentCat;
    }

    /**
     *
     * @param bCurrentCat
     */
    public void setCurrentCat(boolean bCurrentCat) {
        this.bCurrentCat = bCurrentCat;
    }

    /**
     *
     */
    public void creaBases() {

        //MENU PRINCIPAL
        iOffsetY = 88; //Offset para categorias
        iOffsetX = 120;  //Offset para categorias

        //refactor
        int iPosicionX = (tarGame.iHeight / 2) - 132;
        int iPosicionY = 259;

        int iOffsetXBottomPanel; //offset para boton de play y de help
        int iOffsetYBottomPanel; //offset para boton de play y de help

        /**
         * Home
         */
        basCatUno = new Base(iPosicionX, iPosicionY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat1.png")));

        basCatDos = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat2.png")));

        basCatTres = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat3.png")));

        basCatCustom = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat4.png")));

        /**
         * Bottom Home
         */
        basHelp = new Base(25, 670, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/help.png")));

        basSound = new Base(basHelp.getX() + basHelp.getAncho() + 15, 670, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/Sound.png")));

        basPlay = new Base(414, 651, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/play.png")));

        /**
         * Menu secundario
         */
        iPosicionX = 84;
        iPosicionY = 241;

        //iPosicionY = (tarGame.iHeight / 2) + 134;
        //iPosicionX = 290;
        //MENU SECUNDARIO records, credits, instructions
        iSecondaryMenuOffsetX = 30;//
        iSecondaryMenuOffsetY = 84;

        basInstrucciones = new Base(iPosicionX, iPosicionY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/InstruccionesA.png")));

        basCreditos = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/CreditosA.png")));

        basRecords = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/PuntajeA.png")));

        basBackToMenu = new Base(170, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/backMenu.png")));

        basBackToPlay = new Base((tarGame.iWidth / 2), 600, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/BackToPlay.png")));

        basBackToPlay.setX((tarGame.iWidth / 2) - (basBackToPlay.getAncho() / 2));

    }

    private void checaColision() {

    }

    /**
     *
     * @throws FontFormatException
     * @throws IOException
     */
    public void initVars() throws FontFormatException, IOException {
        sCustomFile = "/Flood/Files/Quimica.txt";
        bPrincipal = true;
        bSecundario = false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
        bPlay = false;
        bHoverReturn = false;

        //Categorias selecccionadas
        bCatUno = true;
        bCatDos = false;
        bCatTres = false;
        bCatCustom = false;
        bSound = true;

        //inicializa posiciones del basbacktoplay
        iPosBackPlayX = 600;
        iPosBackPlayY = 600;

        iMouseYOffSet = 21;

        InputStream fontStream = getClass().getResourceAsStream("/Flood/Custom.ttf");
        this.fonFuenteMenu = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        this.fonFuenteMenu = this.fonFuenteMenu.deriveFont(40F);

    }

    public void paintComponent(Graphics graGrafico) {

        if (bPrincipal) {

            graGrafico.drawImage(imaMenuBackground, 0, 0, tarGame.iWidth, tarGame.iHeight, this);
            paintPrincipal(graGrafico);

        } else {

            paintSecondaryMenuSidePanel(graGrafico);
        }

        if (bInstrucciones) {
            paintInstrucciones(graGrafico);
        }

        if (bCreditos) {

            paintCreditos(graGrafico);

        } else if (bRecords) {
            paintRecords(graGrafico);

        }
    }

    //paint principal
    //paint principal

    /**
     *
     * @param graGrafico
     */
    public void paintPrincipal(Graphics graGrafico) {

        int iPrinicipalOffsetX = 70;
        int iPrinicipalOffsetY = 50;

        basCatUno.paint(graGrafico, this);

        basCatDos.paint(graGrafico, this);

        basCatTres.paint(graGrafico, this);

        basCatCustom.paint(graGrafico, this);

        basPlay.paint(graGrafico, this);
        basHelp.paint(graGrafico, this);
        basSound.paint(graGrafico, this);

        if (tarGame.side.bSound) {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/Sound.png")));

        } else {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/Mute.png")));

        }

        //current categroy selected
        paintSelectedCategory(graGrafico);

    }

    //Paint instructions, records y history

    /**
     *
     * @param graGrafico
     */
    public void paintSecondaryMenuSidePanel(Graphics graGrafico) {

        graGrafico.drawImage(imaPantallaInstrucciones, 0, 0, tarGame.iWidth, tarGame.iHeight, this);

        basRecords.paint(graGrafico, this);
        basInstrucciones.paint(graGrafico, this);
        basCreditos.paint(graGrafico, this);

        if (bInstrucciones) {
            //cambio de imagen
            basInstrucciones.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/InstruccionesB.png")));

        } else {
            //cambio de imagen
            basInstrucciones.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/InstruccionesA.png")));

        }

        if (bCreditos) {
            //cambio de imagen
            basCreditos.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/CreditosB.png")));

        } else {
            //cambio de imagen
            basCreditos.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/CreditosA.png")));

        }

        if (bRecords) {
            //cambio de imagen
            basRecords.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/PuntajeB.png")));

        } else {
            //cambio de imagen
            basRecords.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/PuntajeA.png")));

        }

        if (!tarGame.side.getHelp()) {
            if (bHoverReturn) {
                basBackToMenu.setImagen(imaBackToMenu);
                basBackToMenu.paint(graGrafico, this);
            } else {
                basBackToMenu.setImagen(imaBackToMenu2);
                basBackToMenu.paint(graGrafico, this);
            }
        }

    }

    //paint instrucciones

    /**
     *
     * @param graGrafico
     */
    public void paintInstrucciones(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawImage(imaInstrucciones, 327, 219, imaInstrucciones.getWidth(tarGame), imaInstrucciones.getHeight(tarGame), this);

        if (tarGame.side.getHelp()) { //si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }

    }

    //paint creditos

    /**
     *
     * @param graGrafico
     */
    public void paintCreditos(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawImage(imaCredits, 327, 219, imaInstrucciones.getWidth(tarGame), imaInstrucciones.getHeight(tarGame), this);

        if (tarGame.side.getHelp()) { //si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }

    }

    //paint records

    /**
     *
     * @param graGrafico
     */
    public void paintRecords(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);

        if (tarGame.side.getHelp()) { //si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }
    }

    //resalta la categoria seleccionada con un cambio de imagen en la base

    /**
     *
     * @param graGrafico
     */
    public void paintSelectedCategory(Graphics graGrafico) {

        if (!bCatUno) {

            //cambio de imagen
            basCatUno.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat1.png")));

        } else {
            //cambio de imagen
            basCatUno.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat1b.png")));

        }

        if (!bCatDos) {

            //cambio de imagen
            basCatDos.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat2.png")));

        } else {
            //cambio de imagen
            basCatDos.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat2b.png")));

        }

        if (!bCatTres) {

            //cambio de imagen
            basCatTres.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat3.png")));

        } else {
            //cambio de imagen
            basCatTres.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat3b.png")));

        }

        if (!bCatCustom) {

            //cambio de imagen
            basCatCustom.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat4.png")));
        } else {
            //cambio de imagen
            basCatCustom.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/menu/cat4b.png")));
        }

    }

    //Metodo que apaga todas las booleaneas de banner y ya despues de esto
    //prendes el banner que quieras usar

    /**
     *
     */
    public void falseAll() {
        bPrincipal = false;
        bSecundario = false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
    }

    //Metodo que apaga todas las booleaneas de las categorias y ya despues de esto
    //prendes la categoria que quieras usar

    /**
     *
     */
    public void falseAllCategories() {
        bCatUno = false;
        bCatDos = false;
        bCatTres = false;
        bCatCustom = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {

        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY() + iMouseYOffSet;

        /*
        System.out.println("---mouse--");
        System.out.println(iMouseX +" "+iMouseY);
        System.out.println("---Help--");
        System.out.println(basHelp.getX() +" "+basHelp.getY());
         */
        /**
         * MENU
         */
        //selecciona categoria 1
        if (basCatUno.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono cat #1
            System.out.println("Category 1 selected");
            falseAllCategories();
            bCatUno = true;
            sCustomFile = "/Flood/Files/Quimica.txt";
        }
        //selecciona categoria 2
        if (basCatDos.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono cat #2
            System.out.println("Category 2 selected");
            falseAllCategories();
            bCatDos = true;
            sCustomFile = "/Flood/Files/Matematicas.txt";
        }

        //selecciona categoria 3
        if (basCatTres.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono cat #3
            System.out.println("Category 3 selected");
            falseAllCategories();
            bCatTres = true;
            sCustomFile = "/Flood/Files/Geografia.txt";
        }

        //Selecciona categoria custom
        if (basCatCustom.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play
            setCustomCategoryClicked(true);
            System.out.println("Custom Category selected");
            falseAllCategories();
            bCatCustom = true;
            sCustomFile = chooseFile();
        }

        //Se abre ventana para seleccionar archivo.txt
        //selecciona play
        if (basPlay.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play
            falseAll();

            setPlay(true);
            System.out.println("Play EN MENU");

            setCustomCat(sCustomFile);
            System.out.println("Custom Category: " + sCustomFile);
            bPlay = true;
            tarGame.IniciaMenu(sCustomFile);
        }

        //selecciona Help
        if (basHelp.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play

            falseAll();
            bInstrucciones = true;
            bSecundario = true;
            System.out.println("Help");
        }

        //Sound click
        if (basSound.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play

            falseAll();
            tarGame.side.bSound = !tarGame.side.bSound;
            tarGame.side.manejaSonido();
            bPrincipal = true;
            System.out.println("Sound");
        }

        /**
         * Instrucciones, records, credits menu
         */
        //selecciona back to menu
        if (basBackToMenu.intersects(iMouseX, iMouseY) && bSecundario && !tarGame.side.getHelp()) { //seleciono play
            falseAll();
            bPrincipal = true;
            System.out.println("Back to menu");
        }

        //Instrucciones
        if (basInstrucciones.intersects(iMouseX, iMouseY) && bSecundario) { //seleciono play
            falseAll();
            bInstrucciones = true;
            bSecundario = true;
            System.out.println("Instrucciones");
        }

        //selecciona regresar al juego, desde instrucciones
        if (basBackToPlay.intersects(iMouseX, iMouseY) && bSecundario && tarGame.side.getHelp()) { //seleciono play
            falseAll();
            bPlay = true;
            tarGame.side.bPause = false;
            tarGame.side.setHelp(false);//apaga help
            System.out.println("Regreso a jugar");
        }

        //Records
        if (basRecords.intersects(iMouseX, iMouseY) && bSecundario) { //seleciono play
            falseAll();
            bRecords = true;
            bSecundario = true;
            System.out.println("Records");
        }

        //Credits Menu
        if (basCreditos.intersects(iMouseX, iMouseY) && bSecundario) { //seleciono play
            falseAll();
            bCreditos = true;
            bSecundario = true;
            System.out.println("Credits");
        }
    }

    String chooseFile() {

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(fileChooser);
            return ("." + fileChooser.getSelectedFile().getAbsolutePath());
        } catch (Exception e1) {

            System.out.print("No seleccionó un archivo");
            return "/Flood/Files/Defualt.txt";
        }

    }

    //Animación

    /**
     *
     */
    public void animacionImagenes() {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent mouEvent) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
