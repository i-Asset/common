package at.srfg.iot.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.srfg.iot.eclass.model.ClassificationClass;
import at.srfg.iot.eclass.model.PropertyDefinition;
import at.srfg.iot.eclass.model.PropertyUnit;
import at.srfg.iot.eclass.model.PropertyValue;

@FeignClient(name = "eclass-service")
public interface SemanticLookupService  {

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
	public Optional<PropertyDefinition> getProperty(@RequestParam("irdiPR") String irdi);

	@RequestMapping(
			method = RequestMethod.GET,
			path="/properties",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/properties")
	public List<PropertyDefinition> getValues(@RequestParam("irdiCC") String irdi) ;
	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/value",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/value")
	public Optional<PropertyValue> getValue(@RequestParam("irdiVA") String irdi);
	
	@RequestMapping(
			method = RequestMethod.GET,
			path="/values",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/values")
	public List<PropertyValue> getPropertyValues(
			@RequestParam("irdiCC") String irdicc, 
			@RequestParam("irdiPR") String irdipr);

	@RequestMapping(
			method = RequestMethod.GET,
			path="/unit",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping("/unit")
	public Optional<PropertyUnit> getUnit(@RequestParam("irdiUN") String irdi) ;

}
