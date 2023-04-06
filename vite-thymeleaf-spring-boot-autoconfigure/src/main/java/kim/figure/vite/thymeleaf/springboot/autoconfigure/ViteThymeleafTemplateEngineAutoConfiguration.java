package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.thymeleaf.linkbuilder.StandardLinkBuilder;

import java.io.*;

/**
 * author         : Do-Hyeong Walker Kim
 * date           : 2022. 11. 02.
 * description    :
 */

//@AutoConfiguration(after = ThymeleafAutoConfiguration.class)
@Configuration
@ConditionalOnClass(VitePathParseLinkBuilder.class)
@EnableConfigurationProperties(ViteSpringThymeleafConfigProperties.class)
public class ViteThymeleafTemplateEngineAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ViteThymeleafTemplateEngineAutoConfiguration.class);

    /**
     * @param properties ViteSpringThymeleafConfigProperties
     * @return ViteBeanPostProcessor
     */
    @Bean
    public ViteBeanPostProcessor addViteLinkBuilder(ViteSpringThymeleafConfigProperties properties){

        File manifestFile;
        try{
            Resource resource = new ClassPathResource(properties.getManifestFilePathInClassPath());
            InputStream is = resource.getInputStream();
            manifestFile = File.createTempFile("manifest", ".json");
            FileOutputStream fos = new FileOutputStream(manifestFile);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
        }catch (FileNotFoundException e){
            manifestFile = null;
            if(properties.getIsViteDevProxyActive()){
                logger.warn("manifest.json (made by Vite build) is not found.");
            }else{
                e.printStackTrace();
                throw new ViteManifestFileException(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ViteManifestFileException(e.getMessage());
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
