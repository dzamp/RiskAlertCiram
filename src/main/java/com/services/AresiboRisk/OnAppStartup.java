package com.services.AresiboRisk;

import com.aresibo.avro.alert.common.Level;
import com.aresibo.avro.alert.common.Location;
import com.aresibo.avro.alert.common.Point;
import com.aresibo.avro.alert.common.Status;
import com.aresibo.avro.alert.risk.CiramDetails;
import com.aresibo.avro.alert.risk.RiskAlert;
import com.services.AresiboRisk.clients.RiskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;

@Component
public class OnAppStartup {

    @Autowired
    public RiskProducer produder;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("APP START");
        System.out.println("Sending message to RiskAlert topic");
        while (true) {
            RiskAlert alert = RiskProducer.createRiskAlert(System.currentTimeMillis());
            produder.send(alert);
        }
    }

}
