package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder; //로그를 남겨야하는 모든 메소드에 TraceId 파라미터를 넘기지 않기위함(클래스 내부에 관리되는 필드로 메소드마다 꺼내쓰기) , 동시성 이슈가 발생..

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = this.traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
                traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //TraceId 동기화
    private void syncTraceId() {
        if (traceIdHolder == null) { //트랜젝션 시작시 처음 로그를 위해
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId(); //트랜잭션 진행중 다음 로그를 위해
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }

        releaseTraceId();
    }

    //로그 표기가 모두 완료되고 해당 TraceId 파괴하기
    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) { //로그의 시작도 depth가 1 이지만 끝도 1이라서
            traceIdHolder = null;//파괴
        } else {
            traceIdHolder = traceIdHolder.createPreviousId(); //depth 하나씩 줄여가기
        }
    }


    //로그 표현
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
