package com.flaminiovilla.geopic.component;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Sfrutto le api pubbliche di jopen
 * per ottenere la geolocalizzazione da latitudine e longitudine
 * https://opencagedata.com/
 * Le api sono a pagamento quindi abbiamo deciso di strutturare internamente
 * il servizio di geolocalizzazione
 */
@Deprecated
@Component
public class ReverseGeoCoderUtil {

    //effettua la chiamata a jopen per ricevere info sulla posizione
    public String call(Double latitude, Double longitude){
        try {
            JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("---------KEY-----------");

            JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);

            request.setLanguage("it"); // prioritize results in a specific language using an IETF format language code
            request.setNoDedupe(true); // don't return duplicate results
            request.setLimit(5); // only return the first 5 results (default is 10)
            request.setMinConfidence(3); // restrict to results with a confidence rating of at least 3 (out of 10)

            JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

            return response.getResults().get(0).getFormatted();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    //parsing della risposta
    public JSONObject nameCity(Double latitude, Double longitude) {

        String test = call(latitude, longitude);

        String delims = "[,]+";
        String delimsSpace = "[ ]+";

        String[] tokens = test.split(delims);
        String data = tokens[tokens.length - 2];
        String[] city = data.split(delimsSpace);
        String name = "";

       for (int i = 0 ; i < city.length -1 ; i++) {
           if (!isNumeric(city[i]))
               name += city[i] ;
       }
        JSONObject jsonString = new JSONObject()
                .put("name", name);
        jsonString.get("name");
        return jsonString;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}