package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import javax.mail.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    private JavaMailSender mailSender;

    private final ExecutorService executorService = ThreadUtil.newScalingThreadPool(0, 10, 300L);

    // IKE - emails are always sent together with system messages but we do not have to guarantee their arrival
    public void sendMail(String from, String to, String subject, String message) {
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

        try {
            executorService.execute(() -> {
                try {
                    mailSender.send(mail -> {
                        mail.setFrom(from);
                        mail.setRecipients(Message.RecipientType.TO, receivers);
                        mail.setSubject(subject);
                        mail.setText(message);
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

    public void sendMail(ee.hitsa.ois.domain.Message message, Collection<String> receivers) {
        sendMail(message.getSender().getEmail(), String.join(",", StreamUtil.toFilteredList(r -> r != null, receivers)), message.getSubject(), message.getContent());
    }

    public void sendMail(ee.hitsa.ois.domain.Message message, String sender, Collection<String> receivers) {
        sendMail(sender, String.join(",", StreamUtil.toFilteredList(r -> r != null, receivers)), message.getSubject(), message.getContent());
    }

}
