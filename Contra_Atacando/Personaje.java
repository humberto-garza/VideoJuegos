package tarea62;

/**
 * Personaje
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Personaje</code>
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

public class Personaje extends Base{

    private int iVelocidad;

    /**
     * Personaje
     *
     * Metodo constructor usado para crear el objeto animal creando el icono a
     * partir de una imagen
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Personaje(int iX, int iY, Image imaImagen, int iVel) {
        super(iX, iY, imaImagen);
        this.iVelocidad = iVel;
    }

    /**
     * setVelocidad
     *
     * Metodo modificador usado para cambiar la velocidad del objeto
     *
     * @param iVel es la <code>velocidad</code> del objeto.
     *
     */
    public void setVelocidad(int iVel) {
        this.iVelocidad = iVel;
    }   

    /**
     * getVelocidad
     *
     * Metodo de acceso que regresa la velocidad del objeto
     *
     * @return iVel es la <code>velocidad</code> del objeto.
     *
     */
    public int getVelocidad() {
        return iVelocidad;
    }