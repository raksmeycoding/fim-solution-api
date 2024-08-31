package com.fimsolution.group.app.utils;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FimSendingMailOtp {


    @Value("${mailjet.apikey}")
    private String apikey;

    @Value("${mailjet.secret}")
    private String secrete;


    @Builder
    @Data
    public static class MailjetBody {
        private String fromEmail;
        private String fromName;
        private String toEmail;
        private String toName;
        private String subject;
        private String textPart;
        private String htmlPart;
        private String otpCode;
    }

    public void sendingOtp(MailjetBody mailjetBody) throws MailjetException {

        // Initialize MailjetClient with API keys and default ClientOptions
        MailjetClient client = new MailjetClient(
                ClientOptions
                        .builder()
                        .apiKey(apikey)
                        .apiSecretKey(secrete)
                        .build()
        );

        // Create the Mailjet request
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", mailjetBody.fromEmail != null ? mailjetBody.fromEmail : "raksmeykoung@gmail.com")
                                        .put("Name", mailjetBody.fromEmail != null ? mailjetBody.fromName : "Raksmey Koung"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", mailjetBody.fromEmail != null ? mailjetBody.toEmail : "raksmeykoung.learnenglish@gmail.com")
                                                .put("Name", mailjetBody.fromEmail != null ? mailjetBody.toName : "Raksmey Koung")))
                                .put(Emailv31.Message.SUBJECT, mailjetBody.fromEmail != null ? mailjetBody.subject : "Your OTP Code for FiMSolution")
                                .put(Emailv31.Message.TEXTPART, mailjetBody.fromEmail != null ? mailjetBody.textPart : "Dear Raksmey, your OTP code is " + mailjetBody.getOtpCode() + " . Please use this code to verify your account.")
                                .put(Emailv31.Message.HTMLPART, mailjetBody.fromEmail != null ? mailjetBody.htmlPart
                                        : "<h3>Dear Raksmey,</h3><p>Welcome to <a href=\"https://www.fimsolution.com/\">FiMSolution</a>!<br>Your OTP code is <strong>"+ mailjetBody.getOtpCode() +"</strong>. " +
                                        "Please use this code to verify your account.</p><p>Thank you for choosing FiMSolution!</p>")
                        ));

        // Send the email and get the response
        MailjetResponse response = client.post(request);

        // Output the response status and data
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }


    public MailjetBody createDefaultBodyBuilder() {
        return MailjetBody.builder()
                .otpCode(GenerationUtil.generateOtp())
                .build();
    }


}
