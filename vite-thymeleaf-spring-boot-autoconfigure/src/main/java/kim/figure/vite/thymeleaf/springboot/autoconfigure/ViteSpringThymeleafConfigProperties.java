package kim.figure.vite.thymeleaf.springboot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * author         : Do-Hyeong Walker Kim
 * date           : 2022. 11. 11.
 * description    :
 */
@ConfigurationProperties(prefix = "vite")
public class ViteSpringThymeleafConfigProperties {
    private String manifestFilePath;
    private String[] compileAssetExtensionArray;
    private Boolean isViteDevProxyActive;

    /**
     * @return the manifestFilePath
     */
    public String getManifestFilePathInClassPath() {
        return Objects.requireNonNullElse(manifestFilePath, "static/manifest.json");
    }

    /**
     * @param manifestFilePath the manifestFilePath to set
     */
    public void setManifestFilePath(String manifestFilePath) {
        this.manifestFilePath = manifestFilePath;
    }

    /**
     * @return the compileAssetExtensionArray
     */
    public String[] getCompileAssetExtensionArray() {
        if(compileAssetExtensionArray==null){
            return new String[]{"css", "js"};
        }else{
            return compileAssetExtensionArray;
        }
    }

    /**
     * @param compileAssetExtensionArray the compileAssetExtensionArray to set
     */
    public void setCompileAssetExtensionArray(String[] compileAssetExtensionArray) {
        this.compileAssetExtensionArray = compileAssetExtensionArray;
    }


    /**
     * @return the isViteDevProxyActive
     */
    public Boolean getIsViteDevProxyActive() {
        return isViteDevProxyActive;
    }

    /**
     * @param viteDevServerActive the isViteDevProxyActive to set
     */
    public void setIsViteDevProxyActive(Boolean viteDevServerActive) {
        isViteDevProxyActive = viteDevServerActive;
    }

}
