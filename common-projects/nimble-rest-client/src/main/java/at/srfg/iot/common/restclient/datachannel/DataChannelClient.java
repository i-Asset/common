package at.srfg.iot.common.restclient.datachannel;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(name = "data-channel-service", url = "${nimble.data-channel.url:}")
@Profile("!test")
public interface DataChannelClient extends IDataChannelClient {

}