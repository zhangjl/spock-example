package com.drivingrange;

import groovy.transform.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjl
 */
public class OrderService {
    public List<OrderVo> convertOrders(List<OrderDto> orders){
        List<OrderVo> orderList = new ArrayList<>();
        for (OrderDto orderDTO : orders) {
            OrderVo orderVO = OrderMapper.INSTANCE.convert(orderDTO);
            if (1 == orderVO.getType()) {
                orderVO.setOrderDesc("App端订单");
            } else if(2 == orderVO.getType()) {
                orderVO.setOrderDesc("H5端订单");
            } else if(3 == orderVO.getType()) {
                orderVO.setOrderDesc("PC端订单");
            }
            orderList.add(orderVO);
        }
        return orderList;
    }

    @ToString
    public static class OrderDto {
        private Integer type;
        private String orderDesc;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getOrderDesc() {
            return orderDesc;
        }

        public void setOrderDesc(String orderDesc) {
            this.orderDesc = orderDesc;
        }
    }

    @ToString
    public static class OrderVo {
        private Integer type;
        private String orderDesc;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getOrderDesc() {
            return orderDesc;
        }

        public void setOrderDesc(String orderDesc) {
            this.orderDesc = orderDesc;
        }
    }
}
