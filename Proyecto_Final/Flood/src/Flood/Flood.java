
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flood;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import javax.swing.KeyStroke;
import java.text.Normalizer;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 * Jframe, load and save game
 *
 *
 * @author Jose Humberto Garza Rosado
 * @date 2016
 * @version A00808689
 */
public class Flood extends JFrame implements Runnable, KeyListener, MouseListener {

    //Jframe Size

    /**
     *
     */
    public int iHeight;

    /**
     *
     */
    public int iWidth;

    // Objetos imagen
    private Image imaImagenFondo; // Imagen de fondo
    private Image imaImagenApplet; // Imagen a proyectar en Applet
    private Graphics graGraficaApplet; // Objeto grafico de la Imagen

    //Objeto base BackMenu
    private Base backMenu;

    //Sounds
    SoundClip souMove; //suena cuando se mueve el seleccionador
    SoundClip souEliminate; //suena cuando se elimina un cuadro
    SoundClip souWrong;  //suena cuando se equivoca al teclear
    SoundClip souLoop;

    // Variables de Teclado
    boolean bKeyPressed;

    //Variables de las posiciones del Mouse
    private int iMouseX;
    private int iMouseY;

    // Tablero

    /**
     *
     */
    protected Tablero tabTablero;

    //SidePanel Instance

    /**
     *
     */
    protected SidePanel side;

    //Banner Menu Instance
    BannerMenu bannerMenu;

    //Variables de score y nivel

    /**
     *
     */
    protected int iPuntos;

    /**
     *
     */
    protected int iNivel;

    /**
     *
     */
    protected int iModoJuego;

    /**
     *
     */
    protected int iRespondidas;

    // Variables de tiempo
    private long tiempoActual;
    private long tiempoInicial;
    private int iRandMin;
    private int iRandMax;
    private int iRand;
    private int iContadorCiclos;

    /**
     *
     */
    protected int iContRespuesta;

    // Categoria
    private String sCurCategoria;

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FontFormatException
     */
    public Flood() throws FileNotFoundException, IOException, FontFormatException {
        // Jframe Configuration
        iWidth = 900;
        iHeight = 768;

        // Crear la imagen de fondo.
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                         .getResource("Images/Fondo.png"));

        //inicializa la instancia de SidePanel
        this.side = new SidePanel(this);

        //inicializa la instancia de BannerMenu
        this.bannerMenu = new BannerMenu(this);

        //borderlayout para definir los paneles
        add(side, BorderLayout.EAST);

        // BannerMenu
        add(bannerMenu);

        //Sounds
        souMove = new SoundClip("/Flood/Sounds/click_tiny.wav");
        souEliminate = new SoundClip("Sounds/eliminateline.wav");
        souWrong = new SoundClip("Sounds/wrong.wav");
        souLoop = new SoundClip("Sounds/Loop.wav");
        souLoop.setLooping(true);
        souLoop.play(side.bSound);

        Dimension dimD = new Dimension(iWidth, iHeight);
        Container conC = this.getContentPane();
        conC.setPreferredSize(dimD);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Variables de score, nivel y categoria
        sCurCategoria = "/Flood/Files/Quimica.txt";
        iPuntos = 0;
        iNivel = iModoJuego = 1;
        side.cambioNivel();
        nuevoJuego(sCurCategoria);

        // Variables tiempo
        iRandMax = 50;
        iRandMin = 45;
        iContadorCiclos = 0;
        iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
        iContRespuesta = 0;

        // Variables de teclado
        bKeyPressed = false;

        // Listeners: Teclado y Mouse
        addKeyListener(this);
        addMouseListener(this);

        // Declarar thread principal
        Thread th = new Thread(this);

        // Iniciar el thread
        th.start();
    }

    /**
     *
     * @param sFileToOpen
     */
    public void IniciaMenu(String sFileToOpen) {
        //Variables de score y nivel
        sCurCategoria = sFileToOpen;
        iPuntos = 0;
        iNivel = iModoJuego = 1;
        side.cambioNivel();
        nuevoJuego(sFileToOpen);
    }

    /**
     *
     * @param sFileToOpen
     */
    public void nuevoJuego(String sFileToOpen) {
        // Iniciar un nuevo Tablero
        try {
            tabTablero = new Tablero(sFileToOpen);
        } catch (FontFormatException ex) {
            Logger.getLogger(Flood.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Flood.class.getName()).log(Level.SEVERE, null, ex);
        }
        iPuntos = 0;
        iRespondidas = 0;
        // MODO DE JUEGO
        /////////////MODO 1///////////////////
        /*
         * En este modo de juego, aparecen cuadros al azar y
         * se deben ir desapareciendo al responder correctamente
         */
        if (iModoJuego == 1) {
            iRandMax = 50;
            iRandMin = 20;
            tabTablero.creaCuadro();
            /////////////////////////////////////
        }
        /////////////MODO 2///////////////////
        /*
        * En este modo de juego, se trata de desaparecer n Puntos
         */
        if (iModoJuego == 2) {
            tabTablero.llenarGrid();
            /////////////////////////////////////
        }
        /////////////MODO 3///////////////////
        /*
        * En este modo de juego, se trata de evitar que se pasen los cuadros
        * de arriba
         */
        if (iModoJuego == 3) {
            iRandMax = 45;
            iRandMin = 10;
            tabTablero.setIndex(11);
            tabTablero.creaCuadroAbajo();
            /////////////////////////////////////
        }
        /////////////MODO 4///////////////////
        /*
         * En este modo se debe desbloquear el ultimo cuadro
         */
        if (iModoJuego == 4) {
            tabTablero.llenarGridCapas();
            /////////////////////////////////////
        }
        /////////////MODO 5///////////////////
        /*
         * En este modo se debe desbloquear la tache
         */
        if (iModoJuego == 5) {
            tabTablero.llenarGridTache();
            /////////////////////////////////////
        }
        /////////////MODO 6///////////////////
        /*
         * En este modo se debe desbloquear el ultimo cuadro
         */
        if (iModoJuego == 6) {
            iRandMax = 75;
            iRandMin = 50;
            tabTablero.llenarGrid();
            /////////////////////////////////////
        }
        /////////////////////////////////////
    }

    /**
     * main
     *
     * Main del juego principal
     *
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.awt.FontFormatException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, FontFormatException {
        Flood tarGame = new Flood();
    }

    /**
     * run
     *
     * Metodo para correr el juego
     *
     */
    @Override
    public void run() {
        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        // Mientras no sea el fin del juego
        while (true) {
            if (bannerMenu.getPlay() && !bannerMenu.getInstrucciones()) {//boton de play en el menu
                if (!side.bPause) { //no esta en pausa
                    actualiza();//solo actualiza cuando no hay pausa
                }
                side.iContBannerLevel--;//contador de banner nivel up
                add(side);
                remove(bannerMenu);
            } else {//hacer que regrese al menu
                add(bannerMenu);
                remove(side);
                bannerMenu.setPlay(false);

            }
            checaColision();
            repaint();
            try {
                // El hilo del juego se duerme: 10 default.
                Thread.sleep(25);
            } catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego "
                                   + iexError.toString());
            }
        }
    }

    /**
     * actualiza
     *
     * Metodo que actualiza la posicion de principal y de los secundarios
     *
     */
    public void actualiza() {
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        iContadorCiclos++;

        /////////////MODO 1///////////////////
        if (iModoJuego == 1) {
            if (iContadorCiclos >= iRand || tabTablero.noHayDisponibles()) {
                iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
                iContadorCiclos = 0;
                tabTablero.creaCuadro();
                if (tabTablero.estaLleno()) {
                    side.perdioNivel();
                    nuevoJuego(sCurCategoria);
                }
            }
        }
        /////////////MODO 2///////////////////
        if (iModoJuego == 2) {
            //tabTablero.creaCuadro();
            if (tabTablero.noHayDisponibles()) {
                nuevoJuego(sCurCategoria);
                side.perdioNivel();
            }
        }
        /////////////MODO 3///////////////////
        if (iModoJuego == 3) {
            if (iContadorCiclos >= iRand || tabTablero.noHayDisponibles()) {
                iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
                iContadorCiclos = 0;
                tabTablero.creaCuadroAbajo();
                if (tabTablero.estaLleno()) {
                    nuevoJuego(sCurCategoria);
                    side.perdioNivel();
                }
            }
        }
        ////////////////////////////////////////////
        //...
        /////////////MODO 6///////////////////
        if (iModoJuego == 6) {
            if (iContadorCiclos >= iRand || tabTablero.noHayDisponibles()) {
                iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
                iContadorCiclos = 0;
                tabTablero.bloqueaCuadro();
                if (tabTablero.isBloqued()) {
                    nuevoJuego(sCurCategoria);
                    side.perdioNivel();
                    side.perdioNivel();
                }
            }
        }

        ////////////////////////////////////////////
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        // Actualizar el selector
        tabTablero.actualizarSelector();
        // Actualizar la Pregunta Actual
        tabTablero.actualizarPregunta();
    }

    /**
     * checaColision
     *
     * Checar la Colision
     */
    public void checaColision() {

    }

    /**
     * paint
     *
     * Metodo para pintar el JFrame
     *
     * @param graGrafico es el <code>Grafico</code> en el que se pinta
     *
     */
    @Override
    public void paint(Graphics graGrafico) {
        // Inicializan el DoubleBuffer
        // Si quitamos el if funciona con el resize
        if (imaImagenApplet == null) {
            imaImagenApplet = createImage(this.getSize().width,
                                          this.getSize().height);
            graGraficaApplet = imaImagenApplet.getGraphics();
        }
        // Actualiza el Foreground.
        graGraficaApplet.setColor(getForeground());
        paintCustom(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage(imaImagenApplet, 0, 0, this);
    }

    /**
     * paint
     *
     * Metodo sobrescrito de la clase <code>Applet</code>, heredado de la clase
     * <P>
     * Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param graDibujo objeto de <code>Graphics</code> usado para dibujar.
     *
     */
    public void paintCustom(Graphics graDibujo) {
        // si la imagen ya se cargo *No olvidar checar el secundario
        if (imaImagenFondo != null && tabTablero != null) {

            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaImagenFondo, 0, 0, iWidth, iHeight, this);

            if (bannerMenu.getPlay()) {
                //se pinta el menu
                paintCustomFlood(graDibujo);
            } else {

                //se pinta el juego cuando se le presiona play
                paintCustomMenu(graDibujo);
            }

        } // Si no se ha cargado se dibuja un mensaje
        else {
            //Da un mensaje mientras se carga el dibujo
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    /**
     *
     * @param graDibujo
     */
    public void paintCustomFlood(Graphics graDibujo) {
        //paintComponent de side Panel
        side.paintComponent(graDibujo);

        if (!side.bPause) { //solo pinta cuando no hay un banner
            // Pintar el tablero
            tabTablero.paint(graDibujo, this);
        }
    }

    /**
     *
     * @param graDibujo
     */
    public void paintCustomMenu(Graphics graDibujo) {
        bannerMenu.paintComponent(graDibujo);

    }

    /**
     * keyTyped
     *
     * @param keyEvent es el objeto de <code>keyTyped</code> del teclado.
     *
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * keyPressed
     *
     *
     * @param keyEvent es el objeto de <code>keyTyped</code> del teclado.
     *
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if (bannerMenu.getPlay() && !side.bPause) {

            /* Revisar que tecla se presiono y cambiar la posicion*/
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                tabTablero.pressedEnter(iModoJuego);
                souMove.play(side.bSound);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                tabTablero.pressedRight(iModoJuego);
                souMove.play(side.bSound);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                tabTablero.pressedLeft(iModoJuego);
                souMove.play(side.bSound);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                tabTablero.pressedUp(iModoJuego);
                souMove.play(side.bSound);
            }

            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                tabTablero.pressedDown(iModoJuego);
                souMove.play(side.bSound);
            } else {//es lo de escribir la respuesta
                char cAux = keyEvent.getKeyChar();
                System.out.println(cAux);
                int iResult = tabTablero.pressedKey(cAux, iModoJuego);
                if (iResult > 0) {//contesto bien
                    souEliminate.play(side.bSound);
                    tabTablero.disRespuesta.iContRespuesta = 10;//para que se pueda ver la respuesta
                    iPuntos += iResult;
                    iRespondidas++;
                }

                if (iResult > -400 && iResult < 0) {
                    iPuntos += iResult;
                    if (iPuntos < 0) {
                        iPuntos = 0;
                    }
                    souWrong.play(side.bSound);
                }
                if (iModoJuego == 1) {
                    if (iPuntos > 100) {
                        iNivel++;
                        side.cambioNivel();
                        iModoJuego++;
                        nuevoJuego(sCurCategoria);
                    }
                } else if (iModoJuego == 2) {
                    if (iRespondidas >= 20) {
                        iNivel++;
                        side.cambioNivel();
                        iModoJuego++;
                        nuevoJuego(sCurCategoria);
                    }
                } else if (iModoJuego == 3) {
                    if (iPuntos > 100) {
                        iNivel++;
                        side.cambioNivel();
                        iModoJuego++;
                        nuevoJuego(sCurCategoria);
                    }
                } else if (iModoJuego == 4) {
                    if (iResult == -400) {
                        iNivel++;
                        side.cambioNivel();
                        iModoJuego++;
                        nuevoJuego(sCurCategoria);
                    }
                } else if (iModoJuego == 5) {
                    if (iResult == -500) {
                        iNivel++;
                        side.cambioNivel();
                        iModoJuego++;
                        nuevoJuego(sCurCategoria);
                    }
                } else if (iModoJuego == 6) {
                    if (iResult == -600) {
                        //iNivel = iModoJuego = 1;
                        iNivel++;
                        side.cambioNivel();
                        nuevoJuego(sCurCategoria);
                    }
                }
            }
        }
    }

    /**
     * keyReleased
     *
     *
     * @param keyEvent es el objeto de <code>keyTyped</code> del teclado.
     *
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        souMove.stop();

        if ((side.bLevelDown || side.bLevelUp) && side.iContBannerLevel <= 0 && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) { //esta el banner de levelup en pantalla
            //leer input para continuar con juego
            //quitas pausa, y apagas las demás booleanas

            side.bPause = false;
            side.bBanner = false;
            side.bLevelUp = false;
            side.bLevelDown = false;
        }
        if (side.bWonGame && side.iContBannerLevel <= 0 && keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            //apagar booleanas
            side.bPause = false;
            side.bBanner = false;
            side.bLevelUp = false;
            side.bLevelUp = false;

            //regresar al menu
            bannerMenu.setPlay(false);
            System.out.println("set play");
            bannerMenu.falseAll();
            bannerMenu.bPrincipal = true;
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {
        if (side.bExit) {//esta en el banner de exit
            if (side.basYesSalir.intersects(iMouseX, iMouseY)) {//salir del juego
                side.bExit = false;//quita el banner
                side.bBanner = false;//significa que no hay banner
                side.bPause = false;//quitar pausa
                //regresar al menu
                bannerMenu.setPlay(false);
                System.out.println("set play");
                bannerMenu.falseAll();
                bannerMenu.bPrincipal = true;
            } else if (side.basNoPlay.intersects(iMouseX, iMouseY)) {//seguir jugando
                side.bExit = false;//quitar banner
                side.bPause = false;//quitar pausa
                side.bBanner = false;//significa que no hay banner
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouEvent) {
        //actualiza posiciones del mouse
        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY();

        if (iPuntos <= 0) {
            souWrong.play(side.bSound);
        } else if (tabTablero.disRespuesta.basHint.intersects(iMouseX, iMouseY) && !side.bPause) {
            System.out.println("Hint was clicked");
            int iMenosPista = tabTablero.getHint();
            if (iMenosPista == -100) {
                souWrong.play(side.bSound);
            } else {
                iPuntos += iMenosPista;
                if (iPuntos < 0) {
                    iPuntos = 0;
                }
            }
        }
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
