package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubClassLogic2 extends AbstractTemplate{
    //부모클래스의 메소드 구현하여 다형성 적한
    @Override
    protected void call() {
      log.info("비즈니스 로직 2 실행");
    }
}
