package br.com.alura.ecomart.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorizador")
public class CategorizadorDeProdutosController {

    // Atributo
    private final ChatClient chatClient;

    // Construtor
    public CategorizadorDeProdutosController(
            @Qualifier("gpt-4o") ChatClient chatClient) {

        this.chatClient = chatClient;
    }


    // Metodos
    @GetMapping
    public String categorizar(String produto) {
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

        var tokens = contarTokens(system, produto);
        System.out.println("QTD de tokens: " + tokens);

        // Fazer lógica de escolha de modelo baseada no número de tokens

        return this.chatClient.prompt()
                .system(system)
                .user(produto)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.85)
                        .model("gpt-4o")
                        .build())
                .call()
                .content();
    }

    private int contarTokens(String system, String user) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O);
        return enc.countTokens(system + user);
    }

}