package com.carlsberg.consumertech.service.template.blockhound;


import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

import java.security.SecureRandom;

public class ServiceBlockHoundIntegration implements BlockHoundIntegration {

    @Override
    public void applyTo(BlockHound.Builder builder) {
        //UUID.randomUUID() and SSL are using SecureRandom which can be blocking.
        //In Java 11, MacOs and Linux, new SecureRandom() uses /dev/urandom for nextBytes and /dev/random for seeding.
        //To use /dev/urandom for seeding use -Djava.security.egd=file:/dev/urandom
        //https://docs.oracle.com/en/java/javase/11/security/oracle-providers.html#GUID-9DC4ADD5-6D01-4B2E-9E85-B88E3BEE7453
        builder.allowBlockingCallsInside(SecureRandom.class.getName(), "nextBytes");
    }
}
