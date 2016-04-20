package Flood;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Left & Right
 * @version 1.0
 * @date APR/12/2016
 */
public class DisplayPregunta {

    private String sPregunta;
    private String sPreguntaBase;
    private int iX;
    private int iY;
    private Font fonFuente;
    private Font fonFuentel;

    public DisplayPregunta(int iX, int iY, String sPregunta, String sPreguntaBase) throws FontFormatException, IOException {
        this.fonFuente = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/Custom.ttf"));
        this.fonFuente = this.fonFuente.deriveFont(40F);
        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("./src/Flood/CustomL.ttf"));
        this.fonFuentel = this.fonFuentel.deriveFont(25F);

        this.iX = iX;
        this.iY = iY;
        this.sPregunta = sPregunta;
        this.sPreguntaBase = sPreguntaBase;
    }

    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.setFont(fonFuentel);
        graGrafico.drawString(getPreguntaBase(), getX(), getY()-10);
        graGrafico.setFont(fonFuente);
        graGrafico.drawString(getPregunta(), getX(), getY() + 35);
    }

    public String getPregunta() {
        return sPregunta;
    }

    public String getPreguntaBase() {
        return sPreguntaBase;
    }

    public void setPregunta(String sPregunta) {
        this.sPregunta = sPregunta;
    }

    public void setPreguntaBase(String sPreguntaBase) {
        this.sPreguntaBase = sPreguntaBase;
    }

    public int getX() {
        return iX;
    }

    public int getY() {
        return iY;
    }

}
