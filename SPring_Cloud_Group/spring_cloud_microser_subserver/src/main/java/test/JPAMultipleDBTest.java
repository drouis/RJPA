package test;

import com.rjpa.config.DriverSchoolDataSourceConfig;
import com.rjpa.config.QuartzDataSourceConfig;
import com.rjpa.mic.repository.driverschool.AmpqMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DriverSchoolDataSourceConfig.class, QuartzDataSourceConfig.class})

public class JPAMultipleDBTest {
    @Autowired
    private AmpqMessageRepository ampqMessageRepository;

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {
        assertNotNull(ampqMessageRepository.findById(18));
    }
}
