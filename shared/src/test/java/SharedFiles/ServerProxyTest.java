package SharedFiles;

import org.junit.Assert;

/**
 * Created by devey on 3/14/17.
 */
public class ServerProxyTest {
    private ServerProxy proxy = new ServerProxy("localhost", "8080");
    @org.junit.Before
    public void setUp() throws Exception {
        proxy.clear();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        Assert.assertNull(proxy.register(request).getMessage());
        Assert.assertEquals(proxy.register(request).getMessage(), "That username is already being used");
    }

    @org.junit.Test
    public void login() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        proxy.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("mdevey91", "password");
        Assert.assertEquals( "mdevey91", proxy.login(loginRequest).getUserName());
    }

    @org.junit.Test
    public void clear() throws Exception {
        Assert.assertEquals(proxy.clear().getMessage(), "Clear succeeded.");
    }

    @org.junit.Test
    public void fill() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        proxy.register(registerRequest);
        FillRequest fillRequest = new FillRequest("mdevey91", 4);
        Assert.assertEquals(proxy.fill(fillRequest).getMessage(), "Successfully added 31 persons and 61 events to the database");
    }

    @org.junit.Test
    public void load() throws Exception {
        PersonResult[] persons = new PersonResult[1];
        EventResult[] events = new EventResult[1];
        RegisterRequest[] users = new RegisterRequest[1];
        RegisterRequest user = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        EventResult event = new EventResult("mdevey91", "rgh40bj4", "lj34lhs", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        PersonResult person = new PersonResult("lj34lhs", "mdevey91", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        persons[0] = person;
        events[0] = event;
        users[0] = user;
        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        Assert.assertEquals(proxy.load(loadRequest).getMessage(), "Successfully added 1 users, 1 persons, and 1 events to the database.");
    }

    @org.junit.Test
    public void person() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        String auth_token = proxy.register(registerRequest).getAuthToken();
        Assert.assertEquals(proxy.person("blergFace", auth_token).getMessage(), "That person does not exist");
    }

    @org.junit.Test
    public void getAllPersons() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        String auth_token = proxy.register(registerRequest).getAuthToken();
        Assert.assertEquals(proxy.getAllPersons(auth_token).getData().size(), 31);
    }

    @org.junit.Test
    public void event() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        String auth_token = proxy.register(registerRequest).getAuthToken();
        Assert.assertEquals(proxy.event("blergFace", auth_token).getMessage(), "That event does not exist");
    }

    @org.junit.Test
    public void getAllResults() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("mdevey91", "password", "mdevey91@gmail.com", "Devey", "Matt", "m", null);
        String auth_token = proxy.register(registerRequest).getAuthToken();
        Assert.assertEquals(proxy.getAllEvents(auth_token).getData().size(), 61);
    }

}