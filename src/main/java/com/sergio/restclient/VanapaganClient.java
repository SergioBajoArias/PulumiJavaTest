package com.sergio.restclient;

import com.sergio.dto.VanapaganResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "vanapagan", url = "${feign.client.config.vanapagan.url}")
public interface VanapaganClient {

    @RequestMapping(method = RequestMethod.GET, value = "/about")
    VanapaganResponse getAbout();
}
