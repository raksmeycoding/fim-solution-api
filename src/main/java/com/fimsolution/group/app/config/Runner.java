package com.fimsolution.group.app.config;

import com.fimsolution.group.app.utils.FimSendingMailOtp;
import com.fimsolution.group.app.utils.ProfileHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final ProfileHelper profileHelper;
    private final FimSendingMailOtp fimSendingMailOtp;
    private final static Logger logger = LoggerFactory.getLogger(Runner.class);


    @Override
    public void run(String... args) throws Exception {

        fimSendingMailOtp.sendingOtp(fimSendingMailOtp.createDefaultBodyBuilder());

    }


}
