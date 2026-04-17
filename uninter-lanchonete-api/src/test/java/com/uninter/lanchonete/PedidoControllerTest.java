package com.uninter.lanchonete;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornar401SemTokenNoPedido() throws Exception {
        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "cliente@uninter.com", roles = {"CLIENTE"})
    void clientePodeCriarPedido() throws Exception {
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoValidoJson()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "cliente@uninter.com", roles = {"CLIENTE"})
    void deveRetornar409QuandoEstoqueInsuficiente() throws Exception {
        String body = "{\"unidadeId\":1,\"canalPedido\":\"APP\",\"itens\":[{\"produtoId\":1,\"quantidade\":9999}]}";
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "cliente@uninter.com", roles = {"CLIENTE"})
    void deveRetornar404QuandoProdutoNaoExiste() throws Exception {
        String body = "{\"unidadeId\":1,\"canalPedido\":\"APP\",\"itens\":[{\"produtoId\":9999,\"quantidade\":1}]}";
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "cliente@uninter.com", roles = {"CLIENTE"})
    void deveRetornar400QuandoCanalPedidoAusente() throws Exception {
        String body = "{\"unidadeId\":1,\"itens\":[{\"produtoId\":1,\"quantidade\":1}]}";
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@uninter.com", roles = {"ADMIN"})
    void adminPodeListarPedidos() throws Exception {
        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "cliente@uninter.com", roles = {"CLIENTE"})
    void clienteNaoPodeListarPedidos() throws Exception {
        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@uninter.com", roles = {"ADMIN"})
    void deveRetornar404AoCancelarPedidoInexistente() throws Exception {
        mockMvc.perform(patch("/pedidos/9999/cancelar"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin@uninter.com", roles = {"ADMIN"})
    void adminPodeAtualizarStatus() throws Exception {
        Long pedidoId = criarPedidoERetornarId();
        mockMvc.perform(patch("/pedidos/" + pedidoId + "/status?status=EM_PREPARO"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@uninter.com", roles = {"ADMIN"})
    void listarPedidosPorCanalFunciona() throws Exception {
        mockMvc.perform(get("/pedidos?canalPedido=APP"))
                .andExpect(status().isOk());
    }

    private Long criarPedidoERetornarId() throws Exception {
        MvcResult result = mockMvc.perform(post("/pedidos")
                        .with(req -> {
                            req.setRemoteUser("cliente@uninter.com");
                            return req;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoValidoJson()))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.get("id").asLong();
    }

    private String pedidoValidoJson() {
        return "{\"unidadeId\":1,\"canalPedido\":\"APP\",\"itens\":[{\"produtoId\":1,\"quantidade\":1}]}";
    }
}
