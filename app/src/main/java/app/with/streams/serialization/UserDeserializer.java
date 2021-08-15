package app.with.streams.serialization;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Deserializer;


public class UserDeserializer implements Deserializer<User> {
    private Gson gson =
        new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
  
    @Override
    public User deserialize(String topic, byte[] bytes) {
      if (bytes == null) return null;
      return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), User.class);
    }
  }
  
