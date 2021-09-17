import com.drivingrange.OrderService
import com.drivingrange.OrderMapper
import org.powermock.reflect.Whitebox
import spock.lang.Specification

/**
 * @author zhangjl
 */
class OrderMapperSpec extends Specification {

    def 'test final static instance'(){
        given: 'Mock掉OrderMapper的静态final变量INSTANCE，并结合Spock设置动态返回值'
        def orderService = new OrderService()

        def orderMapper = Mock(OrderMapper)
        Whitebox.setInternalState(OrderMapper, 'INSTANCE', orderMapper)
        orderMapper.convert(_) >> orderVo

        when:
        def orderList = orderService.convertOrders([new OrderService.OrderDto()])

        then:
        orderList.get(0).getOrderDesc() == desc

        where:
        orderVo              || desc
        new OrderService.OrderVo(type: 1) || 'App端订单'
        new OrderService.OrderVo(type: 2) || 'H5端订单'
        new OrderService.OrderVo(type: 3) || 'PC端订单'
        new OrderService.OrderVo(type: 4) || null
    }
}
