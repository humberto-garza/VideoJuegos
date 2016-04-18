package Flood;


/**
 *
 * @author Left & Right
 * @version 1.0
 * @date APR/12/2016
 */

public class Pregunta {

    private String sPregunta;
    private String sRespuesta;
    private int iPuntos;

    /**
     * Cuadro
     *
     */

    public Pregunta(String sPregunta, String sRespuesta, int iPuntos) {
        this.sPregunta = sPregunta;
        this.sRespuesta = sRespuesta;
        this.iPuntos = iPuntos;
    }

    /**
     *
     *
     */
    public String getPregunta() {
        return sPregunta;
    }
    public String getRespuesta() {
        return sRespuesta;
    }
    public int getPuntos() {
        return iPuntos;
    }

}
