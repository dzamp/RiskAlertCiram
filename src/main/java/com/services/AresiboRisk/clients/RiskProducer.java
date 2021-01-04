package com.services.AresiboRisk.clients;

import com.aresibo.avro.alert.common.Level;
import com.aresibo.avro.alert.common.Location;
import com.aresibo.avro.alert.common.Point;
import com.aresibo.avro.alert.common.Status;
import com.aresibo.avro.alert.risk.CiramDetails;
import com.aresibo.avro.alert.risk.RiskAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.Random;

@Service
public class RiskProducer implements Producer<RiskAlert> {

    private final static String topicName = "RiskAlert";

   @Autowired
   private KafkaTemplate<String, RiskAlert> kafkaTemplate;


    public void send(RiskAlert message) {
        ListenableFuture<SendResult<String, RiskAlert>> future = kafkaTemplate.send(topicName, message);
        callback(message,future);
    }

    public static RiskAlert createRiskAlert(long timestamp) {
        Random rnd = new Random();
        Location.Builder locationBuilder = Location.newBuilder()
                                                   .setType("Point");


        Location valencia = locationBuilder.setCoordinates(Collections.singletonList(new Point(39.500018, -0.472533)))
                                           .build();

        return RiskAlert.newBuilder()
                        .setAlertId(20000)
                        .setAlertLevel(Level.HIGH)
                        .setAlertTitle("Warning - CIRAM")
                        .setAlertText("Unidentified vessel is approaching restricted area")
                        .setCiramDetails(new CiramDetails(Level.LOW, Level.MEDIUM, Level.MEDIUM))
                        .setAlertStartTime(1605886538143L)
                        .setAlertEndTime(1605886549762L)
                        .setAlertStatus(Status.ALERT_ACTIVE)
                        .setTrackedEntityId(1)
                        .setLocation(valencia)
                        .setTimestamp(timestamp)
                        .build();
    }


}
