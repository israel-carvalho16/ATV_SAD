package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(MockitoExtension.class) 
public class ControlerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private Repository_feed repositoryFeed; 

    @InjectMocks
    private Controler controler; 

    @BeforeEach
    public void setup() {
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(controler).build();
    }

    @Test
    public void deveAbrirPaginaDeContatoComSucesso() throws Exception {
        
        mockMvc.perform(get("/contato"))
                .andExpect(status().isOk())
                .andExpect(view().name("contatos"));
    }

    @Test
    public void deveSalvarDadosERedirecionarAoEnviarContato() throws Exception {
        
        Feed feedMockado = new Feed();
        Mockito.when(repositoryFeed.save(Mockito.any(Feed.class))).thenReturn(feedMockado);

        
        mockMvc.perform(post("/contato/enviar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "Lucas Souza")
                .param("email", "lucas@email.com")
                .param("mensagem", "Mensagem enviada via Teste Unitário!")
                .param("avaliacao", "4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contato"));

        
        Mockito.verify(repositoryFeed, Mockito.times(1)).save(Mockito.any(Feed.class));
    }
}