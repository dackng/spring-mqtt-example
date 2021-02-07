package pe.com.dackng.example.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Constants {
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum ClientIDPrefix{
		PUBLISHER("dackng-api-pub-"), SUBSCRIBER("dackng-api-sub-");
		private String value;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum ValveSwitch{
		ON("1"), OFF("0");
		private String value;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum Parameter{
		LAND_HUMIDITY("landHumidity"), RAIN_INTENSITY("rainIntensity");
		private String value;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum PayloadIndex{
		CLIENT_ID(0), VALUE(1);
		private int value;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum ValveState{
		OFF("VS01-ON"), ON("VS02-OFF"), WAITING_OFF("VS03-WAITING-OFF"), WAITING_ON("VS04-WAITING-ON");
		private String value;
	}
}
