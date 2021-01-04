package com.services.AresiboRisk.clients;

import com.aresibo.avro.alert.risk.RiskAlert;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.schema-registry}")
    private String schemaResgistryUrl;


    @Bean("riskKafkaTemplate")
    public KafkaTemplate<String, RiskAlert> riskKafkaTemplate() {
        return new KafkaTemplate<>(riskAlertProducerFactory());
    }

    public Map<String, Object> avroProducerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,schemaResgistryUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        return props;
    }


    public ProducerFactory<String, RiskAlert> riskAlertProducerFactory() {
        return new DefaultKafkaProducerFactory<>(avroProducerProperties());
    }

}
