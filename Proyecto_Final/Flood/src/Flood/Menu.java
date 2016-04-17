/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flood;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 *
 * @author macbook
 */
public class Menu extends JFrame implements MouseListener {
    
    //Jframe Size
    private int iHeight;
    private int iWidth;
    
    //posiciones del Mouse
    private int iMouseX;
    private int iMouseY;
    
    // Objetos imagen
    private Image imaImagenFondoMenu; // Imagen de fondo
    private Image imaImagenApplet; // Imagen a proyectar en Applet
    private Graphics graGraficaApplet; // Objeto grafico de la Imagen
    
    //BasePlay
     private Base basPlay;
    

    
    public Menu(){
        
        // Jframe Configuration
        iWidth = 960;
        iHeight = 768;
        
        // Crear la imagen de fondo.
        imaImagenFondoMenu = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/fondoMenu.png"));
        
        //crea objeto de play
        basPlay = new Base(100, 400, Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("Images/play.png")));
        
        addMouseListener(this);
        
        Dimension dimD = new Dimension(iWidth, iHeight);
        Container conC = this.getContentPane();
        conC.setPreferredSize(dimD);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    /**
     * paint
     *
     * Metodo para pintar el JFrame
     *
     * @param graGrafico es el <code>Grafico</code> en el que se pinta
     *
     */
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
        if (imaImagenFondoMenu != null && basPlay != null) {

            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaImagenFondoMenu, 0, 0, iWidth, iHeight, this);
            
            // Dibujar el objeto de play
            basPlay.paint(graDibujo, this);
            
        } // Si no se ha cargado se dibuja un mensaje
        else {
            //Da un mensaje mientras se carga el dibujo
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
    public static void main(String[] args) {
        Menu floodMenu = new Menu();
    }
    
    public void mouseClicked(MouseEvent mouEvent) {
        
        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY();
        
        if ( basPlay.intersects(iMouseX, iMouseY) ){//seleciono play
            //dispose menu, iniciar juego
            this.dispose();
            Flood floodGame = new Flood();
            
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
