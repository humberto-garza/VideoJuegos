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
import javax.swing.KeyStroke;

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

    public int iHeight;
    public int iWidth;

    // Variables Grid
    private int iMargenGrid;

    private int iCuadroAncho;
    private int iCuadroAlto;
    private int iCuadroMargen;
    private int iGridOffsetX;
    private int iGridOffsetY;
    private int iCasillas;
    private int iGridRows;
    private int iGridCols;
    private int iIndexActual;

    // Variables Cuadro Pregunta
    private int iCuadroQX;
    private int iCuadroQY;
    private DisplayPregunta disPregunta;

    // Variables Cuadro Respuesta
    private int iDisplRX;
    private int iDisplRY;
    private DisplayRespuesta disRespuesta;

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

    //Objeto base BackMenu
    private Base backMenu;

    // Listas Encadenadas
    private LinkedList<Base> lklCuadros; // ListaEncadenada de Cuadros
    private LinkedList<Integer> lklDisponibles;
    private LinkedList<Cuadro> lklCuadrosBase;
    private LinkedList<Color> lklColores;
    private LinkedList<Pregunta> lklPreguntas;

    // Variables de Teclado
    boolean bKeyPressed;

    //Variables de las posiciones del Mouse
    private int iMouseX;
    private int iMouseY;

    //SidePanel Instance
    private SidePanel side;

    // Variables de tiempo
    private long tiempoActual;
    private long tiempoInicial;
    private int iRandMin;
    private int iRandMax;
    private int iRand;
    private int iContadorCiclos;

    // Variables de archivo
    private String nombreArchivo;    //Nombre del archivo.

    public Flood() throws FileNotFoundException, IOException {

        // Jframe Configuration
        iWidth = 900;
        iHeight = 768;

        // Variables de la matriz central
        iCuadroAncho = 140;
        iCuadroAlto = 108;
        iCuadroMargen = 4;
        iGridOffsetX = 20;
        iGridOffsetY = 110;
        iGridRows = 5;
        iGridCols = 5;
        iCasillas = iGridRows * iGridCols;
        iIndexActual = 0;

        // Variables Cuadro Pregunta
        iCuadroQX = 25;
        iCuadroQY = 60;
        disPregunta = new DisplayPregunta(iCuadroQX, iCuadroQY, "", "");

        // Variables Cuadro Respuesta
        iDisplRX = 25;
        iDisplRY = 735;
        disRespuesta = new DisplayRespuesta(iDisplRX, iDisplRY, "");

        arrGridX = new int[iGridCols];
        arrGridY = new int[iGridRows];

        //inicializa la instancia de SidePanel
        this.side = new SidePanel(this);


        // Llenar los arreglos de posiciones de la matriz central
        for (boolean[] row : matGrid) {
            Arrays.fill(row, false);
        }

        for (int iX = 0; iX < iGridCols; iX++) {
            arrGridX[iX] = iGridOffsetX + iX * iCuadroAncho;
        }

        for (int iX = 0; iX < iGridRows; iX++) {
            arrGridY[iX] = iGridOffsetY + iX * iCuadroAlto;
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
        basSelector = new Base(iGridOffsetX, iGridOffsetY, Toolkit.getDefaultToolkit()
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
        lklCuadrosBase = new LinkedList<Cuadro>();

        lklDisponibles = new LinkedList<Integer>();
        for (int iX = 0; iX < iCasillas; iX++) {
            lklDisponibles.add(iX);
            int iRow = iX / iGridCols;
            int iCol = iX - iRow * iGridCols;
            Color colAux = new Color(255, 255, 255);
            Cuadro cuaAux = new Cuadro(
                arrGridX[iCol] + iCuadroMargen,
                arrGridY[iRow] + iCuadroMargen,
                Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/Cuadro.png")),
                iX,
                false,
                colAux,
                iCuadroAncho - iCuadroMargen * 2,
                iCuadroAlto - iCuadroMargen * 2,
                0);

            lklCuadrosBase.add(cuaAux);
        }
        // Cargar Colores
        lklColores = new LinkedList<Color>();
        cargaColores();

        // Cargar Preguntas
        lklPreguntas = new LinkedList<Pregunta>();
        cargaPreguntas("Quimica");

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
    public static void main(String[] args) throws FileNotFoundException, IOException {
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

    public void paintPanels() {
        side.repaint();
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

        // Actualizar la Pregunta Actual
        Cuadro cuaAux = lklCuadrosBase.get(iIndexActual);
        if (cuaAux.isActive()) {
            int iPregIndex = cuaAux.getPregunta();
            String sPreguntaActual = lklPreguntas.get(iPregIndex).getPregunta();
            disPregunta.setPregunta(sPreguntaActual);
        } else {
            disPregunta.setPregunta("");
        }

    }

    public void cargaColores() throws FileNotFoundException, IOException {
        lklColores.clear();

        nombreArchivo = "./src/Flood/Files/Paleta.txt";
        BufferedReader fileIn;

        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!");
            File puntos = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }

        String[] arrColores;
        String sLine;
        while ((sLine = fileIn.readLine()) != null) {
            arrColores = sLine.split(",");
            int iR = Integer.parseInt(arrColores[0]);
            int iG = Integer.parseInt(arrColores[1]);
            int iB = Integer.parseInt(arrColores[2]);
            Color colAux = new Color(iR, iG, iB);
            lklColores.add(colAux);
        }
        System.out.print("Se Cargaron Colores: ");
        System.out.println(lklColores.size());

    }

    public void cargaPreguntas(String sCategoria) throws FileNotFoundException, IOException {
        lklPreguntas.clear();

        nombreArchivo = "./src/Flood/Files/";
        nombreArchivo += sCategoria + ".txt";

        BufferedReader fileIn;

        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!");
            File puntos = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }

        String[] arrPreguntas;
        String sLine;
        sLine = fileIn.readLine();
        disPregunta.setPreguntaBase(sLine);

        while ((sLine = fileIn.readLine()) != null) {
            arrPreguntas = sLine.split(",");
            String sPreg = arrPreguntas[0];
            String sResp = arrPreguntas[1];

            Pregunta preAux = new Pregunta(sPreg, sResp);
            lklPreguntas.add(preAux);
        }
        System.out.print("Se Cargaron Preguntas: ");
        System.out.println(lklPreguntas.size());

    }

    /**
     * actualiza
     *
     * Metodo que actualiza la posicion de principal y de los secundarios
     *
     */
    public void creaCuadro() {
        if (lklDisponibles.size() > 0) {

            // Seleccionar al azar un lugar disponible
            int iRandPicker = (int) (Math.random() * lklDisponibles.size());

            // Remover el seleccionado de la lista
            int iAux = lklDisponibles.remove(iRandPicker);

            Cuadro cuaAux = lklCuadrosBase.get(iAux);

            // Seleccionar al azar un color nuevo
            iRandPicker = (int) (Math.random() * lklColores.size());
            Color colAux = lklColores.get(iRandPicker);
            cuaAux.setColor(colAux);

            // Seleccionar al azar una pregunta
            iRandPicker = (int) (Math.random() * lklPreguntas.size());
            cuaAux.setPregunta(iRandPicker);

            // Activar
            cuaAux.setActive(true);
        }

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

            // Dibuja los objetos Cuadro
            for (Cuadro cuaAux : lklCuadrosBase) {
                if (cuaAux.isActive()) {
                    cuaAux.paint(graDibujo, this, lklPreguntas);
                }
            }

            // Dibujar el selector
            basSelector.paint(graDibujo, this);

            //paintComponent de side Panel
            side.paintComponent(graDibujo);

            // Escribir el cuadro de pregunta
            disPregunta.paint(graDibujo, this);
            // Escribir el cuadro de pregunta
            disRespuesta.paint(graDibujo, this);

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
            iAux = iAux % iGridCols;
            iIncrementoX = iAux;
            bKeyPressed = true;
            iIndexActual = iIncrementoY * iGridCols + iIncrementoX;
            disRespuesta.setRespuesta("");
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            int iAux = iIncrementoX - 1;
            if (iAux < 0) {
                iAux = iGridCols - 1;
            }
            iAux = iAux % iGridCols;
            iIncrementoX = iAux;
            bKeyPressed = true;
            iIndexActual = iIncrementoY * iGridCols + iIncrementoX;
            disRespuesta.setRespuesta("");
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            int iAux = iIncrementoY - 1;
            if (iAux < 0) {
                iAux = iGridRows - 1;
            }
            iAux = iAux % iGridRows;
            iIncrementoY = iAux;
            bKeyPressed = true;
            iIndexActual = iIncrementoY * iGridCols + iIncrementoX;
            disRespuesta.setRespuesta("");
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            int iAux = iIncrementoY + 1;
            iAux = iAux % iGridRows;
            iIncrementoY = iAux;
            bKeyPressed = true;
            iIndexActual = iIncrementoY * iGridCols + iIncrementoX;
            disRespuesta.setRespuesta("");
        } else {
            char cAux = keyEvent.getKeyChar();
            if (Character.isDigit(cAux) || Character.isLetter(cAux)) {
                System.out.println(keyEvent.getKeyChar());

                // Actualizar la Pregunta Actual
                Cuadro cuaAux = lklCuadrosBase.get(iIndexActual);
                // Si el cuadro esta activo
                if (cuaAux.isActive()) {
                    // Obtener el index de la pregunta
                    int iPregIndex = cuaAux.getPregunta();
                    String sResEsperada = lklPreguntas.get(iPregIndex).getRespuesta();
                    String sResTotal = disRespuesta.getRespuesta();

                    // Comparar la respuesta esperada contra lo que se tiene
                    int iCharIndex = disRespuesta.getSize();
                    char cResEsperada = sResEsperada.charAt(iCharIndex);

                    if (Character.toLowerCase(cResEsperada) == Character.toLowerCase(cAux)) {
                        String sActualizar = disRespuesta.getRespuesta() + sResEsperada.charAt(iCharIndex);
                        disRespuesta.setRespuesta(sActualizar);

                        // Si la respuesta esta correcta
                        if (disRespuesta.getSize() == sResEsperada.length()) {
                            // Dar de baja el cuadro
                            cuaAux.setActive(false);
                            lklDisponibles.add(iIndexActual);
                            disRespuesta.setRespuesta("");
                        }
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
        bKeyPressed = false;
    }


    public void mouseClicked(MouseEvent mouEvent) {
        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY();


        if (side.basBackMenu.intersects(iMouseX, iMouseY)) {//seleciono play
            //dispose menu, iniciar juego

            this.dispose();
            Menu menu = new Menu();
        }
    }

    @Override
    public void mousePressed(MouseEvent e
                            ) {
    }

    @Override
    public void mouseReleased(MouseEvent e
                             ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
                            ) {
    }

    @Override
    public void mouseExited(MouseEvent e
                           ) {
    }
}
