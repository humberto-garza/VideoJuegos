/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea62;

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

/**
 * Jframe, Contra-Atacando
 *
 *
 * @author Jose Humberto Garza Rosado
 * @date 17/2/2016
 * @version A00808689
 */

public class Tarea62  extends JFrame implements Runnable, MouseListener, KeyListener {
    //Jframe Size
    private int iHeight;
    private int iWidth;

    // Objetos imagen
    private Image imaImagenFondo; // Imagen de fondo

    // Banners tipo Base
    private Base basBoard;
    private Base basPause;
    private Base basLives;
   
    // Objetos tipo Principal
    private int iDirPrinX;
    private int iDirPrinY;
    private int iVelocidadPrincipal; // La velocidad del principal
    private Principal priPrincipal;

    // Objetos  secundarios
    private LinkedList< Bala> lklBalas; // ListaEncadenada de *Balas
    private LinkedList< Foe>  lklFoes;  // ListaEncadenada de *Pokebolas
    private int iVelocidadBala;     // La velocidad del principal
    private int iVelocidadFoe;      // La velocidad del principal

    // Clips de Audio
    private SoundClip aChump; // Sonido de Mordisco
    private SoundClip aSplat; // Sonido Pierde una vida
    private SoundClip aShoot; // Sonido Colision Bala
    private SoundClip aHurt; // Sonido Colision con Principal

    // Vida y Puntaje
    private int iVidasRestantes; // Contador de Vidas
    private int iPuntos; // Contador de puntos totales
    private int iColisionesMalas;  // Contador de Colisiones con malos

    // Dinamica Juego
    private boolean bGameOver;
    private boolean bPause;
    boolean bKeyPressed; // Condicion de tecla presionada
    
    /* objetos para manejar el buffer del Applet y 
    que la imagen no parpadee */
    private Image imaImagenApplet; // Imagen a proyectar en Applet 
    private Graphics graGraficaApplet; // Objeto grafico de la Imagen

    public Tarea62() {

        //Jframe Configuration
        iWidth = 800;
        iHeight = 500;
        Dimension d = new Dimension(iWidth,iHeight);
        Container c = this.getContentPane();
        c.setPreferredSize(d);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creo la imagen de fondo.
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Fondo.png"));
        
        // Banners
        basBoard = new Base(0, 0, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/Banner.png")));
        basPause = new Base(0, 0, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/Pause.gif")));
        basLives = new Base(337, 8, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/Life.png")));

        // Clips de Audio
        aChump = new SoundClip("Audio/Chump.wav");
        aSplat = new SoundClip("Audio/Splat.wav");
        aShoot = new SoundClip("Audio/Shoot.wav");
        aHurt  = new SoundClip("Audio/Hurt.wav");

        // Objeto Principal
        priPrincipal = new Principal(0, 0, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/S_Abre.gif")),1);
        
        // Listas encadenada de personajes secundarios
        lklBalas = new LinkedList< Bala>();
        lklFoes = new LinkedList< Foe>();

        // Hacer un set de variables para iniciar el juego
        restart();

        // Listeners: Teclado y Mouse
        addKeyListener(this);
        addMouseListener(this);
        
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * restart
     * 
     */
    public void restart() {
        // Contadores
        iVidasRestantes     = 5; 
        iPuntos             = 0; 
        iColisionesMalas    = 0;

        // Boleanos
        bKeyPressed = false;  
        bGameOver   = false;
        bPause      = false;

        // Velocidad de personajes
        iVelocidadPrincipal = 5; 
        iVelocidadBala      = 5;
        iVelocidadFoe       = 1;

        // Resetear las velocidades
        priPrincipal.setVelocidad(iVelocidadPrincipal);

        // Resetear las listas
        lklFoes.clear();
        lklBalas.clear();

        // Movimiento del Principal
        iDirPrinX           = 0;
        iDirPrinY           = 0;

        // Generar una coleccion (al azar de 10 a 15) *Enemigos
        int iRandomFoes = (int) (Math.random() * 6 + 10);
        generaFoes(iRandomFoes);

        // Reposicionar los objetos principal y secundarios
        reposiciona();
    }


    /**
     * generaBala
     * Metodo para crear una bala
     * 
     */
    public void generaBala(int iDireccionX, int iDireccionY) {
        
        Bala balAux = new Bala( priPrincipal.getX()+priPrincipal.getAncho()/2, priPrincipal.getY()+10, Toolkit.getDefaultToolkit()
                    .getImage(this.getClass().getResource("Images/Bala.png")), 
                    iDireccionX, iDireccionY, iVelocidadBala);

        balAux.setX(balAux.getX()-balAux.getAncho()/2);
        lklBalas.add(balAux);
    }

    /**
     * generaFoes
     * 
     */
    public void generaFoes(int iTotalFoes) {
        
        int iTotalFollows = (int)(Math.ceil(iTotalFoes*0.1));
        iTotalFoes -= iTotalFollows;

        // Crear un Total de (iTotalFollows) que seguiran al principal
        for (int iI = 0; iI < iTotalFollows; iI++) {
            Foe foeAux = new Foe(0, 0, Toolkit.getDefaultToolkit()
                        .getImage(this.getClass().getResource("Images/Pokeball.png")), iVelocidadFoe, true);
            lklFoes.add(foeAux);
        }

        // Creo la lista de objetos secundarios *Sushis[10]
        for (int iI = 0; iI < iTotalFoes; iI++) {
            Foe foeAux = new Foe(0, 0, Toolkit.getDefaultToolkit()
                        .getImage(this.getClass().getResource("Images/Pokeball.png")), iVelocidadFoe, false);
            lklFoes.add(foeAux);
        }
    }

    /**
     * reposiciona
     *
     * Reposiciona a los objetos base principal y los secundarios Posiciona al
     * objeto principal en la mitad del applet inicialmente Posiciona los buenos
     * del lado Izquierdo (por fuera) Posiciona los malos del lado derecho (por
     * fuera)
     */
    public void reposiciona() {

        // Posicionar a Snorlax en el centro del applet
        priPrincipal.setX((iWidth / 2) - (priPrincipal.getAncho() / 2));
        priPrincipal.setY( iHeight - priPrincipal.getAlto());

        // Iterador para posicionar los secundarios *Malos
        for (Foe foeAux: lklFoes) {
            //Posicionar objeto en la parte superior negativa en Y
            foeAux.posiciona(iWidth, iHeight, priPrincipal.getAncho(), 'u');
        }
    }

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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tarea62 tarGame = new Tarea62();
    }

    @Override
    public void run() {
        /* mientras dure el juego, se actualizan posiciones del Principal
           se checa si hubo colisiones con los objetos malos y buenos
         */
        // Mientras no sea el fin del juego
        while (true) {
            if (!bGameOver) {            
                if (!bPause) {
                    actualiza();
                    checaColision();
                }
            }
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

        // Si se presiono una tecla de movimiento, actualizar al principal
        if (bKeyPressed) {
            // Posicionar al principal en X segun la tecla presionada
            priPrincipal.avanza(iDirPrinX, iDirPrinY);
        }

        for (Bala balAux : lklBalas) {
            balAux.avanza();
        }

        for (Foe foeAux : lklFoes) {
            foeAux.avanza(priPrincipal.getX()+priPrincipal.getAncho()/2);
        }
    }

    /**
     * checaColision
     *
     * Checar la Colision del objeto principal con el margen del applet Metodo
     * usado para checar la colision entre el principal y los secundarios Para
     * que una colision entre los mismos cuente, debe ser por abajo del
     * secundario Checar la colision de los secundarios con el borde inferior
     * del applet
     */
    public void checaColision() {

        // Colision del principal con el applet
        priPrincipal.choqueFrame(iWidth, iHeight);

        for (Foe foeAux : lklFoes) {
            // Checar si los malos chocan con el principal
            if (priPrincipal.colisionaLado(foeAux, 14, 17, 92)==2) {
                iColisionesMalas ++;
                foeAux. posiciona(iWidth, iHeight, priPrincipal.getAncho(), 'u');
                ajustaVidas();
            }
            // Checar Si los malos se salen del applet
            foeAux.choqueFrame(iWidth, iHeight, priPrincipal.getAncho());
        }

        for (int iI =0; iI < lklBalas.size(); iI++) {
            Bala balAux = (Bala) lklBalas.get(iI);
            // Checar si las balas se salen del applet 
            if (balAux.choqueFrame(iWidth, iHeight)) {
                lklBalas.remove(balAux);
            }
            else
            {   
                //Checar Si las balas chocan con los malos
                for (int iC =0; iC < lklFoes.size(); iC++) {
                    Foe foeAux = (Foe) lklFoes.get(iC);
                    if (balAux.colisiona(foeAux)) {
                        aShoot.play();
                        ajustaPuntos();
                        lklBalas.remove(balAux);
                        foeAux. posiciona(iWidth, iHeight, priPrincipal.getAncho(),'u');
                    }
                }
            }
        }

    }
    public void ajustaVidas() {
        iPuntos--;
        if(iColisionesMalas == 5) {
            aHurt.play();
            iVelocidadFoe++;
            for (Foe foeAux : lklFoes) {
                foeAux.setVelocidad(iVelocidadFoe);
            }
            iVidasRestantes--;
            iColisionesMalas =0;
        }
        if(iVidasRestantes<1) {
            bGameOver = true;
        }
    }
    public void ajustaPuntos() {
        iPuntos+=10;
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
        if (priPrincipal != null && imaImagenFondo != null && basBoard!= null) {
            
            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaImagenFondo, 0, 0, iWidth,
                    iHeight, this);
 
            // Dibujar el principal
            priPrincipal.paint(graDibujo, this);

            // Dibuja los objetos Bala
            for (Bala balAux : lklBalas) {
                balAux.paint(graDibujo, this);
            }

            // Dibuja los objetos Foe
            for (Foe foeAux : lklFoes) {
                foeAux.paint(graDibujo, this);
            }


            // Si se acaba el juego, mostrar el banner de GAMEOVER
            if (bGameOver) {
                graDibujo.drawImage(Toolkit.getDefaultToolkit()
                        .getImage(this.getClass()
                                .getResource("Images/GameOver.gif")),
                        0, 0, iWidth, iHeight, this);
            }

            // Banner
            basBoard.paint(graDibujo, this); 
            if (bPause) {
                basPause.paint(graDibujo, this); 
            } 
            int iCord = 337;
            for (int iI =0; iI<iVidasRestantes; iI++) {
                basLives.setX(iCord);
                basLives.paint(graDibujo, this); 
                iCord += basLives.getAncho()+2;
            }

            // Elegir el color del string a pintar y el tipo de letra
            graDibujo.setColor(Color.white);
            graDibujo.setFont(new Font("Serif", Font.BOLD, 20));

            // Escribir las vidas restantes
            //graDibujo.drawString(String.valueOf(iVidasRestantes), 337, 28);
            // Escribir los puntos ganados
            graDibujo.drawString(String.valueOf(iPuntos), 647, 28);
            // Escribir cuantos se han ido
            graDibujo.drawString(String.valueOf(iColisionesMalas), 100, 28);

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
        //throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * keyPressed
     *
     * Checar si la tecla presionada fue una de movimiento Q, A, P o L
     *
     * @param keyEvent es el objeto de <code>keyTyped</code> del teclado.
     *
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        /* Revisar que tecla se presiono y cambiar la posicion*/
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            iDirPrinX = -1;
            bKeyPressed = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            iDirPrinX =  1;
            bKeyPressed = true;
        }

    }

    /**
     * keyReleased
     *
     * Si se suelta la tecla, no actualizar al principal
     *
     * @param keyEvent es el objeto de <code>keyTyped</code> del teclado.
     *
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
        if (!bGameOver && !bPause) {
            /* Revisar que tecla se presiono y cambiar la posicion*/
            if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                generaBala(-1,-1);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                generaBala( 0,-1);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                generaBala( 1,-1);
            }

            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                bKeyPressed = false;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                bKeyPressed = false;
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_P && !bGameOver) {
            bPause = !bPause;
            bKeyPressed = false;
        }

        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (bGameOver) {
            restart();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
