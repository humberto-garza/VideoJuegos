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
    private Base basHelp; //Boton de play
    
    //Bases Pantalla de Help
    private Base basInstrucciones; //boton 
    private Base basCreditos; //boton de creditos
    private Base basRecords; //boton de records 
    private Base backToMenu; //boton de back to menu
    
    //Imagenes Menu principal 
    Image imaCatUno;
    Image imaCaDos;
    Image imaCatTres;
    Image imaCatCUstom;
    Image imaPlay; //boton de play
    Image imaHelp; //boton de help
    
    
    //Imagenes Pantalla de Help
    
    //Offset para acomodar los paneles
    int iOffset;
    
    //Posicion del mouse
    int iPosX;
    int iPosY;
    
    
    //banderas que controlan vistas
    private boolean bPrincipal; //boton que lleva a menu principal
    private boolean bInstrucciones; //boton que lleva a instrucciones 
    private boolean bCreditos; //boton que lleva a creditos
    private boolean bRecords; //boton que lleva a records
    private boolean bPlay; //boton que lleva a al juego
    
    //Font a usar
    private Font fonFuentel;
    
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
        
        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/CustomL.ttf"));
        this.fonFuentel = this.fonFuentel.deriveFont(25F);
        
        setPreferredSize(new Dimension(floodGame.iWidth -200, floodGame.iHeight));        
        setBackground(Color.CYAN);
     
    }
    
    public void creaImagenes(){
        
    }
    
    public void setPlay(boolean bPlay){
        
        this.bPlay = bPlay;
      
       
    }
    
    public boolean getPlay(){
        
        return bPlay;
     
    }
    
    
    

  
    
    public void creaBases(){
        
        //refactor 
        int iOffsetY = 60;
        int iOffsetX = 120;
        int iPosicionX = (tarGame.iWidth/2) - 100; 
        int iPosicionY = tarGame.iHeight/2 - 200;
        
        
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
        
        basInstrucciones = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/instrucciones.png")));
        
        basCreditos = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/creditos.png")));
        
        basRecords = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/records.png")));
        
        backToMenu = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/backMenu.png")));
           
    }
    
    
    private void checaColision(){
        
        
        
    }
   
    
    public void initVars(){
        
        bPrincipal = true;
        bInstrucciones = false;
        bCreditos =false;
        bRecords = false;
        bPlay = false;
       
    }
    

    //paint normal
    
    public void paintComponent(Graphics graGrafico){
        
        super.paint(graGrafico);
        
        basPlay.paint(graGrafico,this);
        basHelp.paint(graGrafico, this);
        backToMenu.paint(graGrafico, this);
        
     
        if(bPrincipal){
            
            paintPrincipal(graGrafico);
            
        }else if(bInstrucciones){
            
            paintInstrucciones(graGrafico);
        
        }else if (bCreditos) {
            
            paintCreditos(graGrafico);
            
        }else if (bRecords){
            paintRecords(graGrafico);
            
        }else if (bPlay){
            
            
        }
        
        
     
    }
    
    //paint principal
    public void paintPrincipal(Graphics graGrafico){
        
         //basSelector.paint(graDibujo, this);
        basCatUno.paint(graGrafico, this);
        basCatDos.paint(graGrafico, this);
        basCatTres.paint(graGrafico,this);
        basCatCustom.paint(graGrafico,this);
                        
    }
    
    //paint instrucciones
    public void paintInstrucciones(Graphics graGrafico){
     
        basInstrucciones.paint(graGrafico, this);
        
    }
    
    //paint creditos
    public void paintCreditos(Graphics graGrafico){
        
        basCreditos.paint(graGrafico, this);
        
    }
   //paint records
    public void paintRecords(Graphics graGrafico){
        
        basRecords.paint(graGrafico, this);
        
    }
    
   
    //Metodo que apaga todas las booleaneas de banner y ya despues de esto
    //prendes el banner que quieras usar
    void falseAll() {

        bPrincipal = false;
        bInstrucciones = false;
        bCreditos = false;
        bRecords = false;
        bPlay = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {
          
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



