package mil.osd.avds.decryptor.decryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@EnableScheduling
@Component
public class TestScheduler {

    @Autowired
    MBeanExporter mBeanExporter;

    @Value("${prototype.password}")
    public String passwd;
    @Bean
    public String getPasswd() {
        return passwd;
    }

    @Value("${prototype.test1}")
    public String test1;

	public String getTest1() {
		return test1;
	}

	// This is where we prove the property read works, one ENC and one plaintext
    @Scheduled(initialDelay = 1000, fixedDelayString = "1000")
    public void testProperty() {

        System.out.println("Password: " + getPasswd());
		System.out.println("test1: " + getTest1());
    }

    @PostConstruct
    public void validateMBeans() {

        mBeanExporter.addExcludedBean("12345");
    }

}
