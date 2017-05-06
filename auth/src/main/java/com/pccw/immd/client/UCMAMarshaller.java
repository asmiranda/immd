package com.pccw.immd.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by aiam on 5/6/2017.
 */
@Configuration
public class UCMAMarshaller {
    @Value("${immd.ucma-service-endpoint}")
    private String userEndpoint;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.pccw.immd.generated.user.wsdl");
        return marshaller;
    }

    @Bean
    public UCMAClient ucmaClient(Jaxb2Marshaller marshaller) {
        UCMAClient client = new UCMAClient();
        client.setDefaultUri(userEndpoint);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
