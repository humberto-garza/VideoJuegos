package tarea62;

/**
 * Principal
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Principal</code>
 * Hereda de Personaje y sera utilizado para modelar 
 * al personaje principal
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
     * Principal
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iVel es la <code>velocidad</code> del objeto.
     * 
     */
    public Principal(int iX, int iY, Image imaImagen, int iVel) {
        super(iX, iY, imaImagen, iVel);
    }

    /**
     * choqueFrame
     *
     * Metodo que evita que el principal se salga del applet
     *
     * @param iWidth es el <code>Ancho</code> del marco.
     * @param iHeight es el <code>Alto</code> del marco.
     *
     */
    public void choqueFrame(int iWidth, int iHeight) {
        /** Mandar a llamar el metodo de colision para saber 
         * en que parte del Marco colisiona y hacer la correccion
        **/
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

    /**
     * avanza
     *
     * Metodo que mueve al principal de acuerdo a 
     * la direccion que se le asigne usando la velocidad
     *
     * @param iDirX es la <code>direccion en X</code> del objeto.
     * @param iDirY es la <code>direccion en Y</code> del objeto.
     *
     */
    public void avanza(int iDirX, int iDirY) {
        setX( getX() + (getVelocidad()*iDirX)); 
        setY( getY() + (getVelocidad()*iDirY)); 
    }

    /**
     * colisionaLado
     *
     * Metodo que indica de que lado del principal hubo una colision
     *
     * @param obj es el  <code>Objeto a comparar</code> la colision
     * @param iOffX es el <code>Offset en X</code> a comparar
     * @param iOffY es el <code>Offset en Y</code> a comparar
     * @param iWidth es el umbral de <code>Ancho</code> de la colision
     *
     * @return int que indica de que lado fue la colision, 
     * Revisar la documentacion de "Outcode" para mas informacion
     */
    public int colisionaLado(Object obj, int iOffX, int iOffY, int iWidth) {
        if (colisiona(obj)) {
            // Si se intersectan, obtener las coordenadas del punto medio 
            // de la base del objeto a comparar
            int iCoordenadaBaseX = ((Base)obj).getX() + 
                    ((Base)obj).getAncho()/2;
            int iCoordenadaBaseY = ((Base)obj).getY() + 
                    ((Base)obj).getAlto()/2;
            // Crear un rectangulo con los limites del principal
            // Se dibuja un rectangulo con offset para describir en que parte 
            // se desea comparar el contacto
            Rectangle recEste = new Rectangle(getX()+iOffX,getY()+iOffY, 
                    iWidth,getAlto());
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
