
package pe.scotiabank.com.telebanking.wsclient.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;

import pe.com.scotiabank.tbk.logincreatews.service.CreateSession;
import pe.com.scotiabank.tbk.logincreatews.service.CreateSessionResponse;
import pe.com.scotiabank.tbk.logincreatews.service.SessionContainer;
import pe.scotiabank.com.telebanking.wsclient.login.bean.CreateWsResponse;

public class CreateWSClientSession extends CreateWSClient
{

    private static final long serialVersionUID = -4184457396269278149L;

    public CreateWSClientSession(WebServiceTemplate template, String uri, WebServiceMessageCallback callback)
    {
        super(template, uri, callback);
    }

    public CreateWSClientSession(WebServiceTemplate template)
    {
        super(template);
    }

    public CreateWSClientSession(String uri)
    {
        super(uri);
    }

    public CreateWsResponse createSession2(SessionContainer value)
            throws ClientProtocolException, IOException, JAXBException
    {
        CreateSession request = factory.createCreateSession();
        request.setContainer(value);

        CreateWsResponse response = callWebService("", marshall(request));
        return response;
    }

    /* Obtencion de valor de session */

    public static final String HEADER_NAME = "Set-Cookie";
    public static final Pattern PATTERN_JSESSIONID = Pattern.compile("JSESSIONID[^;=]*=([^;]+)");

    private CreateWsResponse callWebService(String soapAction, String soapEnvBody)
    {
        CreateWsResponse wsResponse = null;
        try
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            StringEntity stringEntity = new StringEntity(soapEnvBody, "UTF-8");
            stringEntity.setChunked(true);

            // Request parameters and other properties.
            HttpPost httpPost = new HttpPost(this.uri);
            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Accept", "text/xml");
            httpPost.addHeader("Content-type", "text/xml");
            httpPost.addHeader("SOAPAction", soapAction);

            HttpResponse response = httpclient.execute(httpPost);
            wsResponse = new CreateWsResponse();

            wsResponse.setSessionId(extractJSESSIONID(response.getAllHeaders()));

            org.apache.http.HttpEntity entity = response.getEntity();

            String strResponse = null;
            if (entity != null)
            {
                XMLInputFactory xif = XMLInputFactory.newFactory();
                XMLStreamReader xsr = xif.createXMLStreamReader(entity.getContent());
                xsr.nextTag();
                xsr.nextTag();
                xsr.nextTag();
                JAXBContext jaxbcontext = JAXBContext.newInstance(CreateSessionResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
                JAXBElement<CreateSessionResponse> responseWs = jaxbUnmarshaller.unmarshal(xsr,
                        CreateSessionResponse.class);
                strResponse = responseWs.getValue().getCreateSessionResult();
            }

            wsResponse.setResponse(strResponse);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return wsResponse;
    }

    private String marshall(CreateSession o)
    {
        try
        {
            JAXBContext jaxbcontext = JAXBContext.newInstance(CreateSession.class);
            Marshaller marshaller = jaxbcontext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            QName qName = new QName("http://service.logincreatews.tbk.scotiabank.com.pe", "createSession");
            JAXBElement<CreateSession> root = new JAXBElement<>(qName, CreateSession.class, o);

            MessageFactory mfactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = mfactory.createMessage();

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            marshaller.marshal(root, doc);
            soapMessage.getSOAPBody().addDocument(doc);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapMessage.writeTo(out);
            String strMsg = new String(out.toByteArray(), "UTF-8");

            return strMsg;

        }
        catch (Exception e)
        {   
            throw new RuntimeException(e);
        }
    }

    private String extractJSESSIONID(Header[] headers)
    {
        for (Header h : headers)
        {
            if (HEADER_NAME.equalsIgnoreCase(h.getName()))
            {
                Matcher m = PATTERN_JSESSIONID.matcher(h.getValue());
                if (m.find())
                {
                    return m.group(1);
                }
            }
        }
        return "";
    }

}
