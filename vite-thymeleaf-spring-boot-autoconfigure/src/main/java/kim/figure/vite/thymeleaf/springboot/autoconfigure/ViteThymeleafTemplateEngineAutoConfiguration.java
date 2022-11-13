package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.linkbuilder.StandardLinkBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * author         : walker
 * date           : 2022. 11. 02.
 * description    :
 */

//@AutoConfiguration(after = ThymeleafAutoConfiguration.class)
@Configuration
@ConditionalOnClass(VitePathParseLinkBuilder.class)
@EnableConfigurationProperties(ViteSpringThymeleafConfigProperties.class)
public class ViteThymeleafTemplateEngineAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ViteThymeleafTemplateEngineAutoConfiguration.class);

    @Bean
    public ViteBeanPostProcessor addViteLinkBuilder(ViteSpringThymeleafConfigProperties properties){

        File manifestFile;
        try{
            manifestFile = ResourceUtils.getFile(properties.getManifestFilePath());
        }catch (FileNotFoundException e){
            manifestFile = null;
            if(properties.getIsViteDevProxyActive()){
                logger.warn("manifest.json (made by Vite build) is not found.");
            }else{
                e.printStackTrace();
                throw new ViteManifestFileException(e.getMessage());
            }
        }


        VitePathParseLinkBuilder vitePathParseLinkBuilder = null;
        try {
            vitePathParseLinkBuilder = new VitePathParseLinkBuilder(new StandardLinkBuilder(), properties.getCompileAssetExtensionArray(), manifestFile,properties.getIsViteDevProxyActive());
        } catch (IOException e) {
            throw new ViteManifestFileException(e.getMessage());
        }

        vitePathParseLinkBuilder.setOrder(1);
        return new ViteBeanPostProcessor(vitePathParseLinkBuilder);
    }
}
