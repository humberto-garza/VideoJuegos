package tarea62;

/**
 * Foe
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Foe</code>
 * Hereda de Personaje y sera utilizado para modelar 
 * a los enemigos
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
     * Foe
     *
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iVel es la <code>velocidad</code> del objeto.
     * @param bFoll es la<code> Variable</code> que indica si 
     * el enemigo perseguira al principal
     *
     */
    public Foe(int iX, int iY, Image imaImagen, int iVel, boolean bFoll) {
        super(iX, iY, imaImagen, iVel);
        this.bFollow = bFoll;
    }

    /**
     * choqueFrame
     *
     * Metodo que indica si el enemigo se salio por abajo
     * del marco
     *
     * @param iWidth es el <code>Ancho</code> del marco.
     * @param iHeight es el <code>Alto</code> del marco.
     *
     */
    public boolean choqueFrame(int iWidth, int iHeight, int iMargen) {
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
                    break;
            case 2:  
                    posiciona(iMargen, iWidth, iHeight,'u');
                    return true;
            default:  
                    break;
        }
        return false;
    }
    /**
     * avanza
     *
     * Metodo que mueve al enemigo de acuerdo al objetivo
     * si es que lo persigue
     * de otra forma se movera hacia abajo
     *
     * @param iObjectiveX es la <code> Coordenada Media</code> en 
     * X del objetivo
     *
     */
    public void avanza(int iObjectiveX) {
        setY( getY() + getVelocidad()); 
        if (bFollow) {
            int iSign = (int) Math.signum(iObjectiveX - (getX()+getAncho()/2));
            setX(getX() + (getVelocidad()*iSign)); 
        }
    }
}
