package at.srfg.iot.common.restclient.delegate;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(name = "delegate-service", url = "${nimble.delegate-service.url:}")
@Profile("!test")
public interface DelegateClient extends IDelegateClient {

}