package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
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

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
            log.error("mail has no from ({}) or receivers ({})", from, to);
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
                    log.info("email {} sent to {}", subject, receivers);
                });
            });
        } catch (Exception e) {
            if(log.isErrorEnabled()) {
                log.error(String.format("sending email %s to %s failed", subject, receivers), e);
            }
        }
    }

    public void sendMail(String from, List<String> receivers, String subject, String message) {
        sendMail(from, String.join(",", receivers), subject, message);
    }
}
