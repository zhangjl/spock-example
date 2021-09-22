import org.junit.Rule
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import spock.lang.Specification

/**
 * 未通过
 * @author zhangjl
 */
@PrepareForTest([AbbreviationProvinceUtil.class])
class StudentServiceStaticSpec extends Specification {
    @Rule
    PowerMockRule powerMockRule = new PowerMockRule()

    def 'test AbbreviationProvinceUtil.convert2Abbreviation with PowerMock'(){
        def demo = new DemoGo()

        setup:
        PowerMockito.mockStatic(AbbreviationProvinceUtil.class)

        and:
        PowerMockito.when(AbbreviationProvinceUtil.convert2Abbreviation(Mockito.any(), Mockito.any()))
                .thenReturn(abbreviation)

        when:
        def response = demo.wrapper(province)

        then:
        response == abbr

        where:
        province | abbreviation || abbr
        '北京'    | '京'         || '京'
        '上海'    | '沪'         || '沪'
    }

    def 'test AbbreviationProvinceUtil.convert2Abbreviation with GroovyMock'(){
        def demo = new DemoGo()

        given:
        GroovyMock(AbbreviationProvinceUtil, global: true)
        AbbreviationProvinceUtil.convert2Abbreviation(_, _) >> abbreviation

        when:
        def response = demo.wrapper(province)

        then:
        response == abbr

        where:
        province | abbreviation || abbr
        '北京'    | '京'         || '京'
        '上海'    | '沪'         || '沪'
    }

    def 'test AbbreviationProvinceUtil.convert2Abbreviation with GroovySpy'(){
        def demo = new DemoGo()

        given:
        GroovySpy(AbbreviationProvinceUtil, global: true)
        AbbreviationProvinceUtil.convert2Abbreviation(_, _) >> abbreviation

        when:
        def response = demo.wrapper(province)

        then:
        response == abbr

        where:
        province | abbreviation || abbr
        '北京'    | '京'         || '京'
        '上海'    | '沪'         || '沪'
    }
}

class DemoGo {
    String wrapper(String province){
        return AbbreviationProvinceUtil.convert2Abbreviation(province, 0L)
    }
}

class AbbreviationProvinceUtil {
    static String convert2Abbreviation(String province, Long data){
        return null
    }
}
