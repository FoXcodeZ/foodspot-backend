package com.codecool.gastro.service.form.service;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.*;
import com.codecool.gastro.security.jwt.service.JwtUtils;
import com.codecool.gastro.service.form.handler.FormHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest {
    FormService restaurantFormService;
    @Mock
    FormHandler<Restaurant> restaurantHandler;
    @Mock
    FormHandler<Address> addressHandler;
    @Mock
    FormHandler<BusinessHour> businessHourHandler;
    @Mock
    FormHandler<Location> locationHandler;
    @Mock
    FormHandler<Customer> customerHandler;
    private NewRestaurantFormDto newRestaurantFormDto;

    @BeforeEach
    void setUp() {
        restaurantFormService = new FormService(
                restaurantHandler,
                addressHandler,
                businessHourHandler,
                locationHandler,
                customerHandler
        );

        NewRestaurantDto newRestaurantDto = new NewRestaurantDto(
                "",
                "",
                "",
                1,
                ""
        );
        NewLocationDto newLocationDto = new NewLocationDto(
                BigDecimal.ONE,
                BigDecimal.ONE
        );


        NewAddressDto newAddressDto = new NewAddressDto(
                "",
                "",
                "",
                "",
                "",
                ""
        );

        newRestaurantFormDto = new NewRestaurantFormDto(
                newRestaurantDto,
                newLocationDto,
                List.of(),
                newAddressDto,
                UUID.randomUUID()
        );

    }

    @Test
    void testSubmitRestaurantForm_ShouldUseAllHandlerInPrivateMethod_WhenCalled() {
        // when
        restaurantFormService.submitRestaurantForm(newRestaurantFormDto);

        // then
        verify(restaurantHandler, times(1)).handleRestaurantForm(eq(newRestaurantFormDto), any(Restaurant.class));
        verify(addressHandler, times(1)).handleRestaurantForm(eq(newRestaurantFormDto), any(Restaurant.class));
        verify(locationHandler, times(1)).handleRestaurantForm(eq(newRestaurantFormDto), any(Restaurant.class));
        verify(businessHourHandler, times(1)).handleRestaurantForm(eq(newRestaurantFormDto), any(Restaurant.class));
    }
}
