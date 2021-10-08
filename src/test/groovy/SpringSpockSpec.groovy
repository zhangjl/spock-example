import com.drivingrange.service.GreeterService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.inject.Named

/**
 * @author zhangjl
 */
@ContextConfiguration(classes = [DetachedJavaConfig])
class SpringSpockSpec extends Specification {
    @Autowired @Named('serviceMock')
    GreeterService serviceMock

    def 'test spring service'(){
        given:
        serviceMock.greet(_) >> 'hello'

        when:
        def word = serviceMock.greet('')

        then:
        'hello' == word
    }
}

class SpringSpockVersionUpgradeSpec extends Specification {
    @SpringBean
    def serviceMock

    def setup(){
        serviceMock = Stub(GreeterService)
    }

    def 'test spring service'(){
        given:
        serviceMock.greet(_) >> 'hello'

        when:
        def word = serviceMock.greet('')

        then:
        'hello' == word
    }
}