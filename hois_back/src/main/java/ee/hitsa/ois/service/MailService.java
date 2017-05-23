package ee.hitsa.ois.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Value("${hois.mail.receivers:#{null}}")
    private String testReceivers;

    @Value("${hois.mail.disable:#{null}}")
    private Boolean disable;

    @Autowired
    private JavaMailSender mailSender;

    private ThreadLocal<ExecutorService> executorLocal = ThreadLocal.withInitial(
            () -> Executors.newSingleThreadExecutor());

    //IKE - emails are always sent together with system messages but we do not have to guarantee their arrival
    public void sendMail(String from, String to, String subject, String message) {
        if(Boolean.TRUE.equals(disable)) {
            return;
        }

        if (!StringUtils.hasText(from) || !StringUtils.hasText(to)) {
            // TODO avoid use of String.format
            log.error(String.format("mail has no from (%s) or receivers (%s)", from, to));
            return;
        }

        String receivers = testReceivers != null ? testReceivers: to;
        if(testReceivers != null) {
            log.debug("email is using debug receivers");
        }

        try {
            this.mailSender.send(mail -> {
                mail.setFrom(from);
                mail.setRecipients(Message.RecipientType.TO, receivers);
                mail.setSubject(subject);
                mail.setText(message);
                executorLocal.get().submit(() -> {
                    mailSender.send(mail);
                    // TODO avoid use of String.format
                    log.info(String.format("email %s sent to %s", subject, receivers));
                });
            });
        } catch (Exception e) {
            // TODO avoid use of String.format
            log.error(String.format("sending email %s to %s failed", subject, receivers), e);
        }
    }

    public void sendMail(String from, List<String> receivers, String subject, String message) {
        sendMail(from, String.join(",", receivers), subject, message);
    }
}
