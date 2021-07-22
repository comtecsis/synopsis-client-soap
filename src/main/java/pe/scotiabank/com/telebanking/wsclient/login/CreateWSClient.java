
package pe.scotiabank.com.telebanking.wsclient.login;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

import pe.com.scotiabank.tbk.logincreatews.service.CreateSession;
import pe.com.scotiabank.tbk.logincreatews.service.CreateSessionResponse;
import pe.com.scotiabank.tbk.logincreatews.service.DestroySession;
import pe.com.scotiabank.tbk.logincreatews.service.DestroySessionResponse;
import pe.com.scotiabank.tbk.logincreatews.service.ExtractSession;
import pe.com.scotiabank.tbk.logincreatews.service.ExtractSessionResponse;
import pe.com.scotiabank.tbk.logincreatews.service.ObjectFactory;
import pe.com.scotiabank.tbk.logincreatews.service.SessionContainer;

public class CreateWSClient implements Serializable
{

    private static final long serialVersionUID = -4184457396269278149L;

    private WebServiceMessageCallback callback;

    private transient WebServiceTemplate template;

    protected String uri;

    protected ObjectFactory factory;

    public CreateWSClient(WebServiceTemplate template, String uri, WebServiceMessageCallback callback)
    {
        this.callback = callback;
        this.uri = uri;
        factory = new ObjectFactory();
    }

    public CreateWSClient(WebServiceTemplate template)
    {
        this(template, null, null);
    }

    public CreateWSClient(String uri)
    {
        this(null, uri, null);
    }

    public WebServiceMessageCallback getCallback()
    {
        return callback;
    }

    public void setCallback(WebServiceMessageCallback callback)
    {
        this.callback = callback;
    }

    /**
     * @return the uri
     */
    public String getUri()
    {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    @SuppressWarnings("unchecked")
    public CreateSessionResponse createSession(SessionContainer value)
    {
        CreateSession request = new CreateSession();
        request.setContainer(value);
        return ((JAXBElement<CreateSessionResponse>) template.marshalSendAndReceive(uri,
                factory.createCreateSession(request), null)).getValue();
    }

    @SuppressWarnings("unchecked")
    public ExtractSessionResponse extractSession(String contract, String user)
    {
        ExtractSession request = new ExtractSession();
        request.setContract(contract);
        request.setUser(user);
        return ((JAXBElement<ExtractSessionResponse>) template.marshalSendAndReceive(uri,
                factory.createExtractSession(request), null)).getValue();
    }

    @SuppressWarnings("unchecked")
    public DestroySessionResponse destroySession()
    {
        DestroySession request = new DestroySession();
        return ((JAXBElement<DestroySessionResponse>) template.marshalSendAndReceive(uri,
                factory.createDestroySession(request), null)).getValue();
    }

}
