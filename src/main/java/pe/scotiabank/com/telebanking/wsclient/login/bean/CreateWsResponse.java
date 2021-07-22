
package pe.scotiabank.com.telebanking.wsclient.login.bean;

public class CreateWsResponse
{
    String sessionId;
    String response;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

}
