package Flood;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

/**
 *
 * @author Left & Right
 *
 */
public class BannerMenu {

    
    private Image imaBackground; //Imagen de fondo 
    
    //Bases Menu principal 
    private Base basCatUno; //boton categoria uno
    private Base basCatDos; //boton categoria dos
    private Base basCatTres; // boton categoria tres
    private Base basCatCustom; // boton custom 
    private Base basPlay; //Boton de play
    private Base basHelp; //Boton de play
    
    //Bases Help
    private Base basInstrucciones; //boton 
    private Base basCreditos; //boton de creditos
    private Base basRecords; //boton de records 
    private Base backToMenu; //boton de back to menu
    
    //banderas que controlan vistas
    private boolean bPrincipal; //boton que lleva a menu principal
    private boolean bInstrucciones; //boton que lleva a instrucciones 
    private boolean bCreditos; //boton que lleva a creditos
    private boolean bRecords; //boton que lleva a records
    protected boolean bPlay; //boton que lleva a al juego
    
    
    
    
    
    public void creaBotones(){
        
        basCatUno = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat1.png")));
        
        basCatDos = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat2.png")));
        
        basCatTres = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat3.png")));
        
        basCatCustom = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/cat4.png")));
        
        basPlay = new Base(200, 200, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/menu/play.png")));
        
        basHelp = new Base(200, 200, Toolkit.getDefaultToolkit()
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
    
    public void inicializaVariables(){
        
        bPrincipal = true;
        bInstrucciones = false;
        bCreditos =false;
        bRecords = false;
        bPlay = false;
        
    }
    
    public BannerMenu(){
        
        
       inicializaVariables();
       creaBotones();
       
       
        
    }
    //paint normal
    
    public void paint(Graphics graGrafico){
        
        if(bPrincipal){
            
        }else if(bInstrucciones){
        
        }else if (bCreditos) {
            
        }else if (bRecords){
            
        }
            
            
            
        bPrincipal = true;
        bInstrucciones = false;
        bCreditos =false;
        bRecords = false;
        bPlay = false;
        
    }
    
    //paint principal
    
    //paint instrucciones
    
    //paint creditos
    
    if(b)

    
    
    
    
    
    
    
    
    
    
    
     
    
    
    
    
}



