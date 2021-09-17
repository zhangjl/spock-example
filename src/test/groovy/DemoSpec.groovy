import spock.lang.Ignore
import spock.lang.Rollup
import spock.lang.Specification
import spock.lang.Timeout
import spock.lang.Unroll

/**
 * @author zhangjl
 */
class DemoSpec extends Specification {
    @Timeout(1)
    def 'given when and then testcase'(){
        given:
        Stack<String> stack = new Stack<>()
        def test = 'this is given test'

        when:
        stack.push(test)

        then:
        !stack.empty
        stack.size() == 1
        stack.peek() == test
    }

    @Ignore('测试Ignore注解')
    def 'exception testcase'(){
        given:
        Stack<String> stack = new Stack<>()

        when:
        stack.pop();

        then:
        thrown(EmptyStackException)
        stack.empty
    }

    def 'exception println testcase'(){
        given:
        Stack<String> stack = new Stack<>()

        when:
        stack.pop()

        then:
        def e = thrown(EmptyStackException)
        println e
        Objects.isNull(e.cause)
    }

    def 'hashMap accept null key'(){
        given:
        def map = [:]

        when:
        map[null] = 'test'

        then:
        notThrown(NullPointerException)
    }

    @Unroll
    def 'computing the maximum of two number style one'(){
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 4, 3]
        b << [2, 3, 1]
        c << [2, 4, 3]
    }

    @Rollup
    def 'computing the maximum of two number style two'(){
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 2 || 2
        2 | 3 || 3
    }

    def 'offered PC matches preferred configuration'(){
        when:
        def pc = Shop.buyPc()

        then:
        pc.vendor == 'Sunny'
        pc.clockRate >= 2333
        pc.ram >= 4096
        pc.os == 'Linux'
    }

    def 'offered PC matches preferred configuration v2'(){
        when:
        def pc = Shop.buyPc()

        then:
        match_preferred_configuration pc
    }

    void match_preferred_configuration(pc){
        assert pc.vendor == 'Sunny'
        assert pc.clockRate >= 2333
        assert pc.ram >= 4096
        assert pc.os == 'Linux'
    }
}

class Shop {
    def static buyPc(){
        return new Pc()
    }

    static class Pc {
        def vendor
        def clockRate
        def ram
        def os

        Pc(){
            this.vendor = 'Sunny'
            this.clockRate = 3000
            this.ram = 5000
            this.os = 'Linux'
        }
    }
}
