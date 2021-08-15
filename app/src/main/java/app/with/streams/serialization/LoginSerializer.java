package app.with.streams.serialization;


import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;

class LoginSerializer implements Serializer<Login> {
  private Gson gson = new Gson();

  @Override
  public byte[] serialize(String topic, Login Login) {
    if (Login == null) return null;
    return gson.toJson(Login).getBytes(StandardCharsets.UTF_8);
  }
}