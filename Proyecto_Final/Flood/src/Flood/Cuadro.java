package Flood;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

/**
 * Cuadro Modela la definición de los objetos tipo  <code>Cuadro<code>
 * Se usará para hacer una lista encadenada de los objetos cuadro
 * que van en las 16 casillas de la matriz del juego principal
 *
 * @author Left & Right
 * @version 1.0
 * @date APR/12/2016
 */
public class Cuadro extends Base {

    private int iIndex;         // Indice del objeto
    private boolean isActive;   // Indica si el cuadro está activado o no
    private Color colFondo;     // Color para el fondo
    private int iPregunta;      // Indice de la pregunta
    private int iAncho;
    private int iAlto;

    /**
     * Cuadro
     *
     * Metodo constructor usado para crear el objeto Cuadro creando el Cuadro a
     * partir de una Base
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iIndex es el <code> índice </code> del objeto.
     * @param isActive es la <code> Bandera de activacion </code> del objeto.
     * @param colFondo es el <code> Color de fondo </code> del cuadro
     * @param iPregunta es el <index> de la matriz de preguntas </code>
     */
    public Cuadro(int iX, int iY, Image imaImagen, int iIndex, boolean isActive,
            Color colFondo, int iAncho, int iAlto, int iPregunta) {

        super(iX, iY, imaImagen);
        this.iIndex = iIndex;
        this.isActive = isActive;
        this.colFondo = colFondo;
        this.iPregunta = iPregunta;
        this.iAncho = iAncho;
        this.iAlto = iAlto;
    }

    /**
     * getIndex
     *
     * Metodo de acceso para obtener el índice del cuadro
     *
     * @return iIndex es el <code> índice </code> del objeto.
     *
     */
    public int getIndex() {

        return iIndex;
    }

    public int getPregunta() {

        return iPregunta;
    }

    public void setPregunta(int iPregunta) {

        this.iPregunta = iPregunta;
    }

    public void setIndex(int iIndex) {

        this.iIndex = iIndex;
    }

    /**
     * isActive
     *
     * Metodo para obtener el estado de activación del cuadro.
     *
     * @return isActive es la bandera del <code> cuadro </code> del objeto.
     *
     */
    public boolean isActive() {

        return isActive;
    }

    /**
     * setActive
     *
     * Metodo modificador usado para cambiar la bandera isActive
     *
     * @param bActive es la <code> bandera </code> del objeto.
     *
     */
    public void setActive(boolean bActive) {

        isActive = bActive;
    }

    public void paint(Graphics graGrafico, ImageObserver imoObserver, LinkedList<Pregunta> lklPreguntas) {
        graGrafico.setColor(getColor());
        graGrafico.fillRect(getX(), getY(), getAncho(), getAlto());

        String sPreg = lklPreguntas.get(getPregunta()).getPregunta();

        Color colAux = new Color(0, 0, 0);
        graGrafico.setColor(colAux);
        graGrafico.drawString(sPreg, getX(), getY()+getAlto()/2);

    }

    public Color getColor() {
        return this.colFondo;
    }

    public void setColor(Color colFondo) {
        this.colFondo = colFondo;
    }

    public int getAncho() {
        return iAncho;
    }

    public int getAlto() {
        return iAlto;
    }
}
