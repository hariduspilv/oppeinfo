package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.thread.ThreadUtil;
import ee.hitsa.ois.util.StreamUtil;

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${hois.mail.receivers:#{null}}")
    private String testReceivers;
    @Value("${hois.mail.disable:#{null}}")
    private Boolean disable;
    @Value("${hois.mail.sender:#{null}}")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;

    private final ExecutorService executorService = ThreadUtil.newScalingThreadPool(0, 10, 300L);

    // IKE - emails are always sent together with system messages but we do not have to guarantee their arrival
    public void sendMail(String from, String to, String subject, String message, boolean isHtml) {
        if(Boolean.TRUE.equals(disable)) {
            return;
        }

        if (!StringUtils.hasText(from) || !StringUtils.hasText(to)) {
            log.error("mail has no from ({}) or receivers ({})", from, to);
            return;
        }

        String receivers = testReceivers != null ? testReceivers: to;
        if(testReceivers != null) {
            log.warn("email is using debug receivers");
        }

        if (sender == null) {
            log.error("hois.mail.sender does not exists, skip email sending");
            return;
        }

        log.info("sending email from {}, sender email replaced by {}", from, sender);
        try {
            executorService.execute(() -> {
                try {
                    mailSender.send(mail -> {
                        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                        helper.setFrom(sender);
                        helper.setTo(receivers != null ? receivers.split(",") : null);
                        helper.setSubject(subject);
                        String msg = message;
                        if (isHtml) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("<html>");
                            sb.append("<body>");
                            sb.append("<div>");
                            sb.append(msg.replaceAll("\n", "<br/>"));
                            sb.append("</div></body></html>");
                            msg = sb.toString();
                        }
                        helper.setText(msg, isHtml);
                    });
                    log.info("email {} sent to {}", subject, receivers);
                } catch(Exception e) {
                    log.error("sending email {} to {} failed", subject, receivers, e);
                }
            });
        } catch (Exception e) {
            log.error("sending email {} to {} failed", subject, receivers, e);
        }
    }

    public void sendMail(ee.hitsa.ois.domain.Message message, Collection<String> receivers, boolean isHtml) {
        sendMail(message.getSender().getEmail(), receivers.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(",")), message.getSubject(), message.getContent(),
                isHtml);
    }

    public void sendMail(ee.hitsa.ois.domain.Message message, String sender,
                         Collection<String> receivers, boolean isHtml) {
        sendMail(sender, receivers.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(",")), message.getSubject(), message.getContent(),
                isHtml);
    }

}
