package io.github.rojae.authcoreapi.common.exception;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

public class GlobalErrorNotification {

    @Value("${server.name}")
    private String serverName;
    @Value("${logging.slack.botName}")
    private String botName;
    @Value("${logging.slack.webhook}")
    private String slackWebhook;

    void notification(Exception e){
        SlackApi slackApi = new SlackApi(slackWebhook);
        var slackAttachment = new SlackAttachment();
        slackAttachment.setFallback("Error");
        slackAttachment.setTitle("Error Detect");
        slackAttachment.setText(e.getMessage());
        slackAttachment.setColor("danger");

        var slackMessage = new SlackMessage();
        slackMessage.setAttachments(Collections.singletonList(slackAttachment));
        slackMessage.setIcon(":ghost:");
        slackMessage.setText(serverName);
        slackMessage.setUsername(botName);

        slackApi.call(slackMessage);
    }
}
