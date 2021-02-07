package pe.com.dackng.example.subscribers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.com.dackng.example.shared.Constants.ValveState;
import pe.com.dackng.example.shared.YamlConfig;
import pe.com.dackng.example.valve.ValveRepository;

@Component
@Slf4j
public class ValveSubscriber implements CommandLineRunner{

	IMqttClient channel;
	YamlConfig yamlConfig;
	ValveRepository valveRepository;
	
	public ValveSubscriber(@Qualifier("subscriberChannel") IMqttClient channel, YamlConfig yamlConfig
			, ValveRepository valveRepository) {
		this.channel = channel;
		this.yamlConfig = yamlConfig;
		this.valveRepository = valveRepository;
	}
	
	@Override
    public void run(String... args) throws Exception {
        CountDownLatch receivedSignal = new CountDownLatch(1);
        onOpenedValve(receivedSignal);
        onClosedValve(receivedSignal);
        
        receivedSignal.await(1, TimeUnit.MINUTES);
        
        log.info("ValveSubscriber subscribe successfuly !");
    }
	
	private void onOpenedValve(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getOpenedValve(), (topic, msg) -> {
			String payload = new String(msg.getPayload());
			
            log.info("[OPENED VALVE] Message received: topic={}, payload={}", topic, payload);
            
            changeValveState(ValveState.WAITING_ON.getValue(), ValveState.ON.getValue());
            
            receivedSignal.countDown();
        });
	}
	
	private void onClosedValve(CountDownLatch receivedSignal) throws Exception {
		channel.subscribe(yamlConfig.getBindings().getClosedValve(), (topic, msg) -> {
			String payload = new String(msg.getPayload());
            
			log.info("[CLOSED VALVE] Message received: topic={}, payload={}", topic, payload);
            
            changeValveState(ValveState.WAITING_OFF.getValue(), ValveState.OFF.getValue());
            
            receivedSignal.countDown();
        });
	}
	
	private void changeValveState(String initialState, String finalState) {
		valveRepository
			.findByState(initialState)
			.stream()
			.findFirst()
			.ifPresent(valve -> valve.setState(finalState));
	}
}
