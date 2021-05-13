/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.actions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.http.IBMAPI;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.util.Constants_Attributes;

/**
 *
 * @author umansilla
 */
public class GatherEmocionesActions {

    private final HttpServletResponse response;

    public GatherEmocionesActions(HttpServletResponse response) {
        this.response = response;
    }

    public void analizarSentimientos(String speechResult, String url) throws IOException {
        if (speechResult != null) {
            JSONObject jsonTraductor = new IBMAPI().traducirTextoDeEspaniolAIngles(speechResult);
            new EscribirEnLog().crearLog(jsonTraductor.toString(2), url);
            if (jsonTraductor.has("translations")) {
                String translation = jsonTraductor.getJSONArray("translations").getJSONObject(0).getString("translation");
                JSONObject jsonSentimientos = new IBMAPI().obtenerSentimientos(translation);
                new EscribirEnLog().crearLog(jsonSentimientos.toString(2), url);
                if (jsonSentimientos.has("document_tone")) {
                    if (jsonSentimientos.getJSONObject("document_tone").has("tones")) {
                        String tono = jsonSentimientos.getJSONObject("document_tone").getJSONArray("tones").getJSONObject(0).getString("tone_name");
                        switch (tono) {
                            case "Anger":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes enojado</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Fear":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes con miedo</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Joy":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes feliz</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Sadness":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes triste</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Analytical":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes analítico</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Confident":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes confidente</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            case "Tentative":
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">He detectado que te sientes surioso</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                            default:
                                response.getWriter().println("<Response>\n"
                                        + "	<Say voice=\"female\" language=\"es\">No he detectado ninguna emoción favor de intentar nuevamente</Say>\n"
                                        + "  	<Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                        + "</Response>");
                                break;
                        }

                    }
                }
            } else {
                response.getWriter().println("<Response>\n"
                        + "    <Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                        + "</Response>\n"
                        + "");
            }

        } else {
            response.getWriter().println("<Response>\n"
                    + "    <Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                    + "</Response>\n"
                    + "");
        }
    }

}
