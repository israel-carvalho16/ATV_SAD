package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controler {

    @Autowired
    private Repository_feed repositoryFeed;

    @GetMapping("/contato")
    public String abrirPagina() {
        return "contatos"; 
    }

    @PostMapping("/contato/enviar")
    public String salvarDados(@RequestParam("nome") String nome,
                              @RequestParam("email") String email,
                              @RequestParam("mensagem") String mensagem,
                              @RequestParam("avaliacao") Integer avaliacao) {
        
        Feed novoFeed = new Feed();
        novoFeed.setNome(nome);
        novoFeed.setEmail(email);
        novoFeed.setMensagem(mensagem);
        novoFeed.setAvaliacao(avaliacao);

        repositoryFeed.save(novoFeed);

        return "redirect:/contato"; 
    }
}