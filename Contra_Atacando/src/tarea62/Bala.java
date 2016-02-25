package tarea62;

/**
 *
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Bala</code>
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
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Bala(int iX, int iY, Image imaImagen, int iDirX, int iDirY, int iVel) {
        super(iX, iY, imaImagen, iVel);
        this.iDireccionX = iDirX;
        this.iDireccionY = iDirY;
    }

    public int getDireccionX() {
        return iDireccionX;
    }

    public int getDireccionY() {
        return iDireccionY;
    }

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

    public void avanza() {
        setX( getX()+getDireccionX()*getVelocidad() );
        setY( getY()+getDireccionY()*getVelocidad() );
    }


}
