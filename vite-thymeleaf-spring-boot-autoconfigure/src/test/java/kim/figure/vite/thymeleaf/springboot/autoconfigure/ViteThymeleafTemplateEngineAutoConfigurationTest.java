package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * author         : Do-Hyeong Walker Kim
 * date           : 2022. 11. 12.
 * description    :
 */

class ViteThymeleafTemplateEngineAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ViteThymeleafTemplateEngineAutoConfiguration.class));

    @Test
    public void someTest() {
        this.contextRunner
                .withPropertyValues("vite.manifest-file-path=classpath:static/manifest.json")
                .run(context->{
                    assertThat(context).hasSingleBean(SpringTemplateEngine.class);
                    ViteBeanPostProcessor viteBeanPostProcessor = context.getBean(ViteBeanPostProcessor.class);
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
}