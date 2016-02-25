package tarea62;

/**
 * Bala
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Bala</code>
 * Hereda de Personaje y sera utilizado para modelar 
 * a los disparos
 *
 * @author Jose Humberto Garza Rosado
 * @version A00808689
 * @date 24/02/2016
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Bala extends Personaje{

    private int iDireccionX; // Direccion de la bala
    private int iDireccionY;

    /**
     * Bala
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iDirX es la <code>Direccion en x</code> del objeto.
     * @param iiDirYes la <code>Direccion en y</code> del objeto.
     * @param iVel es la <code>velocidad</code> del objeto.
     * el enemigo perseguira al principal
     *
     */
    public Bala(int iX, int iY, Image imaImagen, int iDirX, 
            int iDirY, int iVel) {
        super(iX, iY, imaImagen, iVel);
        this.iDireccionX = iDirX;
        this.iDireccionY = iDirY;
    }


    /**
     * getDireccionX
     *
     * Metodo de acceso que regresa la direccion X del objeto
     *
     * @return iDireccionX es la <code>direccion X</code> del objeto.
     *
     */
    public int getDireccionX() {
        return iDireccionX;
    }

    /**
     * getDireccionY
     *
     * Metodo de acceso que regresa la direccion Y del objeto
     *
     * @return iDireccionY es la <code>direccion Y</code> del objeto.
     *
     */
    public int getDireccionY() {
        return iDireccionY;
    }

    /**
     * choqueFrame
     *
     * Metodo que indica si la bala se salio del Frame
     *
     * @param iWidth es el <code>Ancho</code> del marco.
     * @param iHeight es el <code>Alto</code> del marco.
     *
     */
    public boolean choqueFrame(int iWidth, int iHeight) {
        int iChoque = colisionaApplet(iWidth, iHeight);
        switch (iChoque) {
            case 4:  
                    return true;
            case 6:  
                    return true;
            case 8:  
                    return true;
            case 2:  
                    return true;
            default:  
                    return false;
        }

    }
    /**
     * avanza
     *
     * Metodo que mueve la bala de acuerdo a su direccion
     * 
     */
    public void avanza() {
        setX( getX()+getDireccionX()*getVelocidad() );
        setY( getY()+getDireccionY()*getVelocidad() );
    }


}
