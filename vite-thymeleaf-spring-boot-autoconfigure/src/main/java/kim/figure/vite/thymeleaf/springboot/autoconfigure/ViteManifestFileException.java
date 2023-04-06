package kim.figure.vite.thymeleaf.springboot.autoconfigure;

/**
 * author         : Do-Hyeong Walker Kim
 * date           : 2022. 11. 13.
 * description    :
 */
public class ViteManifestFileException extends RuntimeException {
    /**
     *
     */
    public ViteManifestFileException(){
        super();
    }

    /**
     * @param message the message of original Exception.
     */
    public ViteManifestFileException(String message) {
        super(message);
    }
}
