package com.example.demo;

import org.junit.jupiter.api.BeforeEach; // Nova importação
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // Nova importação
import org.springframework.web.context.WebApplicationContext; // Nova importação

// Importações estáticas para o MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TesteIntegracao {

    // 1. Removeu o @Autowired daqui
    private MockMvc mockMvc; 

    // 2. Injeta o contexto da aplicação web inteira
    @Autowired
    private WebApplicationContext webApplicationContext; 

    @MockitoBean
    private Repository_feed repositoryFeed;

    // 3. Este método roda ANTES de cada teste e monta o MockMvc na marra
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void deveAbrirPaginaDeContatoComSucesso() throws Exception {
        mockMvc.perform(get("/contato"))
                .andExpect(status().isOk())
                .andExpect(view().name("contatos"));
    }

    @Test
    public void deveSalvarDadosERedirecionarAoEnviarContato() throws Exception {
        Feed feedSimulado = new Feed();
        feedSimulado.setId(1L);
        Mockito.when(repositoryFeed.save(Mockito.any(Feed.class))).thenReturn(feedSimulado);

        mockMvc.perform(post("/contato/enviar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "Rodrigo Silva")
                .param("email", "rodrigo@email.com")
                .param("mensagem", "Excelente site!")
                .param("avaliacao", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contato"));

        Mockito.verify(repositoryFeed, Mockito.times(1)).save(Mockito.any(Feed.class));
    }
}