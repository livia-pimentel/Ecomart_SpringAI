package br.com.alura.ecomart.controller;

import br.com.alura.ecomart.service.CategorizadorDeProdutosService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.DefaultChatOptionsBuilder;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorizador")
public class CategorizadorDeProdutosController {

    // Atributo
    private final CategorizadorDeProdutosService service;

    // Construtor
    public CategorizadorDeProdutosController(CategorizadorDeProdutosService service) {

        this.service = service;
    }


    // Metodos
    @GetMapping
    public String categorizar(@RequestParam String produto) {
        var system = """
                Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
                
                Escolha uma categoria dentra a lista abaixo:
                
                1. Higiene pessoal
                
                2. Eletronicos
                
                3. Esportes
                
                4. Outros
                
                ##### exemplo de uso:
                Pergunta: Bola de futebol
                Resposta: Esportes
                """;

        return service.categorizar(system, produto);
    }

}