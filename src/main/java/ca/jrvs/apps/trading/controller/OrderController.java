package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/order")
public class OrderController {

  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @ApiOperation(value = "Create a marketOrder with DTO", notes = "Auto generate the security order")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(path = "/marketOrder", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public SecurityOrder createOrder(@RequestBody MarketOrderDto orderDto) {
    try {
      return orderService.executeMarketOrder(orderDto);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

}
