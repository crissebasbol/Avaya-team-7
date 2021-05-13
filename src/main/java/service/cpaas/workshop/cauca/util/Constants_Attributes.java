/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.util;

/**
 *
 * @author umansilla
 */
public class Constants_Attributes {
    //CUENTA API
    public final static String CUENTA_API = System.getenv("CUENTA_API");
    public final static String TOKEN_CUENTA_API = System.getenv("TOKEN_CUENTA_API");
    public final static String CPAAS_WORK_SHOP_API_FQDN = System.getenv("CPAAS_WORK_SHOP_API_FQDN");
    //EJEMPLO NOTIFICACION POR VENCIMIENTO DE PAGO
    public final static String NVP_SALUDO_INICIAL = System.getenv("NVP_SALUDO_INICIAL");
    public final static String NVP_DESPEDIDA = System.getenv("NVP_DESPEDIDA");
    //EJEMPLO INBOUND XML CON BACKEND
    public final static String IVR_WITH_BACKEND_BIENVENIDA = System.getenv("IVR_WITH_BACKEND_BIENVENIDA");
    public final static String IVR_WITH_BACKEND_HANGUP = System.getenv("IVR_WITH_BACKEND_HANGUP");
    public final static String IVR_WITH_BACK_END_PROMOCIONES = System.getenv("IVR_WITH_BACK_END_PROMOCIONES");
    public final static String IVR_WITH_BACK_END_AGENTE = System.getenv("IVR_WITH_BACK_END_AGENTE");
    public final static String IVR_WITH_BACK_END_NUMERO_DE_CUENTA = System.getenv("IVR_WITH_BACK_END_NUMERO_DE_CUENTA");
    //EJEMPLO CONFERENCIA
    public final static String CONFERENCE_INBOUND_XML_URL = System.getenv("CONFERENCE_INBOUND_XML_URL");
    //EMAIL
    public final static String SEND_EMAIL_COLLABORATORY_FQDN = System.getenv("SEND_EMAIL_COLLABORATORY_FQDN");
    //SENTIMIENTOS
    public final static String GATHER_EMOCIONES_INBOUND_XML = System.getenv("GATHER_EMOCIONES_INBOUND_XML");
    
    //IBM TRADUCTOR
    public final static String IBM_TRADUCTOR_URL = System.getenv("IBM_TRADUCTOR_URL");
    public final static String IBM_TRADUCTOR_API_KEY = System.getenv("IBM_TRADUCTOR_API_KEY");
    
    //IBM TONE ANALIZER
    public final static String IBM_TONE_ANALIZER_URL = System.getenv("IBM_TONE_ANALIZER_URL");
    public final static String IBM_TONE_ANALIZER_API_KEY = System.getenv("IBM_TONE_ANALIZER_API_KEY");
    
}
