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

public class Malo extends Base{

    private int iDireccion; // Direccion de la bala
    private int iVelocidad;

    /**
     * Bala
     *
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Malo(int iX, int iY, Image imaImagen, int iDir, int iVel) {
        super(iX, iY, imaImagen);
        this.iDireccion = iDir;
        this.iVelocidad = iVel;
    }
}
