package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceTemplate {

    private final LogTrace trace;

    //실행 메소드의 타입이 모두 다르기에 제네릭 선언
    public <T> T execute(String message, TraceCallBack<T> callBack) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = callBack.call();

            trace.end(status);
            return result;

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
