package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JPanel;

/**
 *
 * @author macbook
 */
public class SidePanel extends JPanel implements MouseListener {

    //Variable que indica si el mouse intersecta con algún botón
    private boolean bIntersects;

    //Objetos Base para los botones del panel
    protected Base basBackMenu;
    private Base basHelp;
    protected Base basPause;
    protected Base basSound;
    protected Base basYesSalir;//salirdeljuego
    protected Base basNoPlay;//seguir jugando

    //Objetos para las imagenes
    private Image imaImagenLogo;
    private Image imaImagenNivel;
    private Image imaImagenBannerSalir;
    private Image imaImagenPausa;

    //Variables booleanas que indican si un botón fue presionado
    private boolean bHelp;
    protected boolean bPause;
    protected boolean bSound;
    protected boolean bExit;
    protected boolean bBanner;
    protected boolean bLevelUp;

    //Variables que indican los tamaños del side panel
    private int iStartPanelX;

    //Variables de offsets para botones del panel
    private int iXOffsetSelections;

    //Variables de posicion del mouse
    int iMouseX;
    int iMouseY;
    int iMouseXOffSet;
    int iMouseYOffSet;

    //Variable que contiene el #de nivel como string, se usa para pintar el nivel
    private String sNivel;

    //The Flood Game instance
    private Flood tarGame;

    //Font a usar
    private Font fonFuentel;

    //Banner Menu
    private BannerMenu bannerMenu;
    
    //Offset del side panel 
    int iOffsetXimagen;
    int iOffsetYimagen;

    public SidePanel(Flood floodGame) throws FontFormatException, IOException {

        this.tarGame = floodGame;//instancia de flood
        initVars();
        creaBases();
        crearImagenes();
        addMouseListener(this);
        
        InputStream fontStream = getClass().getResourceAsStream("/Flood/Score.ttf");
        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        this.fonFuentel = this.fonFuentel.deriveFont(40F);

        // setPreferredSize(new Dimension(floodGame.iWidth /2, floodGame.iHeight));
        setPreferredSize(new Dimension((tarGame.iWidth) - ((tarGame.iWidth) - 289), tarGame.iHeight));
        //
        //228 dimension
    }

    /**
     * initvars inicializa las variables
     *
     */
    public void initVars() {
        //booleanas de botones
        bHelp = false;
        bPause = false;
        bSound = true;
        bExit = false;
        //indica el nivel
        sNivel = Integer.toString(tarGame.iNivel);
        
        bLevelUp = false;

        //offsets
        //iMouseXOffSet = (tarGame.iWidth)-228;
        iStartPanelX = (tarGame.iWidth) - 289;
        iMouseXOffSet = iStartPanelX;
        iMouseYOffSet = 20;
        //iMouseXOffSet = (tarGame.iWidth)-289;
//        iMouseXOffSet = (tarGame.iWidth)-283;
//        iStartPanelX = (tarGame.iWidth)-247;
        iXOffsetSelections = 3;
        
       
    }



    /* crearImagenes
        crea las imagenes base del side panel
     */
    public void crearImagenes() {
        // Crear la imagen de fondo.
        imaImagenLogo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/sidePanel/FloodPuntos.png"));

      
        sNivel = "1";
        imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/sidePanel/nivel" + sNivel + ".png"));

        
        imaImagenBannerSalir = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/sidePanel/bannerSalir.png"));
        
        imaImagenPausa = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/sidePanel/bannerPausa.png"));
    }

    /* creaBases
        crea los objetos base del side panel, botones
     */
    public void creaBases() {

        basBackMenu = new Base(630, 570, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/backMenu.png")));
        
        basPause = new Base(iStartPanelX, 662, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/Pause.png")));
        
        basSound = new Base(iStartPanelX, 662, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/Sound.png")));
        
        basHelp = new Base(iStartPanelX, 662, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/Question.png")));
        
        basPause.setX(iStartPanelX + 119 - (basPause.getAncho()/ 2));
   
        basSound.setX(basPause.getX()- basSound.getAncho() - iXOffsetSelections);
        
        basHelp.setX(basPause.getX()+basPause.getAncho()+iXOffsetSelections);
        
        basYesSalir= new Base(255, 400, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/yes.png")));
        
        basNoPlay = new Base(basYesSalir.getX()+basYesSalir.getAncho()+50 , 400, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/sidePanel/no.png")));
        

    }

    public void paintComponent(Graphics graGrafico) {

        //iOffsetXimagen = iStartPanelX + (119 - (imaImagenLogo.getWidth(this)/2));
        iOffsetXimagen = 630;
        iOffsetYimagen = 40;
        //super.paintComponent(graGrafico);
        /*pinta las imagenes*/

        if (imaImagenLogo != null && imaImagenNivel != null) {
            //pinta imagenes
            graGrafico.drawImage(imaImagenLogo, iOffsetXimagen, iOffsetYimagen, 196, 193, this);

            Color colAux = new Color(255, 255, 255);
            graGrafico.setColor(colAux);
            graGrafico.setFont(fonFuentel);

            Rectangle rect = new Rectangle(669, 195, 127, 34);

            fitInSquare(Integer.toString(tarGame.iPuntos), rect, graGrafico);
            //graGrafico.drawString(Integer.toString(tarGame.iPuntos), 710, 213);
            graGrafico.drawImage(imaImagenNivel, iOffsetXimagen, iOffsetYimagen + 212, imaImagenNivel.getWidth(this), imaImagenNivel.getHeight(this), this);
        } else {
            //Da un mensaje mientras se carga el dibujo
            graGrafico.drawString("No se cargo la imagen..", 20, 20);
        }

        /*pinta los objetos*/
        if (basBackMenu != null
                && basHelp != null && basPause != null && basSound != null) {

            // Dibujar el objeto de menu
            basBackMenu.paint(graGrafico, this);
            basHelp.paint(graGrafico, this);
            basPause.paint(graGrafico, this);
            basSound.paint(graGrafico, this);

        } else {
            //Da un mensaje mientras se carga el dibujo
            graGrafico.drawString("No se cargo la imagen..", 20, 20);
        }
        
        if (bPause && !bExit){//el juego esta en pausa
            //pinta banner de fondo
            graGrafico.drawImage(imaImagenPausa, 0, 0, imaImagenPausa.getWidth(this), imaImagenPausa.getHeight(this), this);

        }
        
        if (bExit){//el usuario quiere regresar a menu
            //pinta banner de fondo
            graGrafico.drawImage(imaImagenBannerSalir, 0, 0, tarGame.iWidth, tarGame.iHeight, this);
            //pinta los botones/objetos
            basYesSalir.paint(graGrafico, this);
            basNoPlay.paint(graGrafico, this);
        }
        
        

    }

    /**
     * manejaSonido maneja el sonido del juego
     */
    public void manejaSonido() {
        if (bSound) {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/sidePanel/Sound.png")));
            //AQUI PONER EL SOUND.PLAY()
        } else {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/sidePanel/Mute.png")));
            //AQUI PONER EL SOUND.PAUSE()
        }
    }
    
    /**
     * manejaPausa maneja la pausa del juego
     */
    public void manejaPausa(){
        if (bPause) {//esta en pausa
            //cambio de imagen
            basPause.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/sidePanel/Play.png")));
        } else {//no esta en pausa
            //cambio de imagen
            basPause.setImagen(Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/sidePanel/Pause.png")));
        }
    }

    public void cambioNivel() {
        //indica el nivel
        sNivel = Integer.toString(tarGame.iNivel);
        
        bLevelUp = true;

        //cambiar imagen a desplegar
        imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/sidePanel/nivel" + sNivel + ".png"));
    }
    
    public boolean getHelp(){
        return bHelp;
    }
    
    public void setHelp(boolean help){
        this.bHelp = help;
    }
    
    
    
    public void mouseClicked(MouseEvent mouEvent) {

        System.out.println("CLICK");

        //actualiza posiciones del mouse
        iMouseX = mouEvent.getX() + iMouseXOffSet;
        iMouseY = mouEvent.getY();

        //checa si el jugador ha presionado algun boton del panel
        if (basHelp.intersects(iMouseX, iMouseY) && !bExit) {
            bHelp = true;//prende help
            System.out.println("clicked help");
            tarGame.bannerMenu.setInstrucciones(true);//para que despliegue instrucciones
            tarGame.bannerMenu.setSecundario(true);//para que despliegue menu secundario
        } else if (basPause.intersects(iMouseX, iMouseY) && !bExit) {
            System.out.println("clicked pause");
            bPause = !bPause;//niega pause
            manejaPausa();
        } else if (basSound.intersects(iMouseX, iMouseY) && !bExit) {
            System.out.println("clicked sound");
            bSound = !bSound;//niega sound
            manejaSonido();
        } else if (basBackMenu.intersects(iMouseX, iMouseY) && !bExit) {
            
            
            /*quieres salir del juego?*/
            //pausa juego para que no aparezcan cuadros
            bPause = true;//pone pausa, si es que no esta puesta
            //prende booleana para desplegar mensaje de "seguro?"
            bExit = true;

            System.out.println("clicked menu");

            //Segun yo SDN borrar, pues 
            //bBackMenu = true;
            //bHelp = false;//apaga las demás
            //Segun yo SDN borrar, pues 
            //SDN - Variables de Banner Menu
            
            /**
            
            tarGame.bannerMenu.setPlay(false);
            System.out.println("set play");
            tarGame.bannerMenu.falseAll();
            tarGame.bannerMenu.bPrincipal = true;
            **/
            
            
            
            //SDN - Variables de Banner Menu

        }
        
        /*
        if (bExit){//esta en el banner de exit
            if (basYesSalir.intersects(iMouseX, iMouseY)) {//salir del juego
                bExit = false;//quita el banner
                bPause = false;//quitar pausa
                //regresar al menu
                tarGame.bannerMenu.setPlay(false);
                System.out.println("set play");
                tarGame.bannerMenu.falseAll();
                tarGame.bannerMenu.bPrincipal = true;
            } else if (basNoPlay.intersects(iMouseX, iMouseY)) {//seguir jugando
                System.out.println("ENTROOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!");
                bExit = false;//quitar banner
                bPause = false;//quitar pausa
            }
        }
*/

    }
    
    
     public void fitInSquare(String sQuestion, Rectangle rect, Graphics graGrafico) {

        int iStringWidth = graGrafico.getFontMetrics().stringWidth(sQuestion);
        int iStringHeight = graGrafico.getFontMetrics().getAscent();

        int iOffsetX = (int) rect.getX() + (int) (rect.getWidth() / 2) - (iStringWidth / 2);
        int iOffsetY = (int) rect.getY() + (int) (rect.getHeight() / 2) - (iStringHeight / 2) + 20;

        Color colAux = new Color(255, 255, 255);
        graGrafico.setColor(colAux);
        graGrafico.setFont(fonFuentel);
       
        graGrafico.drawString(sQuestion, iOffsetX, iOffsetY);

    }
            

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
