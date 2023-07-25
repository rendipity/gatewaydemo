package reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Mono;

@Slf4j
public class MnoTest {

    @Test
    public void monoTest(){
        log.info("monoTest");
        Mono.just("hello").subscribe(log::info);
    }
}
