package ru.mailprotector.kafkamock.Controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mailprotector.kafkamock.KafkaMockApplication;
import ru.mailprotector.kafkamock.Logging.Log;
import ru.mailprotector.kafkamock.Model.RequestDTO;

@RestController
public class MainController {


    @PostMapping(
            value="send",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
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
            Log.Info("Request passed and took " + String.valueOf(System.nanoTime() - start) + "nanosec");
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
