package Flood;

import javax.swing.ImageIcon;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

/**
	El applet AppletAnimacion muestra una animación en pantalla.
*/
public class AppletAnimacion{

	//Objeto de la clase Animacion para el manejo de la animación
	private Animacion anim;
	
	//Variables de control de tiempo de la animación
	private long tiempoActual;
	private long tiempoInicial;
	int posX, posY;
	
	/**
		El método init() crea la animación que se mostrará en pantalla.
	*/
	public void init(){
		
		//Se cargan las imágenes(cuadros) para la animación
		Image splash1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1A.png"));
		Image splash2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1B.png"));
		Image splash3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2A.png"));
		Image splash4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2B.png"));
		Image splash5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3A.png"));
		Image splash6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3B.png"));
		Image splash7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4A.png"));
		Image splash8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4B.png"));
		Image splash9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5A.png"));
		Image splash10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5B.png"));
		Image splash11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6A.png"));
		Image splash12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6B.png"));
                Image splash13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7A.png"));
		Image splash14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7B.png"));
		Image splash15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8A.png"));
		Image splash16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8B.png"));
		Image splash17 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9A.png"));
		Image splash18 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9B.png"));
		Image splash19 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/10A.png"));
		
		
		//Se crea la animación
		anim = new Animacion();
		anim.sumaCuadro(splash1, 50);
		anim.sumaCuadro(splash2, 50);
		anim.sumaCuadro(splash3, 50);
		anim.sumaCuadro(splash4, 50);
		anim.sumaCuadro(splash5, 50);
		anim.sumaCuadro(splash6, 50);
		anim.sumaCuadro(splash7, 50);
		anim.sumaCuadro(splash8, 50);
		anim.sumaCuadro(splash9, 50);
		anim.sumaCuadro(splash10, 50);
		anim.sumaCuadro(splash11, 50);
		anim.sumaCuadro(splash12, 50);
                anim.sumaCuadro(splash13, 50);
		anim.sumaCuadro(splash14, 50);
		anim.sumaCuadro(splash15, 50);
		anim.sumaCuadro(splash16, 50);
		anim.sumaCuadro(splash17, 50);
		anim.sumaCuadro(splash18, 50);
                anim.sumaCuadro(splash19, 50);
		
		
	}

   
    /**
   	 El método paint() muestra en pantalla la animación
    */
    public void paint(Graphics g) {
   	 
                 posX = 100;
                 posY =	100;
         
        // Muestra en pantalla el cuadro actual de la animación
         if (anim != null) {
        	 g.drawImage(anim.getImagen(), posX, posY, (ImageObserver) this);
         }

    }
}