package Flood;

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


    protected int colisionaApplet(int iWidth, int iHeight) {
        int iColision = 0;

        if (getX() < 0) {
            //setX(0);
            iColision = 4;
        }
        else if (getX() > iWidth - getAncho()) {
            //setX(iWidth - getAncho());
            iColision = 6;
        }
        if (getY() < 0) {
            //setY(0);
            iColision = 8;
        }
        else if (getY() > iHeight - getAlto()) {
            //setY(iHeight - getAlto());
            iColision = 2;
        }
        return iColision;
    }


    /**
     * colisiona
     *
     * Checa la colision de un objeto Base
     *
     * @param obj objeto de la clase <code>Object</code>
     * @return boleano <code>true</code> si colisiona <code>false</code> si no
     *
     */
    public boolean colisiona(Object obj) {
        // Checa si el obj es de la clase base
        if (obj instanceof Base) {
            Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), getAlto());
            //Hay que castear porque un obj puede ser cualqueir tipo de objeto
            Rectangle recOtro = new Rectangle(((Base) obj).getX(), 
                    ((Base) obj).getY(),
                    ((Base) obj).getAncho(), ((Base) obj).getAlto());

            return recEste.intersects(recOtro);
        } else {
            return false;
        }
    }

    /**
     * colisiona
     *
     * Checa si un set de coordenadas se encuentra dentro del area del objeto
     * Base
     *
     * @param iX es la <code>coordenada en x</code> a probar.
     * @param iY es la <code>coordenada en y</code> a probar.
     * @return boleano <code>true</code> si el click recae en el area
     * <code>false</code> si no recae
     *
     */
    public boolean colisiona(int iX, int iY) {
        Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), 
                getAlto());
        Rectangle recOtro = new Rectangle(iX, iY, 1, 1);
        return recEste.intersects(recOtro);
    }

    /**
     * posiciona
     *
     * Posiciona un objeto en una seccion de un plano Calcula cuantos objetos
     * caben en el ancho del applet menos el margen Posiciona al objeto Base en
     * de esas secciones al azar con un margen especificado en izquierda y
     * derecha
     *
     * @param iX es la <code>coordenada en x</code> a probar.
     * @param iY es la <code>coordenada en y</code> a probar.
     * @param iLado es el <code>lado del applet</code> por donde saldra el
     * objeto Lado 0 = Izquierdo Lado 1 = Derecho
     * @return boleano <code> true</code> si el click recae en el area
     * <code> false</code> si no recae
     *
     */
    public void posiciona(int iWidth, int iHeight, int iMargen,
            char cLado) {
        // Posicionar del lado Derecho 
        if (cLado == 'u') {
            int iSectionsX = (iWidth - iMargen) / getAncho();
            setX((getAncho() * ((int) (Math.random() * iSectionsX))) + 
                    iMargen / 2);
            setY((-1) * ((int) (Math.random() * iHeight)));
        }
        // Posicionar del lado Derecho 
        if (cLado == 'r') {
            int iSectionsY = (iHeight - iMargen) / getAlto();
            setX(iWidth + (((int) (Math.random() * iWidth))));
            setY((getAlto() * ((int) (Math.random() * iSectionsY))) + 
                    iMargen / 2);
        }
        // Posicionar del lado Izquierdo
        if (cLado == 'l') {
            int iSectionsY = (iHeight - iMargen) / getAlto();

            setX((-1) * (((int) (Math.random() * iWidth))));
            setY((getAlto() * ((int) (Math.random() * iSectionsY))) + 
                    iMargen / 2);
        }

    }


}