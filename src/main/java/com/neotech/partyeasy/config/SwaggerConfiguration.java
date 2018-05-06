package com.neotech.partyeasy.config;

        import com.google.common.base.Predicate;
        import com.google.common.base.Predicates;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import springfox.documentation.builders.ApiInfoBuilder;
        import springfox.documentation.builders.PathSelectors;
        import springfox.documentation.builders.RequestHandlerSelectors;
        import springfox.documentation.service.ApiInfo;
        import springfox.documentation.service.Contact;
        import springfox.documentation.service.Tag;
        import springfox.documentation.spi.DocumentationType;
        import springfox.documentation.spring.web.plugins.Docket;
        import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger configurations.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build().apiInfo(apiInfo())
                .tags(new Tag("Party Easy", "All apis relating toParty Easy")) ;
    }

   /* Make sure that all the other API's are not exposed,specifically spring boot specific*/

    private Predicate<String> paths() {
        return Predicates.or(
                PathSelectors.regex("/partyeasy.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Party Easy")
                .description("API for Party Easy")
                .version("1.0-ALPHA")
                .contact(new Contact("Kiran Nellipudi","","kiran445446@gmail.com"))
                .build();
    }


}
