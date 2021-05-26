package com.carlsberg.consumertech.service.template.brand.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.annotation.Id;

@Value
@Builder
@AllArgsConstructor
public class Brand {
    int id;
    @Id
    @Nullable String uuid;
    @Nullable String name;
}

