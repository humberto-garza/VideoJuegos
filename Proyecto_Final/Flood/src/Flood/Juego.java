// package Flood;

// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;
// import java.awt.FontFormatException;
// import java.awt.Graphics;
// import java.awt.Image;
// import java.awt.Toolkit;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.io.FileInputStream;
// import java.io.IOException;
// import javax.swing.JPanel;

// /**
//  *
//  * @author macbook
//  */
// public class Juego extends JPanel implements KeyListener {

//     //The Flood Game instance
//     private Flood tarGame;

//     // Variables de la cuadricula
//     private int iCuadroAncho;   // Ancho del Cuadro
//     private int iCuadroAlto;    // Alto del Cuadro
//     private int iCuadroMargen;  // Margen de los Cuadros
//     private int iGridOffsetX;   // Posicion de la cuadricula X
//     private int iGridOffsetY;   // Posicion de la cuadricula Y
//     private int iCasillas;      // Total de casillas
//     private int iGridRows;      // Cantidad filas
//     private int iGridCols;      // Cantidad columnas
//     private int iIndexActual;   // Index del cuadro actual

//     // Variables Display Pregunta
//     private int iDisPregOffX;   // Offset del Display de Pregunta X
//     private int iDisPregOffY;   // Offset del Display de Pregunta Y
//     private DisplayPregunta disPregunta;

//     // Variables Display Respuesta
//     private int iDisResOffX;    // Offset del Display de Respuesta X
//     private int iDisResOffY;    // Offset del Display de Respuesta Y
//     private DisplayRespuesta disRespuesta;

//     // Estructura de posicionamiento
//     private LinkedList<Coordenada> cooCoordenadas;

//     // Objeto Base Principal
//     private Base basSelector; // Objeto Selector

    
//     public SidePanel(Flood floodGame) throws FontFormatException, IOException {

//         this.tarGame = floodGame;   // Instancia de flood
//         initVars();
//         creaBases();
//         crearImagenes();
//         addMouseListener(this);

//         // Variables de la cuadricula
//         iCuadroAncho    = 110;
//         iCuadroAlto     = 110;
//         iCuadroMargen   = 4;
//         iGridOffsetX    = 50;
//         iGridOffsetY    = 110;
//         iGridRows       = 5;
//         iGridCols       = 5;
//         iCasillas       = iGridRows * iGridCols;
//         iIndexActual    = 0;

//         // Variables Display Pregunta
//         iDisPregOffX    = 60;
//         iDisPregOffY    = 67;
//         disPregunta     = new DisplayPregunta(iDisPregOffX, iDisPregOffY, "", "");

//         // Variables Cuadro Respuesta
//         iDisResOffX     = 60;
//         iDisResOffY     = 710;
//         disRespuesta    = new DisplayRespuesta(iDisResOffX, iDisResOffY, "");

//         // Estructura de posicionamiento
//         cooCoordenadas = new LinkedList<Coordenada>();
//         for (int iRow = 0; iRow < iGridRows; iRow++) {
//             for (int iCol = 0; iCol < iGridCols; iCol++) {
//                 int iAuxCRow  = iGridOffsetY + iRow * iCuadroAlto;
//                 int iAuxCCol  = iGridOffsetX + iCol * iCuadroAncho;
//                 Coordenada cooAux = new Coordenada(iAuxCCol, iAuxCRow);
//                 cooCoordenadas.add(cooAux);
//             }
//         }

//         this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/CustomL.ttf"));
//         this.fonFuentel = this.fonFuentel.deriveFont(25F);

//         // setPreferredSize(new Dimension(floodGame.iWidth /2, floodGame.iHeight));
//         setPreferredSize(new Dimension(228, tarGame.iHeight));

//         this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/Score.ttf"));
//         this.fonFuentel = this.fonFuentel.deriveFont(40F);

//         // setPreferredSize(new Dimension(floodGame.iWidth /2, floodGame.iHeight));
//         setPreferredSize(new Dimension(228, floodGame.iHeight));

//         // Listeners: Teclado y Mouse
//         addKeyListener(this);
//     }
//     /**initvars
//      inicializa las variables
//      **/
//     public void initVars() {
//         //booleanas de botones
//         bBackMenu = false;
//         bHelp = false;
//         bPause = false;
//         bSound = true;
//         //indica el nivel
//         sNivel = Integer.toString(tarGame.iNivel);
//         iMouseXOffSet = (tarGame.iWidth) - 228;
//     }

//     /* crearImagenes
//         crea las imagenes base del side panel
//     */
//     public void crearImagenes() {
//         // Crear la imagen de fondo.
//         imaImagenLogo = Toolkit.getDefaultToolkit().getImage(this.getClass()
//                         .getResource("Images/sidePanel/logo.png"));

//         imaImagenPuntaje = Toolkit.getDefaultToolkit().getImage(this.getClass()
//                            .getResource("Images/sidePanel/puntaje.png"));

//         sNivel = "1";
//         imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
//                          .getResource("Images/sidePanel/nivel" + sNivel + ".png"));

//     }

//     /* creaBases
//         crea los objetos base del side panel, botones
//     */
//     public void creaBases() {

//         basBackMenu = new Base(640, 540, Toolkit.getDefaultToolkit()
//                                .getImage(this.getClass().getResource("Images/sidePanel/backAlMenu.png")));


//         basSound = new Base(640, 650, Toolkit.getDefaultToolkit()
//                             .getImage(this.getClass().getResource("Images/sidePanel/soundON.png")));


//         basPause = new Base(basSound.getX() + basSound.getAncho(), 650, Toolkit.getDefaultToolkit()
//                             .getImage(this.getClass().getResource("Images/sidePanel/pause.png")));

//         basHelp = new Base(basPause.getX() + basPause.getAncho(), 650, Toolkit.getDefaultToolkit()
//                            .getImage(this.getClass().getResource("Images/sidePanel/help.png")));

//     }

//     public void paintComponent(Graphics graGrafico) {
//         //super.paintComponent(graGrafico);

//         if (imaImagenLogo != null && imaImagenPuntaje != null &&
//                 imaImagenNivel != null) {
//             //pinta imagenes
//             graGrafico.drawImage(imaImagenLogo, 640, 50, 203, 70, this);
//             graGrafico.drawImage(imaImagenPuntaje, 640, 150, 193, 145, this);

//             Color colAux = new Color(255, 255, 255);
//             graGrafico.setColor(colAux);
//             graGrafico.setFont(fonFuentel);
//             graGrafico.drawString(Integer.toString(tarGame.iPuntos), 660, 250);





//             graGrafico.drawImage(imaImagenNivel, 640, 350, 173, 82, this);
//         }

//         if (!bBackMenu && basBackMenu != null &&
//                 basHelp != null && basPause != null && basSound != null) {

//             // Dibujar el objeto de menu
//             basBackMenu.paint(graGrafico, this);
//             basHelp.paint(graGrafico, this);
//             basPause.paint(graGrafico, this);
//             basSound.paint(graGrafico, this);
//         } else {
//             //Da un mensaje mientras se carga el dibujo
//             graGrafico.drawString("No se cargo la imagen..", 20, 20);
//         }
//     }

//     /**manejaSonido
//      * maneja el sonido del juego
//      */
//     public void manejaSonido() {
//         if (bSound) {
//             //cambio de imagen
//             basSound.setImagen(Toolkit.getDefaultToolkit()
//                                .getImage(this.getClass().getResource("Images/sidePanel/soundON.png")));
//             //AQUI PONER EL SOUND.PLAY()
//         } else {
//             //cambio de imagen
//             basSound.setImagen(Toolkit.getDefaultToolkit()
//                                .getImage(this.getClass().getResource("Images/sidePanel/soundOFF.png")));
//             //AQUI PONER EL SOUND.PAUSE()
//         }
//     }

//     public void cambioNivel() {
//         //indica el nivel
//         sNivel = Integer.toString(tarGame.iNivel);

//         //cambiar imagen a desplegar
//         imaImagenNivel = Toolkit.getDefaultToolkit().getImage(this.getClass()
//                          .getResource("Images/sidePanel/nivel" + sNivel + ".png"));
//     }

//     public void mouseClicked(MouseEvent mouEvent) {


//         //actualiza posiciones del mouse
//         iMouseX = mouEvent.getX() + iMouseXOffSet;
//         iMouseY = mouEvent.getY();

//         //checa si el jugador a presionado algun boton del panel
//         if (basHelp.intersects(iMouseX, iMouseY)) {
//             bHelp = true;//prende help
//             System.out.println("clicked help");
//             bBackMenu = false;//apaga las demás
//         }


//         else if ( basPause.intersects(iMouseX, iMouseY) ) {
//             System.out.println("clicked pause");
//             bPause = !bPause;//niega pause
//         } else if (basSound.intersects(iMouseX, iMouseY)) {
//             System.out.println("clicked sound");
//             bSound = !bSound;//niega sound
//             manejaSonido();
//         } else if (basBackMenu.intersects(iMouseX, iMouseY)) {

//             System.out.println("clicked menu");

//             //Segun yo SDN borrar, pues
//             //bBackMenu = true;
//             //bHelp = false;//apaga las demás
//             //Segun yo SDN borrar, pues

//             //SDN - Variables de Banner Menu
//             tarGame.bannerMenu.setPlay(false);
//             System.out.println("set play");
//             tarGame.bannerMenu.falseAll();
//             tarGame.bannerMenu.bPrincipal = true;
//             //SDN - Variables de Banner Menu


//         }

//     }

//     public void mousePressed(MouseEvent e) {
//     }

//     public void mouseReleased(MouseEvent e) {
//     }

//     public void mouseEntered(MouseEvent e) {
//     }

//     public void mouseExited(MouseEvent e) {
//     }


// }
