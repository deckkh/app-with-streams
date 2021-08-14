package app.with.streams.serialization;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Name")
    private String name;
    @SerializedName("Id")
    private Long id;

    public User(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}