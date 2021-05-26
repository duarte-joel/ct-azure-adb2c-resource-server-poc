package com.carlsberg.consumertech.service.template.beverage.config;

import lombok.Builder;
import lombok.Value;

import java.net.URI;

@Value
@Builder
public class BeverageServiceProperties {
    private URI url;
}
