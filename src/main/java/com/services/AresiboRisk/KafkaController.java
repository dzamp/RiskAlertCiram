package com.services.AresiboRisk;

import com.aresibo.avro.alert.risk.RiskAlert;
import com.services.AresiboRisk.clients.Producer;
import com.services.AresiboRisk.clients.RiskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final Producer<RiskAlert> producer;

    @Autowired
    KafkaController( Producer<RiskAlert> producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producer.send(RiskProducer.createRiskAlert(System.currentTimeMillis()));
    }

}