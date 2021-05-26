package com.carlsberg.consumertech.service.template;

import com.carlsberg.consumertech.service.template.beverage.model.Beverage;
import com.carlsberg.consumertech.service.template.beverage.model.BeverageType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BeverageTests {

    @Test
    void testBuilder() {
        Beverage beverage = Beverage.builder()
                .id(1)
                .brandId(1)
                .beverageType(BeverageType.BEER)
                .isAlcoholic(true)
                .name("test")
                .build();

        assertEquals(1, beverage.getBrandId());
        assertEquals(1, beverage.getId());
        assertEquals("test", beverage.getName());
        assertEquals(BeverageType.BEER, beverage.getBeverageType());
        assertTrue(beverage.isAlcoholic());
        assertNull(beverage.getUuid());
    }

}
