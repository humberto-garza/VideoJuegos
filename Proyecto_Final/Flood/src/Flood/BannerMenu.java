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

    int iContadorAnimacion = 0; //borrar
    int iSizeImageX = 3;
    int iSizeImageY = 3;
    
    
    //Bases Menu principal 
    private Base basCatUno; //boton categoria uno
    private Base basCatDos; //boton categoria dos
    private Base basCatTres; // boton categoria tres
    private Base basCatCustom; // boton custom 
    protected Base basPlay; //Boton de play
    private Base basHelp; //Boton de question

    //Bases Pantalla de Help
    private Base basInstrucciones; //boton 
    private Base basCreditos; //boton de creditos
    private Base basRecords; //boton de records 
    private Base basBackToMenu; //boton de back to menu
    private Base basBackToPlay; //boton de regreso al juego

    //Imagenes Menu principal
    Image imaMenuBackground; //Background menu
    private Image imaBackToMenu2; //imagen de animacion del boton de back to menu2
    private Image imaBackToMenu; //imagen de animacion del boton de back to menu2
    private Image imaPantallaInstrucciones; //imagen de animacion del boton de back to menu2
    
   

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
    protected boolean bPrincipal; //boton que lleva a menu principal
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
    
    //Linked list de animaciones
    LinkedList<Image> lklSplash;
        
    

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
    
    
    
    
    //Animacion
    Image splash1;
    Image splash2;
    Image splash3;
    Image splash4;
    Image splash5;
    Image splash6;
    Image splash7;
    Image splash8;
    Image splash9;
    Image splash10;
    Image splash11;
    Image splash12;
    Image splash13;
    Image splash14;
    Image splash15;
    Image splash16;
    Image splash17;
    Image splash18;
    Image splash19;

    
    
    

    //boolean bCustomCategoryClicked
    private boolean bCustomCategoryClicked;

    /**
     * Creates the flood instance.
     */
    private Flood tarGame;

    /**
     * Crates a new BannerMenu instance.
     *
     * @param flood The Flood instance to use.
     */
    public BannerMenu(Flood floodGame) throws FontFormatException, IOException {

        this.tarGame = floodGame;
        initVars();
        creaBases();
        creaImagenes();
        addMouseListener(this);
        animacion();

        //setPreferredSize(new Dimension(tarGame.iWidth, tarGame.iHeight));        
        //setBackground(Color.CYAN);
    }

    public void creaImagenes() {

        imaMenuBackground = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/PantallaJuego.png"));

        imaBackToMenu = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/backMenu.png"));

        imaBackToMenu2 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/backMenu2.png"));
        
        imaPantallaInstrucciones =  Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/menu/PantallaInstrucciones.png"));
    }

    public void setPlay(boolean bPlay) {

        this.bPlay = bPlay;

    }
    
    public void setSecundario(boolean bSecundario) {

        this.bSecundario = bSecundario;

    }
    
    public boolean getSecundario() {

        return bSecundario;

    }
    
    public void setInstrucciones(boolean bInstrucciones) {

        this.bInstrucciones = bInstrucciones;

    }

    public boolean getCustomCategoryClicked() {

        return bCustomCategoryClicked;

    }

    public void setCustomCategoryClicked(boolean bCustomCategoryClicked) {

        this.bCustomCategoryClicked = bCustomCategoryClicked;

    }

    public boolean getPlay() {

        return bPlay;

    }
    
    public boolean getInstrucciones() {

        return bInstrucciones;

    }

    public void setCustomCat(String sCustomCat) {

        this.sCustomCat = sCustomCat;

    }

    public String getCustomCat() {

        return sCustomCat;

    }

    public boolean getCurrentCat(){
        
        return bCurrentCat;
    }
    
    public void setCurrentCat(boolean bCurrentCat){
        this.bCurrentCat = bCurrentCat;
    }
    
    public void creaBases() {

        //MENU PRINCIPAL
        iOffsetY = 88; //Offset para categorias
        iOffsetX = 120;  //Offset para categorias

        //refactor 
        int iPosicionX = 240;
        int iPosicionY = 200;

        int iOffsetXBottomPanel; //offset para boton de play y de help
        int iOffsetYBottomPanel; //offset para boton de play y de help

        //MENU SECUNDARIO records, credits, instructions
        iSecondaryMenuOffsetX = 30;//
        iSecondaryMenuOffsetY = 80;

        /**
         * Home
         */
        basCatUno = new Base(iPosicionX, iPosicionY , Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat1.png")));

        basCatDos = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat2.png")));

        basCatTres = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat3.png")));

        basCatCustom = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/cat4.png")));

    
        iPosicionY = (tarGame.iHeight / 2) + 134;
        iPosicionX = 290;
        
        
        /**
         * Bottom Home
         */
        basHelp = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/help.png")));

        basPlay = new Base(basHelp.getX() + 250, basHelp.getY(), Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/play.png")));

        /**
         * Menu secundario
         */
        iPosicionX = (tarGame.iWidth / 2) - 330;
        iPosicionY = (tarGame.iHeight / 2) - 250;
        

        basInstrucciones = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/instrucciones.png")));
        
        basCreditos = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/creditos.png")));

        basRecords = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/records.png")));

        basBackToMenu = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/backMenu.png")));
        
        basBackToPlay = new Base(500, 600, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/menu/backToPlay.png")));

    }

    private void checaColision() {

    }

    public void initVars() throws FontFormatException, IOException {

        bPrincipal = true;
        bSecundario= false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
        bPlay = false;
        bHoverReturn = false;
        
        //Categorias selecccionadas
        bCatUno = true;
        bCatDos = false;
        bCatTres = false;
        bCatCustom= false;
 
        //inicializa posiciones del basbacktoplay
        iPosBackPlayX =  600;
        iPosBackPlayY =  600;
        
        iMouseYOffSet = 21;
        
        
        this.fonFuenteMenu = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/Custom.ttf"));
        this.fonFuenteMenu = this.fonFuenteMenu.deriveFont(40F);
      
        
        animacion();

    }

    public void paintComponent(Graphics graGrafico) {

        if (bPrincipal) {
            
       
            graGrafico.drawImage(imaMenuBackground, 0, 0, tarGame.iWidth, tarGame.iHeight, this);
            paintPrincipal(graGrafico);

            animacionSplash(graGrafico); //splash animation
               
        } 
        
        else {

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
    public void paintPrincipal(Graphics graGrafico) {

        int iPrinicipalOffsetX = 50;
        int iPrinicipalOffsetY = 40;
        
        basCatUno.paint(graGrafico, this);
        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawString("Química", basCatUno.getX() + iPrinicipalOffsetX, basCatUno.getY() + iPrinicipalOffsetY);
        
        
        basCatDos.paint(graGrafico, this);
        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawString("Matemáticas", basCatDos.getX() + iPrinicipalOffsetX, basCatDos.getY() + iPrinicipalOffsetY);
        
        basCatTres.paint(graGrafico, this);
        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawString("Geografía", basCatTres.getX() + iPrinicipalOffsetX, basCatTres.getY() + iPrinicipalOffsetY);
        
        basCatCustom.paint(graGrafico, this);
        graGrafico.setFont(fonFuenteMenu);
        graGrafico.drawString("Escoge...", basCatCustom.getX() + iPrinicipalOffsetX, basCatCustom.getY() + iPrinicipalOffsetY);
        
        
        basPlay.paint(graGrafico, this);
        basHelp.paint(graGrafico, this);
        
        //current categroy selected
        paintSelectedCategory (graGrafico);

    }

    //Paint instructions, records y history 
    public void paintSecondaryMenuSidePanel(Graphics graGrafico) {

        graGrafico.drawImage(imaPantallaInstrucciones, 0, 0, tarGame.iWidth, tarGame.iHeight, this);
        basInstrucciones.paint(graGrafico, this);
        basCreditos.paint(graGrafico, this);
        basRecords.paint(graGrafico, this);
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
    public void paintInstrucciones(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);
        graGrafico.fillRect(basInstrucciones.getX(),basInstrucciones.getY() + basInstrucciones.getAlto(),basInstrucciones.getAncho(),5);

        if (tarGame.side.getHelp()){//si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }   
    }
    
    //paint creditos
    public void paintCreditos(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);
        graGrafico.fillRect(basCreditos.getX(),basCreditos.getY() + basCreditos.getAlto(),basCreditos.getAncho(),5);
        
        if (tarGame.side.getHelp()){//si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }

    }
    
    //paint records
    public void paintRecords(Graphics graGrafico) {

        graGrafico.setFont(fonFuenteMenu);
        graGrafico.fillRect(basRecords.getX(),basRecords.getY() + basRecords.getAlto(),basRecords.getAncho(),5);

        if (tarGame.side.getHelp()){//si esta prendido significa que el usuario le pico help
            basBackToPlay.paint(graGrafico, this);//da la opcion de regresar al juego
        }
    }
    
    //resalta la categoria seleccionada
    public void paintSelectedCategory(Graphics graGrafico) {

        int iOffsetX = 21;
        int iOffsetY = basCatUno.getAlto()/2 - 6;
        
        
        if(bCatUno){
            graGrafico.fillOval(basCatUno.getX() + iOffsetX , 
                    basCatUno.getY() + iOffsetY, 10, 10);
        }
        
        if(bCatDos){
            graGrafico.fillOval(basCatDos.getX() + iOffsetX , 
                    basCatDos.getY() + iOffsetY, 10, 10);
        }
        
        if(bCatTres){
            graGrafico.fillOval(basCatTres.getX() + iOffsetX , 
                    basCatTres.getY() + iOffsetY, 10, 10);
        }
        
        if(bCatCustom){
            graGrafico.fillOval(basCatCustom.getX() + iOffsetX , 
                    basCatCustom.getY() + iOffsetY, 10, 10);
        }
       

    }

    //Metodo que apaga todas las booleaneas de banner y ya despues de esto
    //prendes el banner que quieras usar
    public void falseAll() {
        bPrincipal = false;
        bSecundario = false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
    }
    
    //Metodo que apaga todas las booleaneas de las categorias y ya despues de esto
    //prendes la categoria que quieras usar
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
         if (basCatUno.intersects(iMouseX, iMouseY) && bPrincipal ) { //seleciono cat #1 
             System.out.println("Category 1 selected");
             falseAllCategories();
             bCatUno = true;
        }
        //selecciona categoria 2
        if (basCatDos.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono cat #2
             System.out.println("Category 2 selected");
             falseAllCategories();
             bCatDos = true;
        }
        
        //selecciona categoria 3
        
        if (basCatTres.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono cat #3 
             System.out.println("Category 3 selected");
             falseAllCategories();
             bCatTres = true;
        }
        
        //Selecciona categoria custom         
        if (basCatCustom.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play

            setCustomCategoryClicked(true);
            System.out.println("Custom Category selected");
            falseAllCategories();
            bCatCustom = true;
            chooseFile(sCustomFile);

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
        }

        //selecciona Help
        if (basHelp.intersects(iMouseX, iMouseY) && bPrincipal) { //seleciono play

            falseAll();
            bInstrucciones = true;
            bSecundario = true;
            System.out.println("Help");
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

    void chooseFile(String sFile) {

        
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(fileChooser);
            sFile = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Archivo seleccionado de choose file: " + sFile);
        }
        
       catch (Exception e1){
        
           System.out.print("No seleccionó un archivo");
       }
        
        

    }
    
    //Animación
    
    public void animacionImagenes(){
        
        splash1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1A.png"));
        splash2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1B.png"));
        splash3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2A.png"));
        splash4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2B.png"));
        splash5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3A.png"));
        splash6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3B.png"));
        splash7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4A.png"));
        splash8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4B.png"));
        splash9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5A.png"));
        splash10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5B.png"));
        splash11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6A.png"));
        splash12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6B.png"));
        splash13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7A.png"));
        splash14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7B.png"));
        splash15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8A.png"));
        splash16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8B.png"));
        splash17 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9A.png"));
        splash18 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9B.png"));
        splash19 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/10A.png"));
    }
    
    public void animacion(){
        
        animacionImagenes();
        
        lklSplash = new LinkedList();
        
        lklSplash.add(splash1);
        lklSplash.add(splash2);
        lklSplash.add(splash3);
        lklSplash.add(splash4);
        lklSplash.add(splash5);
        lklSplash.add(splash6);
        lklSplash.add(splash7);
        lklSplash.add(splash8);
        lklSplash.add(splash9);
        lklSplash.add(splash10);
        lklSplash.add(splash11);
        lklSplash.add(splash12);
        lklSplash.add(splash13);
        lklSplash.add(splash14);
        lklSplash.add(splash15);
        lklSplash.add(splash16);
        lklSplash.add(splash17);
        lklSplash.add(splash18);
        lklSplash.add(splash19);
    }
    
    public void animacionSplash(Graphics graGrafico) {

        Rectangle rectReference = new Rectangle();
        
        rectReference.setBounds(400,400,88,88);
        
        int iRectOffsetX = (int)rectReference.getX() + (rectReference.width/2) - (iSizeImageX/2);
        int iRectOffsetY = (int)rectReference.getY() + (rectReference.height/2) - (iSizeImageY/2);
        
        if (iContadorAnimacion < lklSplash.size()) {

            iContadorAnimacion++;
            iSizeImageX += 5;
            iSizeImageY += 5;

        } else {
            iSizeImageX = 0;
            iSizeImageY = 0;
            iContadorAnimacion = 0;
        }

        graGrafico.drawImage(lklSplash.get(iContadorAnimacion),iRectOffsetX, iRectOffsetY, iSizeImageX, iSizeImageY, (tarGame));

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
