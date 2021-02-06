package pe.com.dackng.example.subscribers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.com.dackng.example.shared.YamlConfig;

@Component
@Slf4j
public class ValveSubscriber implements CommandLineRunner{

	IMqttClient channel;
	YamlConfig yamlConfig;
	
	public ValveSubscriber(@Qualifier("subscriberChannel") IMqttClient channel, YamlConfig yamlConfig) {
		this.channel = channel;
		this.yamlConfig = yamlConfig;
	}
	
	@Override
    public void run(String... args) throws Exception {
        CountDownLatch receivedSignal = new CountDownLatch(1);
        this.onOpenedValve(receivedSignal);
        this.onClosedValve(receivedSignal);
        
        receivedSignal.await(1, TimeUnit.MINUTES);
        
        log.info("ValveSubscriber subscribe successfuly !");
    }
	
	private void onOpenedValve(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getOpenedValve(), (topic, msg) -> {
            byte[] payload = msg.getPayload();
            log.info("[OPENED VALVE] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
	}
	
	private void onClosedValve(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getClosedValve(), (topic, msg) -> {
            byte[] payload = msg.getPayload();
            log.info("[CLOSED VALVE] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
	}
}
