package app.with.streams.serialization;


import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;

class UserSerializer implements Serializer<User> {
  private Gson gson = new Gson();

  @Override
  public byte[] serialize(String topic, User user) {
    if (user == null) return null;
    return gson.toJson(user).getBytes(StandardCharsets.UTF_8);
  }
}