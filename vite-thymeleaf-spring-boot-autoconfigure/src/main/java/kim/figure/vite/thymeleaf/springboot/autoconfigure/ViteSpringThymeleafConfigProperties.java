package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author         : walker
 * date           : 2022. 11. 11.
 * description    :
 */
@ConfigurationProperties(prefix = "vite")
public class ViteSpringThymeleafConfigProperties {
    private String manifestFilePath;
    private String[] compileAssetExtensionArray;
    private String viteDevServerAddress;
    private Boolean isViteDevProxyActive;

    public String getManifestFilePath() {
        if(manifestFilePath==null){
            return "classpath:static/manifest.json";
        }else{
            return manifestFilePath;
        }
    }

    public void setManifestFilePath(String manifestFilePath) {
        this.manifestFilePath = manifestFilePath;
    }

    public String[] getCompileAssetExtensionArray() {
        if(compileAssetExtensionArray==null){
            return new String[]{"css", "js"};
        }else{
            return compileAssetExtensionArray;
        }
    }

    public void setCompileAssetExtensionArray(String[] compileAssetExtensionArray) {
        this.compileAssetExtensionArray = compileAssetExtensionArray;
    }

    public String getViteDevServerAddress() {
        if(viteDevServerAddress==null){
            return "http://locallhost:5173";
        }else{
            return viteDevServerAddress;
        }
    }

    public void setViteDevServerAddress(String viteDevServerAddress) {
        this.viteDevServerAddress = viteDevServerAddress;
    }

    public Boolean getIsViteDevProxyActive() {
        return isViteDevProxyActive;
    }

    public void setIsViteDevProxyActive(Boolean viteDevServerActive) {
        isViteDevProxyActive = viteDevServerActive;
    }

}
