package br.com.alura.ecomart.service;

import br.com.alura.ecomart.ai.ContarTokens;
import br.com.alura.ecomart.ai.SeletorModeloChat;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import org.slf4j.Logger;


@Service
public class CategorizadorDeProdutosService {

    private static final Logger log = LoggerFactory.getLogger(CategorizadorDeProdutosService.class);

    private final Map<String, ChatClient> chatClients;
    private final ContarTokens contarToken;
    private final SeletorModeloChat seletor;

    public CategorizadorDeProdutosService (Map<String, ChatClient> chatClients, ContarTokens contarToken, SeletorModeloChat seletor) {
        this.chatClients = chatClients;
        this.contarToken = contarToken;
        this.seletor = seletor;
    }

    public String categorizar(String systemPrompt, String produto) {

        // contar tokens
        int tokens = contarToken.contar(systemPrompt, produto);

        // escolher modelo automaticamente
        String modelo = seletor.escolherModelo(tokens);

        log.info("Tokens calculados: {}", tokens);
        log.info("Modelo escolhido: {}", modelo);

        // pegar cliente correto
        ChatClient client = chatClients.get(modelo);

        return client.prompt()
                .system(systemPrompt)
                .user(produto)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.85)
                        .build())
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

}
