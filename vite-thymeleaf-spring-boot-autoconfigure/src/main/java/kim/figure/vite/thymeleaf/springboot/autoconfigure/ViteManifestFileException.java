package kim.figure.vite.thymeleaf.springboot.autoconfigure;

/**
 * author         : walker
 * date           : 2022. 11. 13.
 * description    :
 */
public class ViteManifestFileException extends RuntimeException {
    public ViteManifestFileException(){
        super();
    }

    public ViteManifestFileException(String message) {
        super(message);
    }
}
