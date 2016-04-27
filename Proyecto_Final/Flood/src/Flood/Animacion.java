package Flood;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

/**
 *
 * @author Left & Right
 */
public class Animacion {

    //imagenes de la animacion
    private Image imaSplash1;
    private Image imaSplash2;
    private Image imaSplash3;
    private Image imaSplash4;
    private Image imaSplash5;
    private Image imaSplash6;
    private Image imaSplash7;
    private Image imaSplash8;
    private Image imaSplash9;
    private Image imaSplash10;
    private Image imaSplash11;
    private Image imaSplash12;
    private Image imaSplash13;
    private Image imaSplash14;
    private Image imaSplash15;
    private Image imaSplash16;
    private Image imaSplash17;
    private Image imaSplash18;
    private Image imaSplash19;

    private int iPosCuadroX;
    private int iPosCuadroY;

    private int iContadorCiclos;

    private boolean bAnimated;

    //Linked list para la animacion
    private LinkedList<Image> lklSplash;

    /**
     * contador que lleva el indicie de la animacion en la lista
     */
    int iContadorAnimacion; //
    int iSizeImageX;
    int iSizeImageY;

    public Animacion(int iPosCuadroX, int iPosCuadroY) {

        this.iPosCuadroX = iPosCuadroX;
        this.iPosCuadroY = iPosCuadroY;

        initVars();
        initImages();
        animacion();

    }

    public boolean isAnimated() {

        return bAnimated;
    }
    public void setAnimated(boolean bAnimated) {

        this.bAnimated = bAnimated;
    }
    private void initVars() {

        bAnimated = true;
        lklSplash = new LinkedList();
        iContadorAnimacion = 0; //
        iSizeImageX = 3;
        iSizeImageY = 3;
        iContadorCiclos = 0;

    }

    private void initImages() {

        imaSplash1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1A.png"));
        imaSplash2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/1B.png"));
        imaSplash3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2A.png"));
        imaSplash4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/2B.png"));
        imaSplash5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3A.png"));
        imaSplash6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/3B.png"));
        imaSplash7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4A.png"));
        imaSplash8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/4B.png"));
        imaSplash9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5A.png"));
        imaSplash10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/5B.png"));
        imaSplash11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6A.png"));
        imaSplash12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/6B.png"));
        imaSplash13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7A.png"));
        imaSplash14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/7B.png"));
        imaSplash15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8A.png"));
        imaSplash16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/8B.png"));
        imaSplash17 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9A.png"));
        imaSplash18 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/9B.png"));
        imaSplash19 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/splash/10A.png"));
    }

    public void animacion() {

        lklSplash = new LinkedList();

        lklSplash.add(imaSplash1);
        lklSplash.add(imaSplash2);
        lklSplash.add(imaSplash3);
        lklSplash.add(imaSplash4);
        lklSplash.add(imaSplash5);
        lklSplash.add(imaSplash6);
        lklSplash.add(imaSplash7);
        lklSplash.add(imaSplash8);
        lklSplash.add(imaSplash9);
        lklSplash.add(imaSplash10);
        lklSplash.add(imaSplash11);
        lklSplash.add(imaSplash12);
        lklSplash.add(imaSplash13);
        lklSplash.add(imaSplash14);
        lklSplash.add(imaSplash15);
        lklSplash.add(imaSplash16);
        lklSplash.add(imaSplash17);
        lklSplash.add(imaSplash18);
        lklSplash.add(imaSplash19);
    }

    private void animacionSplash(Graphics graGrafico, ImageObserver imgobs) {

        Rectangle rectReference = new Rectangle();

        rectReference.setBounds(iPosCuadroX, iPosCuadroY, 88, 88);

        int iRectOffsetX = (int) rectReference.getX() + (rectReference.width / 2) - (iSizeImageX / 2);
        int iRectOffsetY = (int) rectReference.getY() + (rectReference.height / 2) - (iSizeImageY / 2);

        if (iContadorAnimacion < lklSplash.size() - 1) {

            iContadorAnimacion++;
            iSizeImageX += 5;
            iSizeImageY += 5;

        } else {
            setAnimated(false);
            iContadorAnimacion = 0;
            iSizeImageX = 0;
            iSizeImageY = 0;

        }

        graGrafico.drawImage(lklSplash.get(iContadorAnimacion), iRectOffsetX, iRectOffsetY, iSizeImageX, iSizeImageY, imgobs);
    }

    public void paintComponent(Graphics graGrafico, ImageObserver imgobs) {

        if (isAnimated()) {
            animacionSplash(graGrafico, imgobs); //splash animation
            System.out.println("Paint component de animacion");
        }

    }

}
