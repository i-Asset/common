package at.srfg.iot.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "indexing-service", url= "${iasset.indexing.url")
public interface IndexingService extends at.srfg.indexing.IndexingService {

}
