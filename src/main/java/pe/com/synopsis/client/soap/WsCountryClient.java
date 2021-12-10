
package pe.com.synopsis.client.soap;

import java.io.Serializable;

import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import ws.synopsis.guides.gs_producing_web_service.*;

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

    @SuppressWarnings("unchecked")
    public GetCountryResponse findByName(String name)
    {
        GetCountryRequest request = factory.createGetCountryRequest();
            request.setName(name);
        return ((GetCountryResponse) template.marshalSendAndReceive(uri, request, callback));
    }

    @SuppressWarnings("unchecked")
    public GetCountriesResponse findAll()
    {
        return ((GetCountriesResponse) template.marshalSendAndReceive(uri, factory.createGetCountriesRequest(), callback));
    }
    @SuppressWarnings("unchecked")
    public AddCountryResponse save( String name, Integer population, String capital, Currency currency)
    {
        AddCountryRequest request = factory.createAddCountryRequest();
            request.setName(name.trim());
            request.setPopulation(population);
            request.setCapital(capital.trim());
            request.setCurrency(currency);
        return ((AddCountryResponse) template.marshalSendAndReceive(uri, request, callback));
    }

    @SuppressWarnings("unchecked")
    public UpdateCountryResponse update( String name, Integer population, String capital, Currency currency)
    {
        UpdateCountryRequest request = factory.createUpdateCountryRequest();
            request.setName(name.trim());
            request.setPopulation(population);
            request.setCapital(capital.trim());
            request.setCurrency(currency);
        return ((UpdateCountryResponse) template.marshalSendAndReceive(uri, request, callback));
    }

    @SuppressWarnings("unchecked")
    public DeleteCountryResponse remove(String name)
    {
        DeleteCountryRequest request = factory.createDeleteCountryRequest();
            request.setName(name);
        return ((DeleteCountryResponse) template.marshalSendAndReceive(uri, request, callback));
    }

}
