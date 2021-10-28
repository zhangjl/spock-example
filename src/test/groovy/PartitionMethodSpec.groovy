import com.drivingrange.MyDemo
import org.junit.Rule
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

/**
 * Mock部分方法(包括私有方法)
 * @author zhangjl
 */
@PrepareForTest(MyDemo)
class PartitionMethodSpec extends Specification {

    /**
     * Mock私有方法要加入
     */
    @Rule PowerMockRule powerMockRule = new PowerMockRule();

    /**
     * 推荐方式
     * @return
     */
    def '类的部分方法Mock测试'(){
        given:
        def demo = Mockito.spy(new MyDemo(done: 'done'))

        and:
        Mockito.when(demo.go(Mockito.anyInt(), Mockito.anyInt())).thenReturn(sum)

        when:
        int response = demo.doSomething(c)
        String done = ReflectionTestUtils.getField(demo, 'done')

        then:
        response == expected
        done == 'done'

        where:
        sum | c || expected
        5   | 1 || 5
        4   | 2 || 8
    }

    def '类的部分方法Mock测试,第二种方式'(){
        given:
        def demo = Mockito.mock(MyDemo)

        and:
        Mockito.when(demo.go(Mockito.anyInt(), Mockito.anyInt())).thenReturn(sum)
        Mockito.when(demo.doSomething(Mockito.anyInt())).thenCallRealMethod()

        ReflectionTestUtils.setField(demo, 'done', 'done')

        when:
        int response = demo.doSomething(c)
        String done = ReflectionTestUtils.getField(demo, 'done')

        then:
        response == expected
        done == 'done'

        where:
        sum | c || expected
        5   | 1 || 5
        4   | 2 || 8
    }

    def '类的部分方法Mock测试和私有方法'(){
        given:
        def demo = PowerMockito.spy(new MyDemo(done: 'done'))

        and:
        PowerMockito.when(demo.go(Mockito.anyInt(), Mockito.anyInt())).thenReturn(sum)
        PowerMockito.when(demo, 'inner', Mockito.anyInt(), Mockito.anyInt()).thenReturn(inner)

        when:
        int response = demo.doThat(c)
        String done = ReflectionTestUtils.getField(demo, 'done')

        then:
        response == expected
        done == 'done'

        where:
        sum | inner | c || expected
        5   | 4     | 1 || 20
        4   | 3     | 2 || 24
    }
}
