package mil.osd.avds.decryptor.decryptor;

import lombok.NonNull;

import lombok.Data;

@Data
public class PropertyDefaultFileWrapper {

    @NonNull
    private String filePath;

    private EncryptablePropertiesPropertySource encryptablePropertiesPropertySource;
}
