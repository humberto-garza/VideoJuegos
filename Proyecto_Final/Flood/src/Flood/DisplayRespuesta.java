package Flood;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
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

    public DisplayRespuesta(int iX, int iY, String sRespuesta) {
        this.iX = iX;
        this.iY = iY;
        this.sRespuesta = sRespuesta;
    }

    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.setFont(new Font("SansSerif", Font.PLAIN, 30));

        graGrafico.drawString(getRespuesta(), getX(), getY());
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