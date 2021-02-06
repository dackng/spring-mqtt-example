package pe.com.dackng.example.sensordetail;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class SensorDetail {
	@Id
	private ObjectId sensorDetailId;

	@Field
	private String code;// sensor ID from Arduino project
	
	@Field
	private String parameter;// code to know if the record is from rain or humidity
	
	@Field
	private Double value;// sent value from sensor 
	
	@Field
	private LocalDateTime createdAt;
}
