package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Left & Right
 *
 */
public class BannerMenu extends JPanel implements MouseListener{

    
    private Image imaBackground; //Imagen de fondo 
    
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
    
    //Imagenes Menu principal 
    Image imaMenuBackground; //Background menu
    
    
    //Offsets
    private int iSecondaryMenuOffsetX; //Para menu secundario
    private int iSecondaryMenuOffsetY; //Para menu secundario
    
    private int iOffsetX; //Para menu principal
    private int iOffsetY; //Para menu Principal
    
    
    //Posicion del mouse
    int iMouseX;
    int iMouseY;
    int iMouseYOffSet;
    
    
    //banderas que controlan vistas
    protected boolean bPrincipal; //boton que lleva a menu principal
    private boolean bInstrucciones; //boton que lleva a instrucciones 
    private boolean bCreditos; //boton que lleva a creditos
    private boolean bRecords; //boton que lleva a records
    private boolean bPlay; //boton que lleva a al juego
    
    //Font a usar
    private Font fonFuente;
    
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
     * @param flood The Flood instance to use.
     */
    public BannerMenu(Flood floodGame) throws FontFormatException, IOException {

        this.tarGame = floodGame;
        initVars();
        creaBases(); 
        creaImagenes();
        addMouseListener(this);
        
        
        
        //setPreferredSize(new Dimension(tarGame.iWidth, tarGame.iHeight));        
        //setBackground(Color.CYAN);
     
    }
    
    public void creaImagenes(){
        
        imaMenuBackground = Toolkit.getDefaultToolkit().getImage(this.getClass()
               .getResource("Images/menu/MenuBanner.png"));
    }
    
    public void setPlay(boolean bPlay){
        
        this.bPlay = bPlay;
      
       
    }
    
    public boolean getCustomCategoryClicked(){
        
        return bCustomCategoryClicked;
     
    }
    
    public void setCustomCategoryClicked(boolean bCustomCategoryClicked){
        
        this.bCustomCategoryClicked = bCustomCategoryClicked;
      
       
    }
    
    public boolean getPlay(){
        
        return bPlay;
     
    }
    
    public void setCustomCat(String sCustomCat){
        
        this.sCustomCat = sCustomCat;
      
       
    }
    
    public String getCustomCat(){
        
        return sCustomCat;
     
    }
    
    
    public void creaBases(){
        
       
        iOffsetY = 60; //Offset para categorias
        iOffsetX = 120;  //Offset para categorias
        
         //refactor 
        int iPosicionX = (tarGame.iWidth/2) - 100; 
        int iPosicionY = (tarGame.iHeight/2) - 200;
        
        iSecondaryMenuOffsetX = 30;
        iSecondaryMenuOffsetY = 80;
        
        
        
        
        /**
         * Home  
         */
        basCatUno = new Base(iPosicionX,iPosicionY += iOffsetY ,Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat1.png")));
        
        basCatDos = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat2.png")));
        
        basCatTres = new Base(iPosicionX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat3.png")));
        
        basCatCustom = new Base(iPosicionX , iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat4.png")));
        
        basPlay = new Base(iPosicionX += iOffsetX, iPosicionY += iOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/play.png")));
        
        basHelp = new Base(basPlay.getX() - iOffsetX, basPlay.getY(), Toolkit.getDefaultToolkit()
                                .getImage(this.getClass().getResource("Images/menu/help.png")));
        
        
        /**
         * Menu secundario
         */
        
        
        iPosicionX = (tarGame.iWidth/2) - 350;
        iPosicionY = (tarGame.iHeight/2) - 250;
        
        
        basInstrucciones = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/instrucciones.png")));
        
        basCreditos = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/creditos.png")));
        
        basRecords = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/records.png")));
        
        basBackToMenu = new Base(iPosicionX, iPosicionY += iSecondaryMenuOffsetY, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/backMenu.png")));
           
    }
    
    
    private void checaColision(){
        
        
        
    }
   
    
    public void initVars() throws FontFormatException, IOException{
        
        bPrincipal = true;
        bInstrucciones = false;
        bCreditos =false;
        bRecords = false;
        bPlay = false;
        
        iMouseYOffSet = 21;
        
        
        this.fonFuente = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/Custom.ttf"));
        this.fonFuente = this.fonFuente.deriveFont(40F);
        
        
        setCustomCategoryClicked(false);
       
    }
    
    
    public void paintComponent(Graphics graGrafico){
        
        graGrafico.drawImage(imaMenuBackground, 0, 0, tarGame.iWidth, tarGame.iHeight, this);
        
        if(bPrincipal){
            paintPrincipal(graGrafico);
        }
             
        if(bInstrucciones){
            paintInstrucciones(graGrafico);
        }
        
        if (bCreditos) {
            
            paintCreditos(graGrafico);
            
        }else if (bRecords){
            paintRecords(graGrafico);
            
        }else if (bPlay){
            
            
        }
        
    }
    
    //paint principal
    public void paintPrincipal(Graphics graGrafico){
          
        basCatUno.paint(graGrafico, this);
        basCatDos.paint(graGrafico, this);
        basCatTres.paint(graGrafico,this);
        basCatCustom.paint(graGrafico,this);
        basPlay.paint(graGrafico,this);
        basHelp.paint(graGrafico, this);
                  
    }
    
    //paint instrucciones
    public void paintInstrucciones(Graphics graGrafico){
 
        basInstrucciones.paint(graGrafico, this);
        basCreditos.paint(graGrafico, this);
        basRecords.paint(graGrafico, this);
        basBackToMenu.paint(graGrafico, this);
       
        graGrafico.setFont(fonFuente);
        graGrafico.drawString("Instruciones", 100, 100);
    }
    
    //paint creditos
    public void paintCreditos(Graphics graGrafico){
        
        basInstrucciones.paint(graGrafico, this);
        basCreditos.paint(graGrafico, this);
        basRecords.paint(graGrafico, this);
        basBackToMenu.paint(graGrafico, this);
        
        graGrafico.setFont(fonFuente);
        graGrafico.drawString("Créditos", 100, 100);
        
    }
   //paint records
    public void paintRecords(Graphics graGrafico){
        
        basInstrucciones.paint(graGrafico, this);
        basCreditos.paint(graGrafico, this);
        basRecords.paint(graGrafico, this);
        basBackToMenu.paint(graGrafico, this);
        
        graGrafico.setFont(fonFuente);
        graGrafico.drawString("Records", 100, 100);
        
    }
    
   
    //Metodo que apaga todas las booleaneas de banner y ya despues de esto
    //prendes el banner que quieras usar
    public void falseAll() {
        bPrincipal = false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {
        
        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY()+iMouseYOffSet;
        
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
       
        
        //selecciona categoria 2
        
        //selecciona categoria 3
        
        //Selecciona categoria custom 
         if (basCatCustom.intersects(iMouseX, iMouseY)) { //seleciono play
            
            setCustomCategoryClicked(true);
            System.out.println("Custom Category clicked");
            chooseFile(sCustomFile);
            
                
        }
        
        
        //Se abre ventana para seleccionar archivo.txt 
        
        //selecciona play
        if (basPlay.intersects(iMouseX, iMouseY)) { //seleciono play
            falseAll(); 
           
            setPlay(true); 
            System.out.println("Play!!!!!");
            
            setCustomCat(sCustomFile);
            System.out.println("Custom Category: " + sCustomFile);
        }
       
        
        //selecciona Help
        if ( basHelp.intersects(iMouseX, iMouseY) ) { //seleciono play
             
             falseAll();
             bInstrucciones = true;
             System.out.println("Help");
        }
        
        /**
         * Instrucciones, records, credits menu
         */
        
        
    
        //selecciona back to menu
        if (basBackToMenu.intersects(iMouseX, iMouseY)) { //seleciono play
            falseAll(); 
            bPrincipal = true; 
            System.out.println("Back to menu");
        }
        
        //Instrucciones 
        if (basInstrucciones.intersects(iMouseX, iMouseY)) { //seleciono play
            falseAll(); 
            bInstrucciones = true; 
            System.out.println("Instrucciones");
        }
        
        //Records
        if (basRecords.intersects(iMouseX, iMouseY)) { //seleciono play
            falseAll(); 
            bRecords = true; 
            System.out.println("Records");
        }
        
        //Credits Menu
        if (basCreditos.intersects(iMouseX, iMouseY)) { //seleciono play
            falseAll(); 
            bCreditos = true; 
            System.out.println("Credits");
        }
    }
    
    void chooseFile(String sFile) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        sFile = fileChooser.getSelectedFile().getAbsolutePath();
        
        System.out.println("Archivo seleccionado de choose file: "+ sFile);
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
    
    
}



