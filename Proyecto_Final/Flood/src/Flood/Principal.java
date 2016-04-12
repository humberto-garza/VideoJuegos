package Flood;

/**
 *
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Principal</code>
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

public class Principal extends Personaje{

    /**
     * Malo
     *
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Principal(int iX, int iY, Image imaImagen, int iVel) {
        super(iX, iY, imaImagen, iVel);
    }

    public void choqueFrame(int iWidth, int iHeight) {
        int iChoque = colisionaApplet(iWidth, iHeight);
        switch (iChoque) {
            case 4:  
                    setX(0);
                    break;
            case 6:  
                    setX(iWidth - getAncho());
                    break;
            case 8:  
                    setY(0);
                    break;
            case 2:  
                    setY(iHeight - getAlto());
                    break;
            default:  
                    break;
        }

    }

    public void avanza(int iDirX, int iDirY) {
        setX( getX() + (getVelocidad()*iDirX)); 
        setY( getY() + (getVelocidad()*iDirY)); 
    }

    public int colisionaLado(Object obj, int iOffX, int iOffY, int iWidth) {
        if (colisiona(obj)) {
            // Si se intersectan, obtener las coordenadas del punto medio 
            // de la base del objeto a comparar
            int iCoordenadaBaseX = ((Base)obj).getX() + ((Base)obj).getAncho()/2;
            int iCoordenadaBaseY = ((Base)obj).getY() + ((Base)obj).getAlto()/2;
            // Crear un rectangulo con los limites del principal
            // Se dibuja un rectangulo con offset para describir en que parte 
            // se desea comparar el contacto
            Rectangle recEste = new Rectangle(getX()+iOffX,getY()+iOffY, iWidth,getAlto());
            // Regresar el outcode del contacto del principal con offset
            // con el punto medio inferior del objeto a comparar
            return recEste.outcode((double)iCoordenadaBaseX,
                    (double) iCoordenadaBaseY);
        }
        else {
            return -1;
        }
    }
}
