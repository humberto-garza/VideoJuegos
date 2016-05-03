package Flood;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 *
 * @author Left & Right
 * @version 1.0
 * @date APR/12/2016
 */
public class DisplayRespuesta {

    private String sRespuesta;
    private int iX;
    private int iY;
    private Font fonFuente;
    //Base boton de hint
    protected Base basHint; //objeto base que sirve como boton para obtener una pista
    //Variables de posicion del mouse
    int iMouseX;
    int iMouseY;

    public DisplayRespuesta(int iX, int iY, String sRespuesta) throws FontFormatException, IOException {
        InputStream fontStream = getClass().getResourceAsStream("/Flood/Custom.ttf");
        this.fonFuente = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        this.fonFuente = this.fonFuente.deriveFont(40F);
        
        this.iX = iX;
        this.iY = iY;
        this.sRespuesta = sRespuesta;

        //crea base boton hint
        basHint = new Base(520, 670, Toolkit.getDefaultToolkit()
                           .getImage(this.getClass().getResource("Images/sidePanel/PistaB.png")));

    }

    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.setFont(fonFuente);
        graGrafico.drawString(getRespuesta(), getX(), getY());
        basHint.paint(graGrafico, imoObserver);
    }

    public String getRespuesta() {
        return sRespuesta;
    }

    public void setRespuesta(String sRespuesta) {
        this.sRespuesta = sRespuesta;
    }

    public int getX() {
        return iX;
    }

    public int getY() {
        return iY;
    }

    public int getSize() {
        return sRespuesta.length();
    }

}
