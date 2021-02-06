package pe.com.dackng.example.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pe.com.dackng.example.requests.SaveValveRequest;
import pe.com.dackng.example.requests.UpdateValveRequest;

@RestController
@RequestMapping("/api/v1/valve")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.PATCH })
@AllArgsConstructor
public class ValveController {

	ValveService valveService;
	
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody SaveValveRequest request){

		valveService.save(request);
		
		return ResponseEntity.status(HttpStatus.OK).body("Valve created!");
	}
	
	@PatchMapping
	public ResponseEntity<String> update(@Valid @RequestBody UpdateValveRequest request) throws Exception{
		
		valveService.startUpdating(request);
		
		return ResponseEntity.status(HttpStatus.OK).body("Starting action of valve...");
	}
	
}
