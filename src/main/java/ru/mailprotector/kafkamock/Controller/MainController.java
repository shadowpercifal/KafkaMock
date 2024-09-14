package ru.mailprotector.kafkamock.Controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mailprotector.kafkamock.KafkaMockApplication;
import ru.mailprotector.kafkamock.Logging.Log;
import ru.mailprotector.kafkamock.Model.RequestDTO;
import ru.mailprotector.kafkamock.Model.ResponseDTO;

import java.util.Properties;
import java.util.UUID;

@RestController
public class MainController {


    @PostMapping(
            value="send",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<?> sendMessage(@RequestBody RequestDTO request){
        try{
            long start = System.nanoTime();
            Log.Debug("Processing request");
            Log.Debug("********** Request Info **********", request);
            if (request.getMessage().length() == 0){
                Log.Debug("Message empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message is empty!");
            }
            SendKafkaMessage(request.getMessage());
            final ResponseDTO response = new ResponseDTO(true);
            Log.Info("Request passed and took " + String.valueOf(System.nanoTime() - start) + "nanosec");
            Log.Debug("========== Response ==========" + response);
            return ResponseEntity.ok("success");
        }
        catch (Exception ex){
            Log.Error("Request failed! Error:", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }
    public static void SendKafkaMessage(String message){

        if (KafkaMockApplication.producer == null){
            return;
        }
        // Create a producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("Group3", "mock", message);

        // Send the record
        KafkaMockApplication.producer.send(record);
    }
}
