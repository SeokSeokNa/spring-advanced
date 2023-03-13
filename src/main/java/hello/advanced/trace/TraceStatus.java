package hello.advanced.trace;

import lombok.Getter;

//log 의 상태정보를 표현 하는 클래스
@Getter
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs; //어떤 트랜잭션의 종료시 총 걸린 시간을 계산하기 위함
    public String message;// 시작 과 종료를 나타내는

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
}
