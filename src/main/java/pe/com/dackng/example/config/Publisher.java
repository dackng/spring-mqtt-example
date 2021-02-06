package pe.com.dackng.example.config;

import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Publisher implements Callable<Void>{
	
	private IMqttClient channel;
    private String topic;
    private String object;

    @Override
    public Void call() throws Exception {
        
        if ( !channel.isConnected()) {
            log.error("[ERROR] Client(channel) not connected.");
            return null;
        }
            
        MqttMessage msg = createMessage();
        msg.setQos(0);
        msg.setRetained(true);
        channel.publish(topic,msg);        
        
        return null;        
    }
    
    private MqttMessage createMessage() {       
        byte[] payload = object.getBytes();        
        MqttMessage msg = new MqttMessage(payload); 
        return msg;
    }
}
