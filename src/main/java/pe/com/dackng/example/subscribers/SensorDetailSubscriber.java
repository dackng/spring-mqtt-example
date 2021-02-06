package pe.com.dackng.example.subscribers;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.com.dackng.example.sensordetail.SensorDetail;
import pe.com.dackng.example.sensordetail.SensorDetailRepository;
import pe.com.dackng.example.shared.Constants.Parameter;
import pe.com.dackng.example.shared.Constants.PayloadIndex;
import pe.com.dackng.example.shared.YamlConfig;

@Component
@Slf4j
public class SensorDetailSubscriber implements CommandLineRunner{

	IMqttClient channel;
	YamlConfig yamlConfig;
	SensorDetailRepository sensorDetailRepository;
	
	public SensorDetailSubscriber(@Qualifier("subscriberChannel") IMqttClient channel, YamlConfig yamlConfig
			, SensorDetailRepository sensorDetailRepository) {
		this.channel = channel;
		this.yamlConfig = yamlConfig;
		this.sensorDetailRepository = sensorDetailRepository;
	}
	
	@Override
    public void run(String... args) throws Exception {
        CountDownLatch receivedSignal = new CountDownLatch(1);
        this.onLandHumidity(receivedSignal);
        this.onRainIntensity(receivedSignal);

        receivedSignal.await(1, TimeUnit.MINUTES);
        
        log.info("SensorDetailSubscriber subscribe successfuly !");
    }
	
	private void onLandHumidity(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getHumidity(), (topic, msg) -> {
            String payload = new String(msg.getPayload());
            
            log.info("[LAND HUMIDITY] Message received: topic={}, payload={}", topic, payload);
            
            sensorDetailRepository.save(this.convert(payload, Parameter.LAND_HUMIDITY.getValue()));
            
            receivedSignal.countDown();
        });
	}
	
	private void onRainIntensity(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getRain(), (topic, msg) -> {
			String payload = new String(msg.getPayload());
            
			log.info("[RAIN INTENSITY] Message received: topic={}, payload={}", topic, payload);
            
			sensorDetailRepository.save(this.convert(payload, Parameter.RAIN_INTENSITY.getValue()));
            
			receivedSignal.countDown();
        });
	}
	
	private SensorDetail convert(String payload, String parameter) {
		String[] array = payload.split(",");
		return SensorDetail.builder()
				.code(array[PayloadIndex.SENSOR_CODE.getValue()])
				.createdAt(LocalDateTime.now())
				.parameter(parameter)
				.value(Double.valueOf(array[PayloadIndex.VALUE.getValue()]))
				.build();
	}
}
