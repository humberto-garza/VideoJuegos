package tarea62;

/**
 *
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Malo</code>
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

public class Foe extends Personaje{

    private boolean bFollow;

    /**
     * Malo
     *
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Foe(int iX, int iY, Image imaImagen, int iVel, boolean bFoll) {
        super(iX, iY, imaImagen, iVel);
        this.bFollow = bFoll;
    }

    public boolean choqueFrame(int iWidth, int iHeight, int iMargen) {
        int iChoque = colisionaApplet(iWidth, iHeight);

        switch (iChoque) {
            case 4:  
                    setX(0);
                    break;
            case 6:  
                    setX(iWidth - getAncho());
                    break;
            case 8:  
                    break;
            case 2:  
                    posiciona(iMargen, iWidth, iHeight,'u');
                    return true;
            default:  
                    break;
        }
        return false;
    }

    public void avanza(int iObjectiveX) {
        setY( getY() + getVelocidad()); 
        if (bFollow) {
            int iSign = (int) Math.signum(iObjectiveX - (getX()+getAncho()/2));
            setX(getX() + (getVelocidad()*iSign)); 
        }
    }
}
