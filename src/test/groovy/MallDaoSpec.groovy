import com.drivingrange.dao.City
import com.drivingrange.dao.CityMapper
import com.meituan.mall.spock.mock.MallDaoMock
import spock.lang.Specification

/**
 * @author zhangjl
 */
class MallDaoSpec extends Specification {

    def 'dao select test with mall dao mock'(){
        given:
        def mock = MallDaoMock.getInstance(null, Collections.singletonList(CityMapper), null,
                Arrays.asList('init.sql', 'insert.sql'))

        and:
        CityMapper mapper = mock.mock(CityMapper)

        when:
        City city = mapper.findByState(state)

        then:
        city.getName() == name
        city.getCountry() == country
        city.getState() == state
        city.getId() == id

        where:
        state   || name   | country | id
        '北京市' || '北京' | '中国'    | 2
        '河北省' || '保定' | '中国'    | 1
    }
}
