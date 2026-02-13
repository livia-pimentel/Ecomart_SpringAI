package br.com.alura.ecomart.ai;

import org.springframework.stereotype.Component;

@Component
public class SeletorModeloChat {

    // Limite de tokens
    private static final int TOKEN_LIMIT_MINI = 500;

    // Define modelo conforme contagem de tokens
    public String escolherModelo(int tokens) {

        if (tokens <= TOKEN_LIMIT_MINI) {
            return "gpt-4o-mini";
        }

        return "gpt-4o";
    }
}
