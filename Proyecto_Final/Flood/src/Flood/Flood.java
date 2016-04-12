/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flood;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.awt.Font;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * Jframe, load and save game
 *
 *
 * @author Jose Humberto Garza Rosado
 * @date 2016
 * @version A00808689
 */
public class Flood extends JFrame implements Runnable, MouseListener, KeyListener {

    //Jframe Size
    private int iHeight;
    private int iWidth;
    private int iMargenGrid;
    private int iGridAncho;
    private int iGridAlto;

    // Posicionamiento
    private int[] arrGridX = new int[4];
    private int[] arrGridY = new int[4];
    private boolean[][] matGrid = new boolean[4][4];

    // Objetos imagen
    private Image imaImagenFondo; // Imagen de fondo
    private Image imaImagenApplet; // Imagen a proyectar en Applet
    private Graphics graGraficaApplet; // Objeto grafico de la Imagen

    // Objeto Base Principal
    private Base basSelector; // Objeto Selector
    private int iIncrementoX;
    private int iIncrementoY;

    // Objetos Base Cuadro
    private LinkedList<Base> lklCuadros; // ListaEncadenada de Cuadros
    private LinkedList<Integer> lklDisponibles;
    // Variables de Teclado
    boolean bKeyPressed;

    // Variables de tiempo
    private long tiempoActual;
    private long tiempoInicial;
    private int iRandMin;
    private int iRandMax;
    private int iRand;
    private int iContadorCiclos;
    public Flood() {

        // Jframe Configuration
        iWidth = 800;
        iHeight = 600;

        // Variables de la matriz central
        iGridAncho = 140;
        iGridAlto = 115;
        iMargenGrid = 20;

        // Llenar los arreglos de posiciones de la matriz central
        for (boolean[] row : matGrid) {
            Arrays.fill(row, false);
        }
        for (int iX = 0; iX < 4; iX++) {
            arrGridX[iX] = iMargenGrid + iX * iGridAncho;
            arrGridY[iX] = iMargenGrid + iX * iGridAlto;
        }

        Dimension dimD = new Dimension(iWidth, iHeight);
        Container conC = this.getContentPane();
        conC.setPreferredSize(dimD);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear la imagen de fondo.
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                         .getResource("Images/Fondo.png"));

        // Crear el objeto selector
        basSelector = new Base(iMargenGrid, iMargenGrid, Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/Selector.png")));
        iIncrementoX = 0;
        iIncrementoY = 0;

        // Variables tiempo
        iRandMax = 75;
        iRandMin = 70;
        iContadorCiclos = 0;
        iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);

        // Lista de Cuadros
        lklCuadros = new LinkedList<Base>();
        lklDisponibles = new LinkedList<Integer>();
        for (int iX = 0; iX < 16; iX ++) {
            lklDisponibles.add(iX);
        }

        creaCuadro();

        // Variables de Teclado
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
     * main
     *
     * Main del juego principal
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            actualiza();
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

        iContadorCiclos ++;
        if (iContadorCiclos >= iRand) {
            iRand = (int) (Math.random() * (iRandMin + 1) + iRandMax);
            iContadorCiclos = 0;
            creaCuadro();
            //Guarda el tiempo actual
            tiempoActual += tiempoTranscurrido;
           
        }


        // Si se presiono una tecla de movimiento, actualizar al principal
        if (bKeyPressed) {
            // Posicionar al principal en X segun la tecla presionada
            basSelector.setX(arrGridX[iIncrementoX]);
            basSelector.setY(arrGridY[iIncrementoY]);
        }
    }
    /**
     * actualiza
     *
     * Metodo que actualiza la posicion de principal y de los secundarios
     *
     */
    public void creaCuadro() {
        if (lklDisponibles.size() > 0) {

            int iRandPicker = (int) (Math.random() * lklDisponibles.size());
            int iAux =  lklDisponibles.remove(iRandPicker);

            int iRow = iAux / 4;
            int iCol = iAux - iRow * 4;

            Base basAux = new Base(arrGridX[iCol], arrGridY[iRow], Toolkit.getDefaultToolkit()
                                   .getImage(this.getClass().getResource("Images/Cuadro.png")));
            
            lklCuadros.add(basAux);
        }

        System.out.println(lklDisponibles.size());

        /*
        boolean bAux = true;
        int iRandCol = 0;
        int iRandRow = 0;

        while (bAux) {
            iRandCol = (int) (Math.random() * 4);
            iRandRow = (int) (Math.random() * 4);

            bAux = matGrid[iRandCol][iRandRow];
        }

        Base basAux = new Base(arrGridX[iRandCol], arrGridY[iRandRow], Toolkit.getDefaultToolkit()
                               .getImage(this.getClass().getResource("Images/Cuadro.png")));

        matGrid[iRandCol][iRandRow] = true;

        lklCuadros.add(basAux);
        */
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
        if (imaImagenFondo != null && basSelector != null) {

            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaImagenFondo, 0, 0, iWidth, iHeight, this);


            // Dibuja los objetos Cuadro
            for (Base basAux : lklCuadros) {
                basAux.paint(graDibujo, this);
            }


            // Dibujar el selector
            basSelector.paint(graDibujo, this);

        } // Si no se ha cargado se dibuja un mensaje
        else {
            //Da un mensaje mientras se carga el dibujo
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            int iAux = iIncrementoX + 1;
            iAux = iAux % 4;
            iIncrementoX = iAux;
            bKeyPressed = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            int iAux = iIncrementoX - 1;
            if (iAux < 0) {
                iAux = 3;
            }
            iAux = iAux % 4;
            iIncrementoX = iAux;
            bKeyPressed = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            int iAux = iIncrementoY - 1;
            if (iAux < 0) {
                iAux = 3;
            }
            iAux = iAux % 4;
            iIncrementoY = iAux;
            bKeyPressed = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            int iAux = iIncrementoY + 1;
            iAux = iAux % 4;
            iIncrementoY = iAux;
            bKeyPressed = true;
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
        bKeyPressed = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
