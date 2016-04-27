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
public class Flood extends JFrame implements Runnable, KeyListener {

    //Jframe Size
    public int iHeight;
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

    // Variables de Teclado
    boolean bKeyPressed;

    //Variables de las posiciones del Mouse
    private int iMouseX;
    private int iMouseY;

    // Tablero
    protected Tablero tabTablero;

    //SidePanel Instance
    protected SidePanel side;

    //Banner Menu Instance
    BannerMenu bannerMenu;

    //Variables de score y nivel
    protected int iPuntos;
    protected int iNivel;
    protected int iModoJuego;

    // Variables de tiempo
    private long tiempoActual;
    private long tiempoInicial;
    private int iRandMin;
    private int iRandMax;
    private int iRand;
    private int iContadorCiclos;

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
        souMove = new SoundClip("Sounds/click_tiny.wav");
        souEliminate = new SoundClip("Sounds/eliminateline.wav");

        Dimension dimD = new Dimension(iWidth, iHeight);
        Container conC = this.getContentPane();
        conC.setPreferredSize(dimD);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Variables de score y nivel
        iPuntos = 0;
        iNivel = 1;

        // Variables tiempo
        iRandMax = 75;
        iRandMin = 70;
        iContadorCiclos = 0;
        iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);

        // Iniciar un nuevo Tablero
        tabTablero = new Tablero("./src/Flood/Files/Quimica.txt");

        // Definir el primer modo de juego
        iModoJuego = 3;

        // MODO DE JUEGO
        /////////////MODO 1///////////////////
        /*
         * En este modo de juego, aparecen cuadros al azar y
         * se deben ir desapareciendo al responder correctamente
         */
        if (iModoJuego == 1) {
            /////////////MODO 1///////////////////
            tabTablero.creaCuadro();
            /////////////////////////////////////
        }
        /////////////MODO 2///////////////////
        /*
        * En este modo de juego, se trata de desaparecer n Puntos
         */
        if (iModoJuego == 2) {
            /////////////MODO 2///////////////////
            tabTablero.llenarGrid();
            /////////////////////////////////////
        }
        /////////////MODO 3///////////////////
        /*
        * En este modo de juego, se trata de evitar que se pasen los cuadros
        * de arriba
         */
        if (iModoJuego == 3) {
            /////////////MODO 2///////////////////
            tabTablero.setIndex(11);
            tabTablero.creaCuadroAbajo();
            /////////////////////////////////////
        }
        /////////////////////////////////////

        // Variables de teclado
        bKeyPressed = false;

        // Listeners: Teclado y Mouse
        addKeyListener(this);

        // Declarar thread principal
        Thread th = new Thread(this);

        // Iniciar el thread
        th.start();
    }

    /**
     * main
     *
     * Main del juego principal
     *
     * @param args the command line arguments
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
                actualiza();
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
        if (iContadorCiclos >= iRand) {
            iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
            iContadorCiclos = 0;
            /////////////MODO 1///////////////////
            if (iModoJuego == 1) {
                tabTablero.creaCuadro();
            }
            /////////////MODO 2///////////////////
            if (iModoJuego == 2) {
                tabTablero.creaCuadro();
            }
            /////////////MODO 3///////////////////
            if (iModoJuego == 3) {
                tabTablero.creaCuadroAbajo();
            }
            ////////////////////////////////////////////

            //Guarda el tiempo actual
            tiempoActual += tiempoTranscurrido;
        }

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

    public void paintCustomFlood(Graphics graDibujo) {
        //paintComponent de side Panel
        side.paintComponent(graDibujo);

        // Pintar el tablero
        tabTablero.paint(graDibujo, this);
    }

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
        /* Revisar que tecla se presiono y cambiar la posicion*/
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            tabTablero.pressedEnter(iModoJuego);
            souMove.play(side.bSound);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            tabTablero.pressedRight();
            souMove.play(side.bSound);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            tabTablero.pressedLeft();
            souMove.play(side.bSound);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            if (iModoJuego == 1) {
                tabTablero.pressedUp();
            } else if (iModoJuego == 2) {
                tabTablero.pressedUp();
            } else if (iModoJuego == 3) {

            }
            souMove.play(side.bSound);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            if (iModoJuego == 1) {
                tabTablero.pressedDown();
            } else if (iModoJuego == 2) {
                tabTablero.pressedDown();
            } else if (iModoJuego == 3) {

            }
            souMove.play(side.bSound);
        } else {
            char cAux = keyEvent.getKeyChar();
            int iResult = tabTablero.pressedKey(cAux, iModoJuego);
            if (iResult > 0) {
                souEliminate.play(side.bSound);
            }
            iPuntos += iResult;


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
    }

}
