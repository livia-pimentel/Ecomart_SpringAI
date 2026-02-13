package br.com.alura.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gerador")
public class GeradorDeProdutosController {

    // Atributo
    private final ChatClient chatClient;

    // Construtor
    public GeradorDeProdutosController (@Qualifier("gpt-4o-mini") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // Metodos
    @GetMapping
    public String gerarProdutos() {
        var pergunta = "Gere 5 produtos ecológicos";
        return this.chatClient.prompt()
                .user(pergunta)
                .call()
                .content();
    }

}
