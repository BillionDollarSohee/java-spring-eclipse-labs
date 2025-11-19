package com.example.demo.controller;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Product;

@Controller
public class MarketingController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/marketing")
    public String getMarketing() {
        return "marketing_request";
    }

    @PostMapping("/marketing")
    public String postMarketing(@ModelAttribute Product product, Model model) {

        // ===== 1. 시스템 메세지 =====
        var systemMessage = new SystemMessage("""
                너는 전문 마케팅 카피라이터야.
                입력된 제품 정보를 기반으로 매력적인 광고 문구를 만들어줘.
                표현은 자연스럽고 구매욕을 높이는 방식으로 작성해줘.
                """);

        // ===== 2. 사용자 메세지 =====
        var userMessage = new UserMessage(MessageFormat.format("""
                아래 제품 정보를 기반으로 마케팅 카피를 생성해줘.

                - 제품명 : {0}
                - 가격 : {1}
                - 구매링크 : {2}
                - 제품의 특징 : {3}
                """,
            product.getName(),
            product.getPrice(),
            product.getLink(),
            product.getFeatures()
        ));

        // ===== 3. 옵션 설정 (2000토큰, temperature 0.7) =====
        ChatOptions options = ChatOptions.builder()
                .maxTokens(2000)
                .temperature(0.7)
                .build();

        // ===== 4. Prompt 생성 =====
        Prompt prompt = new Prompt(
                List.of(systemMessage, userMessage),
                options
        );

        // ===== 5. 모델 호출 =====
        AssistantMessage result = chatModel.call(prompt).getResult().getOutput();

        model.addAttribute("marketingResult", result);

        return "marketing_response";
    }
}
