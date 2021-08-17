package app.with.streams.serialization;

public class LoginUser {
 
    private long id;
    private String user;
    private String server;
    public long getId() {
        return id;
    }
    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setId(long id) {
        this.id = id;
    }


    public LoginUser(long id,String user,String server)
    {
        this.id = id;
        this.user = user;
        this.server= server;
    }
    


}
