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
import java.io.InputStream;
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

    /**
     *
     * @param iX
     * @param iY
     * @param sPregunta
     * @param sPreguntaBase
     * @throws FontFormatException
     * @throws IOException
     */
    public DisplayPregunta(int iX, int iY, String sPregunta, String sPreguntaBase) throws FontFormatException, IOException {

        InputStream fontStream = getClass().getResourceAsStream("/Flood/Custom.ttf");
        this.fonFuente = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        this.fonFuente = this.fonFuente.deriveFont(40F);

        InputStream fontStream2 = getClass().getResourceAsStream("/Flood/CustomL.ttf");
        this.fonFuentel = Font.createFont(Font.TRUETYPE_FONT, fontStream2);
        this.fonFuentel = this.fonFuentel.deriveFont(25F);

        this.iX = iX;
        this.iY = iY;
        this.sPregunta = sPregunta;
        this.sPreguntaBase = sPreguntaBase;
    }

    /**
     *
     * @param graGrafico
     * @param imoObserver
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.setFont(fonFuentel);

        graGrafico.drawString(getPreguntaBase(), getX(), getY() - 10);
        graGrafico.setFont(fonFuente);
        graGrafico.drawString(getPregunta(), getX(), getY() + 28);
    }

    /**
     *
     * @return
     */
    public String getPregunta() {
        return sPregunta;
    }

    /**
     *
     * @return
     */
    public String getPreguntaBase() {
        return sPreguntaBase;
    }

    /**
     *
     * @param sPregunta
     */
    public void setPregunta(String sPregunta) {
        this.sPregunta = sPregunta;
    }

    /**
     *
     * @param sPreguntaBase
     */
    public void setPreguntaBase(String sPreguntaBase) {
        this.sPreguntaBase = sPreguntaBase;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return iX;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return iY;
    }

    void addMouseListener(Tablero aThis) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
