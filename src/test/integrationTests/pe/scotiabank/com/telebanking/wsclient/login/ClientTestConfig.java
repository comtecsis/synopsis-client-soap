package pe.scotiabank.com.telebanking.wsclient.login;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import pe.com.scotiabank.tbk.logincreatews.service.SessionContainer;
import pe.scotiabank.com.telebanking.wsclient.login.bean.CreateWsResponse;

@Configuration
public class ClientTestConfig {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("pe.com.scotiabank.tbk.*");
		return marshaller;
	}
	
	@Bean(name="loginWSTemplate")
	public WebServiceTemplate loginWSTemplate(){
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("http://10.237.13.170:9080/tbk-session-create/LoginCreateServices");
		webServiceTemplate.setMarshaller(marshaller());
		webServiceTemplate.setUnmarshaller(marshaller());
		return webServiceTemplate;
	}
	
	@Bean
	public CreateWSClient loginClient(Jaxb2Marshaller marshaller) {
		CreateWSClient client = new CreateWSClient(loginWSTemplate(),"http://10.237.13.170:9080/tbk-session-create/LoginCreateServices",null);
		return client;
	}
	
	@Test
	public void createSession2() throws ClientProtocolException, IOException, JAXBException {
	    CreateWSClientSession client = new CreateWSClientSession("http://10.237.13.170:9080/tbk-session-create/LoginCreateServices");
        SessionContainer request = new SessionContainer();
        CreateWsResponse response = client.createSession2(request);
        System.out.println("COOKIE: "+response.getSessionId());
        System.out.println("RESPONSE: "+response.getResponse());
    }
}
