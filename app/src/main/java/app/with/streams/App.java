/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package app.with.streams;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.with.streams.serialization.*;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serdes.VoidSerde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());



    public static void main(String[] args) {


        logger.info("Info: Log4j2 Usage");

        MyProperties prop = new MyProperties();

        try {
          prop.getPropValues();

          String user = prop.getPropValue("user");

          System.out.println(user);

          // the builder is used to construct the topology
          StreamsBuilder builder = new StreamsBuilder();

          UserSerdes userSerdes = new UserSerdes();
          LoginSerdes loginSerdes = new LoginSerdes();
          VoidSerde voidSerde = new VoidSerde();



          // read from the source topic, "users"
          // KStream<Void, User> stream = builder.stream(prop.getPropValue("stream-users"),Consumed.with(voidSerde, userSerdes));


    // register the score events stream
    KStream<String, User> stream =
        builder
            .stream("users", 
            Consumed.with(voidSerde, userSerdes))
            // now marked for re-partitioning
            .selectKey((k, v) -> v.getId().toString());;

          // for each record that appears in the source topic,
          // print the value
          stream.foreach(
              (key, value) -> {
                System.out.println("(User) Hello, " + value.getName());
              });

         stream.to("mytopic", Produced.with(Serdes.String(),userSerdes));


         ValueJoiner<Login, User, LoginUser> productJoiner =
         (mylogin, myuser) -> new LoginUser(myuser.getId(),myuser.getName(),mylogin.getServer());

          KStream<String, Login> login = builder.stream(prop.getPropValue("stream-login"),
          Consumed.with(Serdes.String(), loginSerdes)).selectKey((k,v) -> v.getId().toString());


//          KStream<Void, User> onlyPositives = stream.filterNot((key, value) -> value.getId() == 0);


        GlobalKTable<String, User> joinTable = builder.globalTable("mytopic",Consumed.with(Serdes.String(), userSerdes));




              login.foreach(
                  (key, value) -> {
                    System.out.println("(Login) Hello, " + value.getServer());
                  });

          KeyValueMapper<String,Login,String> keyMapper = (leftKey,mylogin) -> { 
            return String.valueOf(mylogin.getId().toString());
          };


          KStream<String,LoginUser> withLogin = login.join(joinTable, keyMapper, productJoiner);

          withLogin.foreach(
            (key, value) -> {
              System.out.println("(LoginUser) Hello, " + value.getServer()+ " from "+value.getUser());
            });


          // you can also print using the `print` operator
          // stream.print(Printed.<String, String>toSysOut().withLabel("source"));

          // set the required properties for running Kafka Streams
          Properties config = new Properties();
          config.put(StreamsConfig.APPLICATION_ID_CONFIG, prop.getPropValue("app-dev-id"));
          config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, prop.getPropValue("kafka-uri"));
          config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
          config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Void().getClass());
          config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

          // build the topology and start streaming
          KafkaStreams streams = new KafkaStreams(builder.build(), config);
          streams.start();

          // close Kafka Streams when the JVM shuts down (e.g. SIGTERM)
          Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
          
              logger.debug("Debug: Program has finished successfully");
              logger.error("Error: Program has errors");

        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }

}
