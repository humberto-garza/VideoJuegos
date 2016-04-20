package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
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
    private Base basPause;
    private Base basSound;

    //Objetos para las imagenes
    private Image imaImagenLogo;
    private Image imaImagenPuntaje;
    private Image imaImagenNivel;

    //Variables booleanas que indican si un botón fue presionado
    private boolean bBackMenu;
    private boolean bHelp;
    private boolean bPause;
    private boolean bSound;

    //Variables de posicion del mouse
    int iMouseX;
    int iMouseY;
    int iMouseXOffSet;
    
    //Variable que contiene el #de nivel como string, se usa para pintar el nivel
    private String sNivel;

    //The Flood Game instance
    private Flood tarGame;

    //Font a usar
    private Font fonFuentel;
<<<<<<< HEAD
    
    //Banner Menu
    private BannerMenu bannerMenu;
   
=======


>>>>>>> origin/master

    public SidePanel(Flood floodGame) throws FontFormatException, IOException {

        this.tarGame = floodGame;//instancia de flood
        initVars();
        creaBases();
        crearImagenes();
        addMouseListener(this);
        
        
        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/CustomL.ttf"));
        this.fonFuentel = this.fonFuentel.deriveFont(25F);

       // setPreferredSize(new Dimension(floodGame.iWidth /2, floodGame.iHeight));
        setPreferredSize(new Dimension(228, tarGame.iHeight));        
        



        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/Score.ttf"));
        this.fonFuentel = this.fonFuentel.deriveFont(40F);

        // setPreferredSize(new Dimension(floodGame.iWidth /2, floodGame.iHeight));
        setPreferredSize(new Dimension(228, floodGame.iHeight));

    }
    /**initvars
     inicializa las variables
     **/
    public void initVars() {
        //booleanas de botones
        bBackMenu = false;
        bHelp = false;
        bPause = false;
        bSound = true;
        //indica el nivel
        sNivel = Integer.toString(tarGame.iNivel);
        iMouseXOffSet = (tarGame.iWidth)-228;
    }

    /* crearImagenes
        crea las imagenes base del side panel
    */
    public void crearImagenes() {
        // Crear la imagen de fondo.
        imaImagenLogo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                        .getResource("Images/sidePanel/logo.png"));

        imaImagenPuntaje = Toolkit.getDefaultToolkit().getImage(this.getClass()
                           .getResource("Images/sidePanel/puntaje.png"));

        sNivel = "1";
        imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
                         .getResource("Images/sidePanel/nivel" + sNivel + ".png"));

    }

    /* creaBases
        crea los objetos base del side panel, botones
    */
    public void creaBases() {

        basBackMenu = new Base(640, 540, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/sidePanel/backAlMenu.png")));


        basSound = new Base(640, 650, Toolkit.getDefaultToolkit()
                            .getImage(this.getClass().getResource("Images/sidePanel/soundON.png")));


        basPause = new Base(basSound.getX() + basSound.getAncho(), 650, Toolkit.getDefaultToolkit()
                            .getImage(this.getClass().getResource("Images/sidePanel/pause.png")));

        basHelp = new Base(basPause.getX() + basPause.getAncho(), 650, Toolkit.getDefaultToolkit()
                           .getImage(this.getClass().getResource("Images/sidePanel/help.png")));

    }

    public void paintComponent(Graphics graGrafico) {
        //super.paintComponent(graGrafico);

        if (imaImagenLogo != null && imaImagenPuntaje != null &&
                imaImagenNivel != null) {
            //pinta imagenes
            graGrafico.drawImage(imaImagenLogo, 640, 50, 203, 70, this);
            graGrafico.drawImage(imaImagenPuntaje, 640, 150, 193, 145, this);

            Color colAux = new Color(255, 255, 255);
            graGrafico.setColor(colAux);
            graGrafico.setFont(fonFuentel);
            graGrafico.drawString(Integer.toString(tarGame.iPuntos), 660, 250);





            graGrafico.drawImage(imaImagenNivel, 640, 350, 173, 82, this);
        }
    
        if (!bBackMenu && basBackMenu != null && 
                basHelp !=null && basPause != null && basSound != null) {

            // Dibujar el objeto de menu
            basBackMenu.paint(graGrafico, this);
            basHelp.paint(graGrafico, this);
            basPause.paint(graGrafico, this);
            basSound.paint(graGrafico, this);
        } else {
            //Da un mensaje mientras se carga el dibujo
            graGrafico.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    /**manejaSonido
     * maneja el sonido del juego
     */
    public void manejaSonido() {
        if (bSound) {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/sidePanel/soundON.png")));
            //AQUI PONER EL SOUND.PLAY()
        } else {
            //cambio de imagen
            basSound.setImagen(Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/sidePanel/soundOFF.png")));
            //AQUI PONER EL SOUND.PAUSE()
        }
    }

    public void cambioNivel() {
        //indica el nivel
        sNivel = Integer.toString(tarGame.iNivel);

        //cambiar imagen a desplegar
        imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
                         .getResource("Images/sidePanel/nivel" + sNivel + ".png"));
    }

    public void mouseClicked(MouseEvent mouEvent) {
<<<<<<< HEAD
        
    
=======

>>>>>>> origin/master
        //actualiza posiciones del mouse
        iMouseX = mouEvent.getX()+iMouseXOffSet;
        iMouseY = mouEvent.getY();

        //checa si el jugador a presionado algun boton del panel
        if (basHelp.intersects(iMouseX, iMouseY)) {
            bHelp = true;//prende help
            System.out.println("clicked help");
            bBackMenu = false;//apaga las demás
        }
<<<<<<< HEAD
        else if (basPause.intersects(iMouseX, iMouseY) ) {
=======
        else if ( basPause.intersects(iMouseX, iMouseY) ) {
            System.out.println("clicked pause");
>>>>>>> origin/master
            bPause = !bPause;//niega pause
        }
        else if (basSound.intersects(iMouseX, iMouseY)) {
            System.out.println("clicked sound");
            bSound = !bSound;//niega sound
            manejaSonido();
        }
        else if (basBackMenu.intersects(iMouseX, iMouseY)){
<<<<<<< HEAD
            
=======
            System.out.println("clicked menu");
>>>>>>> origin/master
            bBackMenu = true;
            bannerMenu.setPlay(false);
             System.out.println("set play");
            bHelp = false;//apaga las demás
        }

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
