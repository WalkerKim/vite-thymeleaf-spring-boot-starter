package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import static org.assertj.core.api.Assertions.*;

import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.lang.reflect.Field;

/**
 * author         : walker
 * date           : 2022. 11. 12.
 * description    :
 */
class ViteThymeleafTemplateEngineAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ViteThymeleafTemplateEngineAutoConfiguration.class));

    @Test
    public void someTest() {
        this.contextRunner
                .withPropertyValues("vite.manifest-file-path=classpath:static/manifest.json2")
                .run(context->{
                    assertThat(context).hasSingleBean(SpringTemplateEngine.class);
                    ViteBeanPostProcessor viteBeanPostProcessor = (ViteBeanPostProcessor)context.getBean("addViteLinkBuilder");
                    VitePathParseLinkBuilder vitePathParseLinkBuilder = viteBeanPostProcessor.getVitePathParseLinkBuilder();
                    File assignedManifestFile = getField(vitePathParseLinkBuilder, "manifestFile");
                    System.out.println(assignedManifestFile.getPath());

                });


    }


    private <T> T getField( Object instance, String fieldName ) throws Exception {
        Field fld = instance.getClass().getDeclaredField( fieldName );
        fld.setAccessible( true );
        return (T) fld.get( instance );
    }
//    @Test
//    public void noSettingsAdded() {
//        this.contextRunner.run((context ->
//                assertThat(context.getBean(GreetingConfig.class).getProperty(USER_NAME))
//                        .isEqualTo(System.getProperty("user.name"))));
//    }

}