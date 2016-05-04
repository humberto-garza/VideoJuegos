package Flood;

/**
 *
 * @author asushg
 */
public class Coordenada {
	private int iX;
	private int iY;

    /**
     *
     * @param iX
     * @param iY
     */
    public Coordenada(int iX, int iY) {
		this.iX = iX;
		this.iY = iY;
	}

    /**
     *
     * @return
     */
    public int getX() {
		return this.iX;
	}

    /**
     *
     * @return
     */
    public int getY() {
		return this.iY;
	}

    /**
     *
     * @param iX
     */
    public void setX(int iX) {
		this.iX = iX;
	}

    /**
     *
     * @param iY
     */
    public void setY(int iY) {
		this.iY = iY;
	}
};