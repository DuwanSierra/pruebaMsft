package com.jeffdev.prueba.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffdev.prueba.impl.IClienteImple;
import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.service.IClienteService;
import com.jeffdev.prueba.utils.UtilsTest;
import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClienteImple iClienteService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void saveClient() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setNombre("Pepito");
        cliente.setApellido("Perez");
        cliente.setEmail("pepito@gmail.com");
        cliente.setDni("11111");
        cliente.setTelefono(213333L);
        String inputInJson = UtilsTest.mapToJson(cliente);
        String URI = "/api/cliente/save-cliente";
        Mockito.when(iClienteService.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        mockMvc = MockMvcBuilders.webAppContextSetup (webApplicationContext).build();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Cliente clienteRes = objectMapper.readValue(response.getContentAsString(),Cliente.class);

        assertThat(clienteRes.getIdcliente()).isNull();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updateClient() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setNombre("Pepito");
        cliente.setApellido("Perez");
        cliente.setEmail("pepito@gmail.com");
        cliente.setDni("11111");
        cliente.setTelefono(213333L);
        cliente.setIdcliente(1L);
        String inputInJson = UtilsTest.mapToJson(cliente);
        String URI = "/api/cliente/save-cliente";
        Mockito.when(iClienteService.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        mockMvc = MockMvcBuilders.webAppContextSetup (webApplicationContext).build();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String clienteRes = response.getContentAsString();

        assertThat(inputInJson).isEqualTo(clienteRes);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }



}
