package pe.com.dackng.example.valve;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
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
public class Valve {
	@Id
	private ObjectId valveId;
	
	@Field
	private String code;
	
	@Field
	private String name;
	
	@Field
	private String state;
	
	@CreatedBy
	@Field
	private String createdBy;
	
	@Field
    @CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedBy
	@Field
	private String updatedBy;
	
	@Field
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
}
