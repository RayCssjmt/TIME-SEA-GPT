package com.cn.bdth.common;

import com.cn.bdth.constants.ServerConstant;
import com.cn.bdth.utils.RedisUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 雨纷纷旧故里草木深
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ChatGptCommon {

    private final RedisUtils redisUtils;

    @Value("${config.openAiUrl}")
    private String openAiUrl;

    @Value("${config.openKey}")
    private String openKey;

    @Value("${config.openPlusKey}")
    private String openPlusKey;

    @Value("${config.gptTextImageFrequency}")
    private Long gptTextImageFrequency;

    @Value("${config.gptFrequency}")
    private Long gptFrequency;

    @Value("${config.gptPlusFrequency}")
    private Long gptPlusFrequency;

    public ChatGptStructure getChatGptStructure() {
        final Object value = redisUtils.getValue(ServerConstant.CHAT_GPT_CONFIG);
        if (value == null) {
            return getDefault();
        }
        try {
            return (ChatGptStructure) value;
        } catch (Exception e) {
            log.warn("已清除上一个版本的GPT配置,请前往控制台重新配置ChatGPT参数配置");
            redisUtils.delKey(ServerConstant.CHAT_GPT_CONFIG);
            return getDefault();
        }
    }

    private ChatGptStructure getDefault() {
        log.warn("请前往控制台配置ChatGPT参数配置");
        return new ChatGptStructure()
                .setGptFrequency(gptFrequency)
                .setGptPlusFrequency(gptPlusFrequency)
                .setOpenPlusKey(openPlusKey)
                .setOpenKey(openKey)
                .setGptTextImageFrequency(gptTextImageFrequency)
                .setOpenAiUrl(openAiUrl);
    }

    @Data
    @Accessors(chain = true)
    public static class ChatGptStructure {

        private String openAiUrl;

        private String openKey;

        private String openPlusKey;

        private Long gptFrequency;

        private Long gptPlusFrequency;

        private Long gptTextImageFrequency;
    }

}
