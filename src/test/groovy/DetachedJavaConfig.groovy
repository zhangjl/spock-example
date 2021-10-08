import com.drivingrange.service.GreeterService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

/**
 * @author zhangjl
 */
@Configuration
class DetachedJavaConfig {
    def mockFactory = new DetachedMockFactory()

    @Bean
    GreeterService serviceMock(){
        return mockFactory.Stub(GreeterService)
    }
}
