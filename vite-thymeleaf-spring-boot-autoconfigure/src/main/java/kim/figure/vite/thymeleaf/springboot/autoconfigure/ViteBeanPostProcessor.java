package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import kim.figure.vite.thymleaf.linkbuilder.VitePathParseLinkBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.thymeleaf.spring6.SpringTemplateEngine;

public class ViteBeanPostProcessor implements BeanPostProcessor {
    private VitePathParseLinkBuilder vitePathParseLinkBuilder;

    ViteBeanPostProcessor(VitePathParseLinkBuilder vitePathParseLinkBuilder) {
        this.vitePathParseLinkBuilder = vitePathParseLinkBuilder;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof SpringTemplateEngine){
            SpringTemplateEngine springTemplateEngine = (SpringTemplateEngine)bean;
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

    public VitePathParseLinkBuilder getVitePathParseLinkBuilder(){
        return this.vitePathParseLinkBuilder;
    }


}