package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
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
    
    /**
     * Creates the flood instance. 
     */
    
    private Flood flood;
    
    /**
     * Crates a new BannerMenu instance.
     *
     * @param flood The Flood instance to use.
     */
    public BannerMenu(Flood flood) {

        this.flood = flood;
        setPreferredSize(new Dimension(flood.iWidth, flood.iHeight));
        setBackground(Color.BLACK);
    }

  
    
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
        
        //borrar
        graGrafico.fillRect(100, 100, 100, 100);
        
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
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}



