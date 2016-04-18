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

    /**
     * Cuadro
     *
     */

    public Pregunta(String sPregunta, String sRespuesta) {
        this.sPregunta = sPregunta;
        this.sRespuesta = sRespuesta;
    }

    /**
     *
     *
     */
    public int getPregunta() {
        return sPregunta;
    }
    public int getRespuesta() {
        return sRespuesta;
    }

}
