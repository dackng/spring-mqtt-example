package pe.com.dackng.example.requests;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveValveRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String code;
	
	@NotNull
	private String name;
}
