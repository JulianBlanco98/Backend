package com.backend.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GlobalHandlerTest {

    private GlobalHandler globalHandler;

    @BeforeEach
    void setUp() {
        globalHandler = new GlobalHandler();
    }

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entidad no encontrada");
        ProblemDetail response = this.globalHandler.exceptionHandling(exception);

        assertNotNull(response);
        assertEquals(404, response.getStatus(), "El codigo de error de EntityNotFound es 404");
        assertEquals("Entidad no encontrada", response.getProperties().get("message"));
    }

    @Test
    void testHandleEntityExistException() {
        EntityExistsException exception = new EntityExistsException("Entidad ya existe");
        ProblemDetail response = this.globalHandler.exceptionHandling(exception);

        assertNotNull(response);
        assertEquals(409, response.getStatus(), "El codigo de error de EntityExistsException es 409");
        assertEquals("Entidad ya existe", response.getProperties().get("message"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Argumento incorrecto");
        ProblemDetail response = this.globalHandler.exceptionHandling(exception);

        assertNotNull(response);
        assertEquals(400, response.getStatus(), "El codigo de error de IllegalArgumentException es 400");
        assertEquals("Argumento incorrecto", response.getProperties().get("message"));
    }

    @Test
    void testHandleGenerictException() {
        Exception exception = new Exception("Error del servidor");
        ProblemDetail response = this.globalHandler.exceptionHandling(exception);

        assertNotNull(response);
        assertEquals(500, response.getStatus(), "El codigo de error de Exception es 500");
        assertEquals("Error del servidor", response.getDetail());
    }



}