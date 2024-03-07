package com.example.skylink;

import com.example.skylink.IntegrationTest.AirportPathIntegrated;
import com.example.skylink.IntegrationTest.PassengerDataManagerIntegrated;
import com.example.skylink.IntegrationTest.PaymentHandlerIntegrated;
import com.example.skylink.IntegrationTest.PlaneConfigurationIntegrated;
import com.example.skylink.IntegrationTest.UserHandlerIntegrated;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserHandlerIntegrated.class,
        AirportPathIntegrated.class,
        PassengerDataManagerIntegrated.class,
        PlaneConfigurationIntegrated.class,
        PaymentHandlerIntegrated.class
})
public class  IntegratedTest{
    // This class can remain empty
    // It's used only as a container for the suite
}
