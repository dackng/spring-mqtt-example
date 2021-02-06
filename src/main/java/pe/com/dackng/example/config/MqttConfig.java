package pe.com.dackng.example.config;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import pe.com.dackng.example.shared.Constants.ClientIDPrefix;
import pe.com.dackng.example.shared.YamlConfig;

@Configuration
@AllArgsConstructor
public class MqttConfig {
	
	YamlConfig yamlConfig;
	
	@Bean
	@ConfigurationProperties(prefix = "mqtt.options")
	public MqttConnectOptions mqttConnectOptions() {
		return new MqttConnectOptions();
	}
	
	@Bean(name = "publisherChannel")
	public IMqttClient publisherChannel() throws MqttException {
		String clientID = createClientID(ClientIDPrefix.PUBLISHER.getValue());
		IMqttClient mqttClient = new MqttClient(yamlConfig.getHostname(), clientID);
		mqttClient.connect(mqttConnectOptions());
		return mqttClient;
	}

	@Bean(name = "subscriberChannel")
	public IMqttClient subscriberChannel() throws MqttException {
		String clientID = createClientID(ClientIDPrefix.SUBSCRIBER.getValue());
		IMqttClient mqttClient = new MqttClient(yamlConfig.getHostname(), clientID);
		mqttClient.connect(mqttConnectOptions());
		return mqttClient;
	}
	
	public static String createClientID(String prefixCode) {
		return prefixCode + UUID.randomUUID();
	} 
}
