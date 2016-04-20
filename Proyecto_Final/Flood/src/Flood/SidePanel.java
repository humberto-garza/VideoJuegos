/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flood;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author macbook
 */
public class SidePanel extends JPanel {
    
    

    //Variable que indica si el mouse intersecta con algún botón
    private boolean bIntersects;
    
    //Objeto Base para el back Menu
    protected Base basBackMenu;
    
    //The Flood Game instance
    private Flood tarGame;

    public SidePanel(Flood floodGame) {
        

        this.tarGame = floodGame;
        
        setPreferredSize(new Dimension(floodGame.iWidth -200, floodGame.iHeight));
        
        setBackground(Color.CYAN);
        
        //crea objeto de back al menu
        basBackMenu = new Base(750, 400, Toolkit.getDefaultToolkit()
               .getImage(this.getClass().getResource("Images/backToMenu.png")));
        
    }
    
    public void paintComponent(Graphics graGrafico){
        super.paintComponent(graGrafico);
        
        if (basBackMenu != null) {
            // Dibujar el objeto de play
            //basBackMenu.paint(graGrafico, this);
            
        } // Si no se ha cargado se dibuja un mensaje
        else {
            //Da un mensaje mientras se carga el dibujo
            graGrafico.drawString("No se cargo la imagen..", 20, 20);
        }
        
    }
    
    
}
