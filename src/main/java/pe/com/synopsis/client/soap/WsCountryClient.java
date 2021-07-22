
package pe.com.synopsis.client.soap;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

import io.spring.guides.gs_producing_web_service.ObjectFactory;

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
    public GetCountryResponse createSession(GetCountryRequest value)
    {
        return (GetCountryResponse) template.marshalSendAndReceive(uri, value, callback);
    }

}
