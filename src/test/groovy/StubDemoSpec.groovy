import spock.lang.Specification

/**
 * @author zhangjl
 */
class StubDemoSpec extends Specification {

    IStubDemo iface
    def demo
    def setup(){
        iface = Stub(IStubDemo)
        demo = new StubDemo(iface)
    }

    def 'test stub sequences of values'(){
        given:
        iface.receive(_) >>> ['ok', 'error']

        when:
        def data = (1..2).collect { demo.receive('test') }

        then:
        data.size() == 2
        data.get(0) == 'ok'
        data.get(1) == 'error'
    }

    def 'test stub computing return values'(){
        given:
        '''
            第一种写法
        '''
        iface.receive(_ as String) >> { args -> args[0].toUpperCase() }

        '''
            第二种写法
        '''
        // iface.receive(_ as String) >> { String msg -> msg.toUpperCase() }

        when:
        def data = (1..2).collect {i -> demo.receive('test_' + i) }

        then:
        data.size() == 2
        data.get(0) == 'TEST_1'
        data.get(1) == 'TEST_2'
    }
}

class StubDemo {
    IStubDemo iface

    StubDemo(IStubDemo iface){
        this.iface = iface
    }

    String receive(String msg){
        return iface.receive(msg)
    }
}

interface IStubDemo {
    String receive(String msg)
}
