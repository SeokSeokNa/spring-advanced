package hello.advanced.trace;

import lombok.Getter;

import java.util.UUID;
//로그 내용 만들기
@Getter
public class TraceId {

    private String id; //트랜잭션 ID
    private int level; //Depth

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        //uuid 중 앞에 8자리만 사용
        return UUID.randomUUID().toString().substring(0,8);
    }

    //다음 depth 로그를 표현하기 위한 메소드
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }
    //이전 depth 로그를 표현하기 위한 메소드
    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    //첫번쨰 depth 인가 ?? 를 판단하는 편의기능 메소드
    public boolean isFirstLevel() {
        return level == 0;
    }
}
