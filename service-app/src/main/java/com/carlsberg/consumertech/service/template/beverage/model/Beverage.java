package com.carlsberg.consumertech.service.template.beverage.model;

import lombok.Builder;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Beverage {
    int id;
    int brandId;
    @Id
    @Nullable String uuid;
    @Nullable String name;
    boolean isAlcoholic;
    @Nullable BeverageType beverageType;
}
