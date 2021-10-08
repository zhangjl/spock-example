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
        3 * runner.run(_, _) >> {name, args -> name + args}

        then:
        def data = demo.run(3)
        data.size() == 3
        data.get(0) == 'runner_1'
    }
}

class DemoRunnerGo {
    DemoRunner runner

    DemoRunnerGo(DemoRunner runner){
        this.runner = runner
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
