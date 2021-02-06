package pe.com.dackng.example.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import pe.com.dackng.example.requests.SaveValveRequest;
import pe.com.dackng.example.valve.Valve;

@Mapper
public interface ValveMapper {

	public static final ValveMapper INSTANCE = Mappers.getMapper(ValveMapper.class);
	
	@Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
	Valve from(SaveValveRequest source);
}
