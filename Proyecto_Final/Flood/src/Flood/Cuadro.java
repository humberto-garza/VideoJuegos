
package Flood;

//prueba

import java.awt.Image;

/**
 *
 * @author Left & Right
 */
public class Cuadro extends Base {
    
    private int iIndex;
    private boolean isActive;
    

    public Cuadro(int iX, int iY, Image imaImagen) {
        super(iX, iY, imaImagen);
    }
    
    public int getIndex(){
        
        return iIndex;
    }
    
    public void setIndex(int iIndex){
        
        this.iIndex = iIndex;
    }
    
    
    public boolean isActive(){
        
        return isActive;
    }
    
 
}
