package cn.eyeo.ai.agent.web;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * DeepSeek Chat Test
 *
 * @author amos.wang
 * @date 2026/4/27
 */
@RestController
public class ChatController {

    private static final String CHAT_MODEL = "deepseek-v4-flash";

    @GetMapping("/ai/stream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        DeepSeekApi deepSeekApi = DeepSeekApi.builder().apiKey(System.getenv("DEEPSEEK_API_KEY")).build();
        DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                .model(CHAT_MODEL)
                .temperature(0.4)
                .maxTokens(200)
                .build();
        DeepSeekChatModel chatModel = DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .defaultOptions(options)
                .build();

        return chatModel.stream(new Prompt(message));
    }

}