package app.with.streams.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class LoginSerdes  implements Serde<Login> {
    @Override
    public Serializer<Login> serializer() {
      return new LoginSerializer();
    }
  
    @Override
    public Deserializer<Login> deserializer() {
      return new LoginDeserializer();
    }
}
