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

    public Pregunta(String sPregunta, String sRespuesta, int iPuntos) throws FontFormatException, IOException {

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
