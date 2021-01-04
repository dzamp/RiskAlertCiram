package com.services.AresiboRisk.clients;

import com.aresibo.avro.alert.risk.RiskAlert;
import com.services.AresiboRisk.AresiboRiskApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public interface Producer<T> {
    static final Logger LOGGER = LogManager.getLogger(AresiboRiskApplication.class);

    public void send(T message);

    default void callback(T message, ListenableFuture<SendResult<String, T>> future) {

        future.addCallback(new ListenableFutureCallback<SendResult<String, T>>() {

            @Override
            public void onSuccess(SendResult<String, T> result) {
                LOGGER.info("The message has been produced");
                LOGGER.info("Sent message=[" + result.getProducerRecord().value().toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
