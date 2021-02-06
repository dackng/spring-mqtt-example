package pe.com.dackng.example.valve;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ValveRepository extends PagingAndSortingRepository<Valve, String>{

	@Query("{ 'code' : ?0 }")
	Valve findByCode(String code);
}
