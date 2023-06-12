package com.DiscussionCommunity.utils;

import org.springframework.stereotype.Component;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Collections;
import java.util.List;

@Component
public class SwaggerUtil {

    private static final String AUTHORIZATION_HEADER="Authorization";
    public ApiInfo getInfo(){
        return new ApiInfo(
                "DISCUSSION COMMUNITY [API DOCUMENTATION]  ",
                "A repo where user can post photos, status, etc and gets comment on that post , people can reply comment on the post and also upvote and downvote that post and comment.",
                "2.7.12",
                "Terms of Service",
                new Contact("anix001","","barcafan830@gmail.com"),
                "LICENSE of APIS",
                "API LICENSE URL",
                Collections.emptyList()
        );
    }

    public ApiKey apiKey(){
        return  new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    public List<SecurityContext> securityContextList(){
        return Collections.singletonList(SecurityContext.builder().securityReferences(securityReferenceList()).build());
    }

    private List<SecurityReference> securityReferenceList(){
        AuthorizationScope scopes = new AuthorizationScope("global","accessEverything");
        return List.of(new SecurityReference("JWT", new AuthorizationScope[]{scopes}));
    }
}
