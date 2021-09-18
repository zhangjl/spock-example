import com.drivingrange.dao.City
import com.drivingrange.dao.CityMapper
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import spock.lang.Specification

import java.sql.Statement

/**
 * @author zhangjl
 */
class DaoSpec extends Specification {
    static SqlSessionFactory sqlSessionFactory

    def setupSpec(){
        try (def is = Resources.getResourceAsStream('mybatis-config-spock-h2.xml')){
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is)
            sqlSessionFactory.getConfiguration().addMapper(CityMapper)
        } catch (Exception e) {
        }

        SqlSession session = sqlSessionFactory.openSession(true)
        String initSql = getClass().getClassLoader().getResource('h2/init.sql').readLines().join('\n')
        String insertSql = getClass().getClassLoader().getResource('h2/insert.sql').readLines().join('\n')

        Statement stm = session.getConnection().createStatement()
        stm.execute(initSql)
        stm.execute(insertSql)

        session.close()
    }

    def 'dao dat select test'(){
        given:
        SqlSession session = sqlSessionFactory.openSession(true)

        and:
        CityMapper mapper = session.getMapper(CityMapper)

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
