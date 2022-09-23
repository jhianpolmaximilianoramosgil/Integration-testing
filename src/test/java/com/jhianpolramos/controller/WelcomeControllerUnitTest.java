package com.jhianpolramos.controller;

import com.jhianpolramos.service.WelcomeService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("PRUEBA UNITARIA CONTROLADOR")
class WelcomeControllerUnitTest {

    WelcomeController welcomeController;

    @BeforeEach
    void setup() {
        WelcomeService welcomeService = Mockito.mock(WelcomeService.class);
        when(welcomeService.getWelcomeMessage("Stranger")).thenReturn("Welcome Stranger!");
        when(welcomeService.getWelcomeMessage("John")).thenReturn("Welcome John!");
        this.welcomeController = new WelcomeController(welcomeService);
    }

    @Test
    void shouldGetDefaultWelcomeMessage() {
        assertEquals("Welcome Stranger!", welcomeController.welcome("Stranger"));
    }

    @Test
    void shouldGetCustomWelcomeMessage() {
        assertEquals("Welcome John!", welcomeController.welcome("John"));
    }
}
