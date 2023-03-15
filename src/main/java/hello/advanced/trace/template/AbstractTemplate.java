package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

/* 템플릿 메소드 패턴에서 변경점이 없는 코드를 적용한 부모역활을 하는 추상 클래스*/
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> { //메소드마다 리턴값이 다르기에 제네릭으로 선언

    private final LogTrace trace;

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //상속 받아 로직 호출
            T result = call();

            trace.end(status);
            return result;

        } catch (Exception e) {
            trace.exception(status , e);
            throw e;
        }
    }

    protected abstract T call();
}
