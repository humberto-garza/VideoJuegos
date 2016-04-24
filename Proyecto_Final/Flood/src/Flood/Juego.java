package Flood;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;


/**
 *
 * @author L&R
 */
public class Juego implements KeyListener {

	//The Flood Game instance
	private Flood tarGame;

	// Variables de la cuadricula
	private int iCuadroAncho;   // Ancho del Cuadro
	private int iCuadroAlto;    // Alto del Cuadro
	private int iCuadroMargen;  // Margen de los Cuadros
	private int iGridOffsetX;   // Posicion de la cuadricula X
	private int iGridOffsetY;   // Posicion de la cuadricula Y
	private int iCasillas;      // Total de casillas
	private int iGridRows;      // Cantidad filas
	private int iGridCols;      // Cantidad columnas
	private int iIndexActual;   // Index del cuadro actual

	// Variables Display Pregunta
	private int iDisPregOffX;   // Offset del Display de Pregunta X
	private int iDisPregOffY;   // Offset del Display de Pregunta Y
	private DisplayPregunta disPregunta;

	// Variables Display Respuesta
	private int iDisResOffX;    // Offset del Display de Respuesta X
	private int iDisResOffY;    // Offset del Display de Respuesta Y
	private DisplayRespuesta disRespuesta;

	// Estructura de posicionamiento
	private LinkedList<Coordenada> cooCoordenadas;

	// Objeto Base Principal
	private Base basSelector; // Objeto Selector

	// Listas Encadenadas
	private LinkedList<Integer> lklDisponibles;
	private LinkedList<Integer> lklUsados;
	private LinkedList<Cuadro> lklCuadrosBase;
	private LinkedList<Color> lklColores;
	private LinkedList<Pregunta> lklPreguntas;

	// Index De listas
	private int iIndexColor;
	private int iIndexUsado;

	// Booleanas
	private boolean bCambio;	// Forzar cambio de cuadro
	boolean bKeyPressed;		// Tecla Presionada

	public Juego() throws FontFormatException, IOException {

		// Crear el objeto selector
		basSelector = new Base(iGridOffsetX, iGridOffsetY, Toolkit.getDefaultToolkit()
		                       .getImage(this.getClass().getResource("Images/Selector.png")));

		// Variables de la cuadricula
		iCuadroAncho    = 110;
		iCuadroAlto     = 110;
		iCuadroMargen   = 4;
		iGridOffsetX    = 50;
		iGridOffsetY    = 110;
		iGridRows       = 5;
		iGridCols       = 5;
		iCasillas       = iGridRows * iGridCols;
		iIndexActual    = 0;

		// Variables Display Pregunta
		iDisPregOffX    = 60;
		iDisPregOffY    = 67;
		disPregunta     = new DisplayPregunta(iDisPregOffX, iDisPregOffY, "", "");

		// Variables Cuadro Respuesta
		iDisResOffX     = 60;
		iDisResOffY     = 710;
		disRespuesta    = new DisplayRespuesta(iDisResOffX, iDisResOffY, "");

		// Estructura de posicionamiento
		cooCoordenadas = new LinkedList<Coordenada>();
		for (int iRow = 0; iRow < iGridRows; iRow++) {
			for (int iCol = 0; iCol < iGridCols; iCol++) {
				int iAuxCRow  = iGridOffsetY + iRow * iCuadroAlto;
				int iAuxCCol  = iGridOffsetX + iCol * iCuadroAncho;
				Coordenada cooAux = new Coordenada(iAuxCCol, iAuxCRow);
				cooCoordenadas.add(cooAux);
			}
		}

		// Inicializar listas
		lklCuadrosBase = new LinkedList<Cuadro>();
		lklDisponibles = new LinkedList<Integer>();
		lklUsados = new LinkedList<Integer>();
		lklColores = new LinkedList<Color>();
		lklPreguntas = new LinkedList<Pregunta>();
		
		// Inicializar los cuadros
		for (int iX = 0; iX < iCasillas; iX++) {
			lklDisponibles.add(iX);
			Color colBlanco = new Color(255, 255, 255);
			Cuadro cuaAux = new Cuadro(
			    cooCoordenadas.get(iX).getX() + iCuadroMargen,
			    cooCoordenadas.get(iX).getY() + iCuadroMargen,
			    Toolkit.getDefaultToolkit()
			    .getImage(this.getClass().getResource("Images/Cuadro.png")),
			    iX, // Valors en 0
			    false,
			    colBlanco,
			    iCuadroAncho - iCuadroMargen * 2,
			    iCuadroAlto - iCuadroMargen * 2,
			    0);

			lklCuadrosBase.add(cuaAux);
		}

		// Listeners: Teclado y Mouse
		addKeyListener(this);
	}

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
