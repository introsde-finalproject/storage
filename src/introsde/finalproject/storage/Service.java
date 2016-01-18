package introsde.finalproject.storage;

import introsde.finalproject.localdatabase.User;

import java.net.UnknownHostException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface Service {

    @WebMethod(operationName="getUserGoals")
    @WebResult(name="goals")
    public List<String> getUserGoals(@WebParam(name="userId") Long id);

    @WebMethod(operationName="getUserInfo")
    @WebResult(name="userInfo")
    public List<String> getUserInfo(@WebParam(name="userId") Long id);

    @WebMethod(operationName="getUserMeasureHistory")
    @WebResult(name="measureHistory")
    public List<String> getUserMeasureHistory(@WebParam(name="userId") Long id);

    @WebMethod(operationName="getUser")
    @WebResult(name="user") 
    public User getUser(@WebParam(name="userId") Long id);

    @WebMethod(operationName="saveUser")
    @WebResult(name="user") 
    public void saveUser(@WebParam(name="user") User user);

    @WebMethod(operationName="getRandomQuote")
    @WebResult(name="randomQuote") 
    public String getRandomQuote();

    @WebMethod(operationName="getCongratsPicture")
    @WebResult(name="asciiPicture") 
    public String getCongratsPicture();
    
}

