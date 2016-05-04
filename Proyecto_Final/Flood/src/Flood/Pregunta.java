package Flood;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;


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
     *
     * @param sPregunta
     * @param sRespuesta
     * @param iPuntos
     * @throws FontFormatException
     * @throws IOException
     */
    public Pregunta(String sPregunta, String sRespuesta, int iPuntos) throws FontFormatException, IOException {

        this.sPregunta = sPregunta;
        this.sRespuesta = sRespuesta;
        this.iPuntos = iPuntos;
    }

    /**
     *
     *
     * @return 
     */
    public String getPregunta() {
        return sPregunta;
    }

    /**
     *
     * @return
     */
    public String getRespuesta() {
        return sRespuesta;
    }

    /**
     *
     * @return
     */
    public int getPuntos() {
        return iPuntos;
    }

}
