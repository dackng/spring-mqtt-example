package pe.com.dackng.example.api;

import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pe.com.dackng.example.config.Publisher;
import pe.com.dackng.example.mappers.ValveMapper;
import pe.com.dackng.example.requests.SaveValveRequest;
import pe.com.dackng.example.requests.UpdateValveRequest;
import pe.com.dackng.example.shared.Constants.ValveState;
import pe.com.dackng.example.shared.Constants.ValveSwitch;
import pe.com.dackng.example.shared.YamlConfig;
import pe.com.dackng.example.valve.Valve;
import pe.com.dackng.example.valve.ValveRepository;

@Service
@Slf4j
public class ValveService {
	IMqttClient channel;
	YamlConfig yamlConfig;
	ValveRepository valveRepository;
	
	public ValveService(@Qualifier("publisherChannel") IMqttClient channel, YamlConfig yamlConfig
			, ValveRepository valveRepository) {
		this.channel = channel;
		this.yamlConfig = yamlConfig;
		this.valveRepository = valveRepository;
	}
	
	@Transactional
	public void save(SaveValveRequest request) {
		valveRepository.save(ValveMapper.INSTANCE.from(request));
	}
	
	@Transactional
	public void startUpdating(UpdateValveRequest request) throws Exception {
		Callable<Void> publisher;
		Valve document = valveRepository.findByCode(request.getCode());
		if(request.getSwitchOn()) {
			publisher = new Publisher(channel, yamlConfig.getBindings().getOpenValve(), ValveSwitch.ON.getValue());
			document.setState(ValveState.WAITING_ON.getValue());
		} else {
			publisher = new Publisher(channel, yamlConfig.getBindings().getCloseValve(), ValveSwitch.OFF.getValue());
			document.setState(ValveState.WAITING_OFF.getValue());
		}
      	publisher.call();
        log.info("Message sent successfuly!");	
	}

}
