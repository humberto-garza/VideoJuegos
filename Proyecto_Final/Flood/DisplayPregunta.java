package Flood;

import java.awt.Color;
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
public class DisplayPregunta {

    private String sPregunta;      // Indice de la pregunta
    private int iX;
    private int iY;

    public DisplayPregunta(int iX, int iY, String sPregunta) {
        this.iX = iX;
        this.iY = iY;
        this.sPregunta = sPregunta;
    }

    public void paint(Graphics graGrafico, ImageObserver imoObserver, LinkedList<Pregunta> lklPreguntas) {
        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.drawString(getPregunta(), iOffsetX, iOffsetY);
    }

    public String getPregunta() {
        return sPregunta;
    }

    public void setPregunta(String sPregunta) {
        this.sPregunta = sPregunta;
    }
}