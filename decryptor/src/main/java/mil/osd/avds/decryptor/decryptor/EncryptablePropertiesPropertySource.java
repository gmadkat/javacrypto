package mil.osd.avds.decryptor.decryptor;

import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public final class EncryptablePropertiesPropertySource extends PropertiesPropertySource {
    public EncryptablePropertiesPropertySource(String name, EncryptableProperties props) {
        super(name, props);
    }

    public EncryptablePropertiesPropertySource(String name, Properties props, TextEncryptor encryptor) {
        super(name, processProperties(props, encryptor));
    }

    public EncryptablePropertiesPropertySource(String name, Properties props, StringEncryptor encryptor) {
        super(name, processProperties(props, encryptor));
    }


    private static Properties processProperties(Properties props, TextEncryptor encryptor) {
        if (props == null) {
            return null;
        } else if (props instanceof EncryptableProperties) {
            throw new IllegalArgumentException("Properties object already is an " + EncryptableProperties.class.getName() + " object. No encryptor should be specified.");
        } else {
            EncryptableProperties encryptableProperties = new EncryptableProperties(encryptor);
            encryptableProperties.putAll(props);
            return encryptableProperties;
        }
    }

    private static Properties processProperties(Properties props, StringEncryptor encryptor) {
        if (props == null) {
            return null;
        } else if (props instanceof EncryptableProperties) {
            throw new IllegalArgumentException("Properties object already is an " + EncryptableProperties.class.getName() + " object. No encryptor should be specified.");
        } else {
            EncryptableProperties encryptableProperties = new EncryptableProperties(encryptor);
            encryptableProperties.putAll(props);
            return encryptableProperties;
        }
    }
}
