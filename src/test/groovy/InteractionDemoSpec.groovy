import spock.lang.Specification

/**
 * @author zhangjl
 */
class InteractionDemoSpec extends Specification {

    def 'runner go test'(){
        given:
        DemoRunner runner = Mock(DemoRunner)
        def demo = new DemoRunnerGo(runner)

        when:
        '''
            第一种写法：直接到参数中
            多个参数或者带类型的1个参数
        '''
        times * runner.run(_ as String, _ as Integer) >> {name, args -> name + args}

        '''
            第二种写法
        '''
//        times * runner.run(_ as String, _ as Integer) >> {args ->
//            println args
//            return args[0] + args[1]
//        }

        then:
        def data = demo.run(times)
        data.size() == times
        data.get(index) == value

        where:
        times | index | value
        3     | 0     | 'runner_1'
        3     | 1     | 'runner_2'
        3     | 2     | 'runner_3'
    }

    def 'runner go test with difference mock'(){
        given:
        DemoRunner runner = Mock {
            1 * run('runner', 1)
            1 * run('go', 2)
        }
        def demo = new DemoRunnerGo(runner)

        expect:
        demo.mask('runner', 1)
        demo.mask('go', 2)
    }
}

class DemoRunnerGo {
    DemoRunner runner

    DemoRunnerGo(DemoRunner runner){
        this.runner = runner
    }

    void mask(String name, int args){
        runner.run(name, args)
    }

    List<String> run(int times){
        return (1..times).collect { i ->
            return runner.run('runner_', i)
        }
    }
}

interface DemoRunner {
    String run(String name, int args)
}
