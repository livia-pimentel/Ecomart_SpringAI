package br.com.alura.ecomart.ai;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.stereotype.Component;

@Component
public class ContarTokens {

    // Faz contagem dos tokens
    public int contar(String system, String user) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O);

        return enc.countTokens(system + user);
    }
}
