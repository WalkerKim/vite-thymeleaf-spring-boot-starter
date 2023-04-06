package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import jakarta.annotation.Nullable;
import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * author         : Do-Hyeong Walker Kim
 * date           : 2022. 11. 11.
 * description    :
 */
public class ViteBeanPostProcessor implements BeanPostProcessor {
    private final VitePathParseLinkBuilder vitePathParseLinkBuilder;

    ViteBeanPostProcessor(VitePathParseLinkBuilder vitePathParseLinkBuilder) {
        this.vitePathParseLinkBuilder = vitePathParseLinkBuilder;
    }


    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {/**/
        if(bean instanceof SpringTemplateEngine springTemplateEngine){
            Integer standardLinkBuilderOrder = vitePathParseLinkBuilder.getStandardLinkBuilderOrder();
            if (standardLinkBuilderOrder == null) {
                vitePathParseLinkBuilder.setOrder(1);
            } else {
                vitePathParseLinkBuilder.setOrder(standardLinkBuilderOrder-1);
            }
            springTemplateEngine.addLinkBuilder(vitePathParseLinkBuilder);
        }
        return bean;
    }


    /**
     * @return the vitePathParseLinkBuilder
     */
    public VitePathParseLinkBuilder getVitePathParseLinkBuilder(){
        return this.vitePathParseLinkBuilder;
    }


}