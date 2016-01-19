package introsde.finalproject.storage;

import introsde.finalproject.localdatabase.LocalDatabaseService;
import introsde.finalproject.localdatabase.User;
import introsde.finalproject.localdatabase.Goal;
import introsde.finalproject.localdatabase.Measure;

import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;

@WebService(endpointInterface = "introsde.finalproject.storage.Service", serviceName="StorageService")
public class ServiceImpl implements Service {

    private static final LocalDatabaseService localDatabase = new LocalDatabaseService();

    @Override
    public List<String> getUserGoals(Long id) {
        User user = getUser(id);
        List<String> goals = new ArrayList<>();
        for(Goal g : user.getGoals()) {
            goals.add(g.getGoalType() + ":" + ((g.getGoalValue() > 0) ? Double.toString(g.getGoalValue()) : "n/a"));
        }
        return goals;
    }

    @Override
    public List<String> getUserInfo(Long id) {
        User user = getUser(id);
        List<String> info = new ArrayList<>();
        info.add("First name: " + user.getFirstname());
        info.add("Last name: " + user.getLastname());
        info.add("Birthdate: " + user.getBirthdate());
        for (Measure m : user.getCurrentHealth()) {
            if(m.getMeasureType().equals("height")) {
                info.add("Height: " + m.getMeasureValue());
            }
            if(m.getMeasureType().equals("weight")) {
                info.add("Weight: " + m.getMeasureValue());
            }
        }
        return info;
    }
    
    @Override
    public List<String> getUserMeasureHistory(Long id) {
        User user = getUser(id);
        ArrayList<String> result = new ArrayList<>();
        for (Measure measure : user.getHealthHistory()) {
            result.add(measure.getDateRegistered()+":\n\tmeasure: "+measure.getMeasureType()+"\n\tvalue: "+measure.getMeasureValue()+"\n");
        }
        return result;
    }

    @Override
    public User getUser(Long id) {
        return localDatabase.getServiceImplPort().getUser(id);
    }

    @Override
    public void saveUser(User user) {
        localDatabase.getServiceImplPort().saveUser(user);
    }

    @Override
    public String getRandomQuote() {
        try {
            ClientConfig clientConfig = new ClientConfig();
            Client client = ClientBuilder.newClient(clientConfig);

            String hostname = InetAddress.getLocalHost().getHostAddress();
            String port = "7004";
            URI uri = UriBuilder.fromUri("http://" + hostname + ":" + port + "/quote/random").build();

            WebTarget service = client.target(uri);
            return service.request().accept(MediaType.TEXT_PLAIN).get(String.class);    
        } catch(UnknownHostException ex) {
            return "";
        }
    }

    @Override
    public String getCongratsPicture() {
        try {
            ClientConfig clientConfig = new ClientConfig();
            Client client = ClientBuilder.newClient(clientConfig);

            String hostname = InetAddress.getLocalHost().getHostAddress();
            String port = "7005";
            URI uri = UriBuilder.fromUri("http://" + hostname + ":" + port + "/picture/random").build();

            WebTarget service = client.target(uri);
            return service.request().accept(MediaType.TEXT_PLAIN).get(String.class);    
        } catch(UnknownHostException ex) {
            return "";
        }
    }
}
