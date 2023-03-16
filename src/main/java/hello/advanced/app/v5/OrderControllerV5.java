package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallBack;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/* 템플릿 콜백 패턴 적용 */
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;


    // TraceTemplate를 필요한 곳에서 계속해서 생성하지 않기 위해 이렇게 생성자를 만들어서 사용 , logTrace 도 주입받기 위해
    // 생성자 방식이 싫으면 스프링 bean 으로 등록해서 사용해도 괜찮음
   public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(logTrace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
      return template.execute("OrderController.request()", new TraceCallBack<>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
