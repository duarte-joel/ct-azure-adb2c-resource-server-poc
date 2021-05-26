package com.carlsberg.consumertech.service.template.api;

import lombok.Builder;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

@Value
@Builder
public class BeverageDto {
    @Nullable String brandUUID;
    @Nullable String uuid;
    @Nullable String name;
    @Nullable Boolean isAlcoholic;
    @Nullable BeverageTypeDto beverageType;
}
