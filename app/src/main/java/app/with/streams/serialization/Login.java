package app.with.streams.serialization;


import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("Server")
    private String server;
    @SerializedName("Id")
    private Long id;

    public Login(String server, Long id) {
        this.server = server;
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
