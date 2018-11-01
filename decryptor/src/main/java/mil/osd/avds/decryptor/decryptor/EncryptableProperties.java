package mil.osd.avds.decryptor.decryptor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

/*
* Borrowed from jasypt, to prove that it can snag properties read and decrypt as needed if prepended with "ENC("
 */

public final class EncryptableProperties extends Properties {
    private static final long serialVersionUID = 6479795856725500639L;
    private final Integer ident;
    private transient StringEncryptor stringEncryptor;
    private transient TextEncryptor textEncryptor;


    public EncryptableProperties(StringEncryptor stringEncryptor) {
        this((Properties)null, (StringEncryptor)stringEncryptor);
    }

    public EncryptableProperties(TextEncryptor textEncryptor) {
        this((Properties)null, (TextEncryptor)textEncryptor);
    }

    public EncryptableProperties(Properties defaults, StringEncryptor stringEncryptor) {
        super(defaults);
        this.ident = new Integer("111");
        this.stringEncryptor = null;
        this.textEncryptor = null;
        this.stringEncryptor = stringEncryptor;
        this.textEncryptor = null;
    }

    public EncryptableProperties(Properties defaults, TextEncryptor textEncryptor) {
        super(defaults);
        this.ident = new Integer("222");
        this.stringEncryptor = null;
        this.textEncryptor = null;
//        CommonUtils.validateNotNull(textEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = null;
        this.textEncryptor = textEncryptor;
    }

    public String getProperty(String key) {

        return this.decode(super.getProperty(key));
    }

    public String getProperty(String key, String defaultValue) {
        return this.decode(super.getProperty(key, defaultValue));
    }

    public synchronized Object get(Object key) {
        Object value = super.get(key);
        String valueStr = value instanceof String ? (String)value : null;
        return this.decode(valueStr);
    }

    Integer getIdent() {
        return this.ident;
    }

    private synchronized String decode(String encodedValue) {
        // Mutate the ENC's so we prove we touch it. TODO: actually decrypt!
        if (encodedValue != null && encodedValue.contains("ENC(")) {
            return encodedValue.substring(4);
        }
        else {
            return encodedValue;
        }

        // TODO: keep below code for reference to actually implement

//        if (!PropertyValueEncryptionUtils.isEncryptedValue(encodedValue)) {
//            return encodedValue;
//        } else if (this.stringEncryptor != null) {
//            return PropertyValueEncryptionUtils.decrypt(encodedValue, this.stringEncryptor);
//        } else if (this.textEncryptor != null) {
//            return PropertyValueEncryptionUtils.decrypt(encodedValue, this.textEncryptor);
//        } else {
//            throw new EncryptionOperationNotPossibleException("Neither a string encryptor nor a text encryptor exist for this instance of EncryptableProperties. This is usually caused by the instance having been serialized and then de-serialized in a different classloader or virtual machine, which is an unsupported behaviour (as encryptors cannot be serialized themselves)");
//        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        // TODO: keep below code for reference to actually implement

//        in.defaultReadObject();
//        EncryptablePropertiesEncryptorRegistry registry = EncryptablePropertiesEncryptorRegistry.getInstance();
//        StringEncryptor registeredStringEncryptor = registry.getStringEncryptor(this);
//        if (registeredStringEncryptor != null) {
//            this.stringEncryptor = registeredStringEncryptor;
//        } else {
//            TextEncryptor registeredTextEncryptor = registry.getTextEncryptor(this);
//            if (registeredTextEncryptor != null) {
//                this.textEncryptor = registeredTextEncryptor;
//            }
//
//        }
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {

        // TODO: keep below code for reference to actually implement

//        EncryptablePropertiesEncryptorRegistry registry = EncryptablePropertiesEncryptorRegistry.getInstance();
//        if (this.textEncryptor != null) {
//            registry.setTextEncryptor(this, this.textEncryptor);
//        } else if (this.stringEncryptor != null) {
//            registry.setStringEncryptor(this, this.stringEncryptor);
//        }
//
//        outputStream.defaultWriteObject();
    }
}