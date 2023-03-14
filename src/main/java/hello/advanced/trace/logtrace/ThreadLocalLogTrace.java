package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>(); //ThreadLocal 을 사용해서 스레드별로 개별적으로 값은 관리할 수 있도록 함(동시성 이슈 해결!!)

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = this.traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
                traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //TraceId 동기화
    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) { //트랜젝션 시작시 처음 로그를 위해
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId()); //트랜잭션 진행중 다음 로그를 위해
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
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) { //로그의 시작도 depth가 1 이지만 끝도 1이라서
            traceIdHolder.remove();//파괴(ThreadLocal 사용시 필요없는 데이터는 꼭 remove하여 삭제해야한다 안그러면 삭제되지 않아 계속해서 메모리상에 남아있음 , )
            /*
                스레드 풀을 사용하게 되면 해당 스레드는 사용 후 스레드풀에 반납이 된다.
                만약 해당 스레드안에 관리되는 값이 있다면 이후 다른요청에서 해당 스레드를 이용해서 하면 다른요청은 해당 스레드가 전에 가지고 있던값에 영향을 미치게된다..!!
                로직이 끝나면 꼭 remove 하자 !!.
            */
        } else {
            traceIdHolder.set(traceId.createPreviousId()); //depth 하나씩 줄여가기
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
