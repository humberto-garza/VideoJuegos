package Flood;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;


import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 *
 * @author L&R
 */
public class Tablero {

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
	protected DisplayRespuesta disRespuesta;

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

	// Animacion
	protected Animacion animSplash;

	// Booleanas
	public Tablero(String sCategoria) throws FontFormatException, IOException {

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

		// Cargar Colores
		cargaColores();

		//Cargar Preguntas
		cargaPreguntas(sCategoria);



	}


	public boolean hayDisponibles() {
		return lklDisponibles.size() == iCasillas;
	}



	public void paint(Graphics graDibujo, ImageObserver imoObserver) {
		// Dibuja los objetos Cuadro
		for (Cuadro cuaAux : lklCuadrosBase) {
			if (cuaAux.isActive()) {
				cuaAux.paint(graDibujo, imoObserver, lklPreguntas);
			}
		}
		// Dibujar el selector
		basSelector.paint(graDibujo, imoObserver);

		// Escribir el cuadro de pregunta
		disPregunta.paint(graDibujo, imoObserver);

		// Escribir el cuadro de pregunta
		disRespuesta.paint(graDibujo, imoObserver);

		// Pintar la animación
		if (animSplash != null) {
			animSplash.paintComponent(graDibujo, imoObserver);
		}
	}

	public void cargaColores() throws FileNotFoundException, IOException {
		lklColores.clear();
		iIndexColor = 0;
		// Seleccionar al azar una paleta
		int iRandPicker = (int) (Math.random() * 6) + 1;

		String nombreArchivo = "./src/Flood/Files/Paletas/" + iRandPicker + ".txt";
		BufferedReader fileIn;

		try {
			fileIn = new BufferedReader(new FileReader(nombreArchivo));
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND!");
			File puntos = new File(nombreArchivo);
			PrintWriter fileOut = new PrintWriter(puntos);
			fileOut.println("100,demo");
			fileOut.close();
			fileIn = new BufferedReader(new FileReader(nombreArchivo));
		}

		String[] arrColores;
		String sLine;
		while ((sLine = fileIn.readLine()) != null) {
			arrColores = sLine.split(",");
			int iR = Integer.parseInt(arrColores[0]);
			int iG = Integer.parseInt(arrColores[1]);
			int iB = Integer.parseInt(arrColores[2]);
			Color colAux = new Color(iR, iG, iB);
			lklColores.add(colAux);
		}
		System.out.print("Se Cargaron Colores: ");
		System.out.println(lklColores.size());

	}

	public void cargaPreguntas(String sCategoria) throws FileNotFoundException, IOException, FontFormatException {
		lklPreguntas.clear();

		/*
		String nombreArchivo = "./src/Flood/Files/";
		nombreArchivo += sCategoria + ".txt";
		*/
		String nombreArchivo = sCategoria;
		BufferedReader fileIn;

		try {
			fileIn = new BufferedReader(new FileReader(nombreArchivo));
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND!");
			File puntos = new File(nombreArchivo);
			PrintWriter fileOut = new PrintWriter(puntos);
			fileOut.println("100,demo");
			fileOut.close();
			fileIn = new BufferedReader(new FileReader(nombreArchivo));
		}

		String[] arrPreguntas;
		String sLine;
		sLine = fileIn.readLine();
		disPregunta.setPreguntaBase(sLine);

		while ((sLine = fileIn.readLine()) != null) {
			arrPreguntas = sLine.split(",");
			String sPreg = arrPreguntas[0];
			String sResp = arrPreguntas[1];
			String sPunt = arrPreguntas[2];

			Pregunta preAux = new Pregunta(sPreg, sResp, Integer.parseInt(sPunt));
			lklPreguntas.add(preAux);
		}
		System.out.print("ASe Cargaron Preguntas: ");
		System.out.println(lklPreguntas.size());

	}

	public void setIndex(int iNewIndex) {
		this.iIndexUsado = iNewIndex;
	}

	public void creaCuadro() {
		if (lklDisponibles.size() > 0) {

			// Seleccionar al azar un lugar disponible
			int iRandPicker = (int) (Math.random() * lklDisponibles.size());

			// Remover el seleccionado de la lista
			int iAux = lklDisponibles.remove(iRandPicker);
			lklUsados.add(iAux);
			iIndexUsado = lklUsados.size() - 1;

			Cuadro cuaAux = lklCuadrosBase.get(iAux);

			// Seleccionar un color nuevo
			Color colAux = lklColores.get(iIndexColor);
			cuaAux.setColor(colAux);

			// Seleccionar al azar una pregunta
			iRandPicker = (int) (Math.random() * lklPreguntas.size());
			cuaAux.setPregunta(iRandPicker);

			// Activar
			Pregunta preAux = lklPreguntas.get(iRandPicker);

			cuaAux.setValor(preAux.getPuntos());
			cuaAux.setActive(true);

			// Si no habian cuadors, seleccionar el que se acaba de crear
			if (lklUsados.size() == 1) {
				System.out.println("ENTROCAMBIO");
				cambioCuadro();
			}
		}
	}

	public void creaCuadroAbajo() {
		if (lklDisponibles.size() > 0) {
			// Seleccionar al azar un lugar disponible
			int iRandPicker = (int) (Math.random() * lklDisponibles.size());
			int iIndexDisp = lklDisponibles.get(iRandPicker);
			int iIndexNextD = iIndexDisp + iGridCols;
			boolean bActivo = false;

			while (iIndexNextD < iCasillas) {
				if (!bActivo) {
					Cuadro cuaNext = lklCuadrosBase.get(iIndexNextD);
					bActivo = cuaNext.isActive();
					if (!bActivo) {
						iIndexDisp = iIndexNextD;
						iIndexNextD = iIndexDisp + iGridCols;
					}
					System.out.println(bActivo);
				} else {
					break;
				}
			}

			// Remover el seleccionado de la lista
			for (int iC = 0; iC < lklDisponibles.size(); iC++) {
				if (iIndexDisp == lklDisponibles.get(iC)) {
					lklDisponibles.remove(iC);
					lklUsados.add(iIndexDisp);
					iIndexUsado = lklUsados.size() - 1;
				}
			}

			Cuadro cuaAux = lklCuadrosBase.get(iIndexDisp);

			// Seleccionar un color nuevo
			Color colAux = lklColores.get(iIndexColor);
			cuaAux.setColor(colAux);

			// Seleccionar al azar una pregunta
			iRandPicker = (int) (Math.random() * lklPreguntas.size());
			cuaAux.setPregunta(iRandPicker);

			// Activar
			Pregunta preAux = lklPreguntas.get(iRandPicker);

			cuaAux.setValor(preAux.getPuntos());
			cuaAux.setActive(true);

			// Si no habian cuadors, seleccionar el que se acaba de crear
			if (lklUsados.size() == 1) {
				System.out.println("ENTROCAMBIO");
				cambioCuadro();
			}
		}
	}

	public void llenarGrid() {

		lklDisponibles.clear();

		for (int iC = 0; iC < getCasillas(); iC++) {
			lklUsados.add(iC);
			Cuadro cuaAux = lklCuadrosBase.get(iC);

			// Seleccionar un color nuevo
			Color colAux = lklColores.get(iIndexColor);
			cuaAux.setColor(colAux);

			// Seleccionar al azar una pregunta
			int iRandPicker = (int) (Math.random() * lklPreguntas.size());
			cuaAux.setPregunta(iRandPicker);
			// Activar el cuadro con puntos
			Pregunta preAux = lklPreguntas.get(iRandPicker);
			cuaAux.setValor(preAux.getPuntos());
			cuaAux.setActive(true);
		}
	}

	public void cambioCuadro() {
		if (lklUsados.size() > 0) {
			disRespuesta.setRespuesta("");
			iIndexActual = lklUsados.get(iIndexUsado);
			iIndexUsado = (iIndexUsado + 1) % lklUsados.size();
		}

	}

	public void actualizarSelector() {
		basSelector.setX(cooCoordenadas.get(iIndexActual).getX());
		basSelector.setY(cooCoordenadas.get(iIndexActual).getY());
	}

	public void actualizarPregunta() {
		Cuadro cuaAux = lklCuadrosBase.get(iIndexActual);
		if (cuaAux.isActive()) {
			int iPregIndex = cuaAux.getPregunta();
			String sPreguntaActual = lklPreguntas.get(iPregIndex).getPregunta();
			disPregunta.setPregunta(sPreguntaActual);
		} else {
			disPregunta.setPregunta("");
		}
	}

	public void pressedEnter(int iModoJuego) {
		if (iModoJuego == 3) {
			int iIndexAux = iIndexActual + 1;
			if (iIndexAux == iCasillas) {
				iIndexActual = iCasillas - iGridCols;
			} else {
				iIndexActual = iIndexAux;
			}
			disRespuesta.setRespuesta("");
		} else {
			cambioCuadro();
		}
	}

	public void pressedRight() {
		/*
		* Si moverlo a la derecha se pasa, ponerlo en
		* la primera columna de su fila original
		*/
		if ((iIndexActual + 1) % iGridCols == 0) {
			iIndexActual = iIndexActual - (iGridCols - 1);
		} else {
			iIndexActual = (iIndexActual + 1) % iCasillas;
		}
		disRespuesta.setRespuesta("");
	}
	public void pressedLeft() {
		/*
		 * Si moverlo a la Izquierda se regresa, ponerlo en
		 * la ultima columna de su fila original
		*/
		if ((iIndexActual - 1) % iGridCols == iGridCols - 1 || (iIndexActual - 1) < 0) {
			iIndexActual = iIndexActual + (iGridCols - 1);
		} else {
			iIndexActual = iIndexActual - 1;
		}
		disRespuesta.setRespuesta("");
	}
	public void pressedUp() {
		if ((iIndexActual - iGridCols) < 0) {
			iIndexActual = (iCasillas - iGridCols) + (iIndexActual % iGridCols);
		} else {
			iIndexActual = iIndexActual - iGridCols;
		}
		disRespuesta.setRespuesta("");
	}
	public void pressedDown() {
		if ((iIndexActual + iGridCols) >= iCasillas) {
			iIndexActual = iIndexActual % iGridCols;
		} else {
			iIndexActual = iIndexActual + iGridCols;
		}
		disRespuesta.setRespuesta("");
	}

	public int pressedKey(char cAux, int iModoJuego) {
		int iPuntos = 0;

		if (Character.isDigit(cAux) || Character.isLetter(cAux)) {

			// Actualizar la Pregunta Actual
			Cuadro cuaAux = lklCuadrosBase.get(iIndexActual);
			// Si el cuadro esta activo
			if (cuaAux.isActive()) {
				// Obtener el index de la pregunta
				int iPregIndex = cuaAux.getPregunta();
				String sResEsperada = lklPreguntas.get(iPregIndex).getRespuesta();
				String sResEsperadaCopy = lklPreguntas.get(iPregIndex).getRespuesta();
				sResEsperada = deAccent(sResEsperada);
				String sResTotal = disRespuesta.getRespuesta();

				// Comparar la respuesta esperada contra lo que se tiene
				int iCharIndex = disRespuesta.getSize();
				char cResEsperada = sResEsperada.charAt(iCharIndex);
				cAux = Character.toLowerCase(cAux);
				if (cAux == 'ñ') {
					cAux = 'n';
				}

				// Si Coinciden los caracteres
				if (Character.toLowerCase(cResEsperada) == cAux) {

					// Agregar el caracter a la respuesta
					String sActualizar = disRespuesta.getRespuesta() + sResEsperadaCopy.charAt(iCharIndex);
					disRespuesta.setRespuesta(sActualizar);


					// Si la respuesta esta completa
					if (disRespuesta.getSize() == sResEsperada.length()) {
						//Animar
						int iAnimX = cooCoordenadas.get(iIndexActual).getX() + iCuadroMargen + 3;
						int iAnimY = cooCoordenadas.get(iIndexActual).getY() + iCuadroMargen + 3;
						animSplash = new Animacion(iAnimX, iAnimY);

						// Sumar los puntos
						iPuntos += cuaAux.getValor();

						// Agregar el index a disponibles
						lklDisponibles.add(iIndexActual);
						// Remover de usados
						iIndexUsado = lklUsados.indexOf(iIndexActual);
						lklUsados.remove(iIndexUsado);
						iIndexUsado = lklUsados.size() - 1;

						// Dar de baja el cuadro
						cuaAux.setActive(false);
						if (iModoJuego == 3) {
							bajarColumna(iIndexActual);
						}

						disRespuesta.setRespuesta("");
						iIndexColor++;
						if (iIndexColor >= lklColores.size()) {
							iIndexColor = 0;
						}
						pressedEnter(iModoJuego);

					}
				}
				// Caracter equivocado
				else {
					iPuntos -= (int)(cuaAux.getValor() / sResEsperadaCopy.length());
				}
			}
		}
		return iPuntos;
	}

	public void bajarColumna(int iCuadro) {
		int iColumna = iCuadro % iGridCols;
		for (int iC = iCuadro - iGridCols; iC >= 0; iC -= iGridCols) {
			Cuadro cuaArriba = lklCuadrosBase.get(iC);
			Cuadro cuaAbajo = lklCuadrosBase.get(iC + iGridCols);
			if (!cuaAbajo.isActive() && cuaArriba.isActive()) {
				cuaAbajo.setValor(cuaArriba.getValor());
				cuaAbajo.setPregunta(cuaArriba.getPregunta());
				cuaAbajo.setColor(cuaArriba.getColor());
				cuaArriba.setActive(false);
				cuaAbajo.setActive(true);

				lklDisponibles.add(iC);
				int iIndexAux = lklUsados.indexOf(iC);
				lklUsados.remove(iIndexAux);

				lklUsados.add(iC + iGridCols);
				iIndexAux = lklDisponibles.indexOf(iC + iGridCols);
				lklDisponibles.remove(iIndexAux);
			}


		}
	}

	public String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public int getCasillas() {
		return this.iCasillas;
	}
}

