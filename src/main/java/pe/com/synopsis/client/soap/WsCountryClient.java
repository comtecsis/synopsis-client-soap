
package pe.com.synopsis.client.soap;

import java.io.Serializable;

import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import ws.synopsis.guides.gs_producing_web_service.AddCountryRequest;
import ws.synopsis.guides.gs_producing_web_service.AddCountryResponse;
import ws.synopsis.guides.gs_producing_web_service.Currency;
import ws.synopsis.guides.gs_producing_web_service.GetCountriesRequest;
import ws.synopsis.guides.gs_producing_web_service.GetCountriesResponse;
import ws.synopsis.guides.gs_producing_web_service.GetCountryRequest;
import ws.synopsis.guides.gs_producing_web_service.GetCountryResponse;
import ws.synopsis.guides.gs_producing_web_service.ObjectFactory;

public class WsCountryClient implements Serializable
{

    private static final long serialVersionUID = -4184457396269278149L;

    private final WebServiceMessageCallback callback;
    private final transient WebServiceTemplate template;
    private final String uri;
    private final ObjectFactory factory;

    public WsCountryClient(WebServiceTemplate template, String uri, WebServiceMessageCallback callback)
    {
        this.template = template;
        this.callback = callback;
        this.uri = uri;
        factory = new ObjectFactory();
    }

    //@SuppressWarnings("unchecked")
    public GetCountryResponse findByName(String value)
    {
        GetCountryRequest request = factory.createGetCountryRequest();
        request.setName(value);
        //return (GetCountryResponse) template.marshalSendAndReceive(uri, value, callback);
        return ((GetCountryResponse) template.marshalSendAndReceive(uri, request, callback));
    }
    
    public GetCountriesResponse getCountries() {
    	GetCountriesRequest request = factory.createGetCountriesRequest();
		return ((GetCountriesResponse) template.marshalSendAndReceive(uri,request,callback));
	}
    
    public AddCountryResponse addCountry(String name, int population, String capital, Currency currency) {
    	AddCountryRequest request = factory.createAddCountryRequest();
    	request.setName(name);
    	request.setPopulation(population);
    	request.setCapital(capital);
    	request.setCurrency(currency);
    	return ((AddCountryResponse)template.marshalSendAndReceive(uri,request,callback));
    }

}
