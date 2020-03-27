package at.srfg.iot.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.iot.eclass.model.ClassificationClass;
import at.srfg.iot.eclass.model.Property;
import at.srfg.iot.eclass.model.Unit;
import at.srfg.iot.eclass.model.Value;

@FeignClient(name = "eclass-service", url= "${iasset.eclass.url")
public interface EClassService  {

	@RequestMapping(
			method = RequestMethod.GET,
			path="/class",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/class")
	public Optional<ClassificationClass> getClass(@RequestParam("irdiCC") String irdi) ;
	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/property",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	@GetMapping("/property")
	public Optional<Property> getProperty(@RequestParam("irdiPR") String irdi);

	@RequestMapping(
			method = RequestMethod.GET,
			path="/properties",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/properties")
	public List<Property> getValues(@RequestParam("irdiCC") String irdi) ;
	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/value",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/value")
	public Optional<Value> getValue(@RequestParam("irdiVA") String irdi);
	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/values",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/values")
	public List<Value> getPropertyValues(
			@RequestParam("irdiCC") String irdicc, 
			@RequestParam("irdiPR") String irdipr);

	@RequestMapping(
			method = RequestMethod.GET,
			path="/unit",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/unit")
	public Optional<Unit> getUnit(@RequestParam("irdiUN") String irdi) ;

}
