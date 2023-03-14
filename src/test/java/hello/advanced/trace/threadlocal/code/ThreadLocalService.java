package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    //싱글톤 방식에서 동시성 문제를 해결 하기 위해 사용하는 것으로 요청마다 개별 변수처럼 가지고 있을 수 있게됨
    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}" , name , nameStore.get());
        nameStore.set(name);
        sleep(1000); //저장시 1초 걸린다 치자
        log.info("조회 nameStore={}" , nameStore.get());
        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
