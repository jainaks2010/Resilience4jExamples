package com.vodafone.demo.of.resilience4j;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

@Service
@Slf4j
public class AnyFaultyService {

    @Getter
    @Setter
    private  Integer callCount = 0;
    @Getter
    @Setter
    private Integer successOnCount = 3;

    public void alwaysThrowRuntimeException() {
        throw new RuntimeException("Runtime Exception by a faulty service");
    }

    public void alwaysThrowException(Exception exception) throws Exception {
        throw new Exception("Runtime Exception by a faulty service");
    }

    public void delayBy(int delayInSeconds) {
        Utils.sleep(delayInSeconds);
    }

    @SneakyThrows
    public <T, E extends Exception> void throwExceptionOnCondition(T data, Predicate<T> predicate, E exception) {
        if (predicate.test(data)) {
            throw exception;
        }
        return;
    }

    public void successOnNthCall() {
       increaseCallCount();
       // log.info("Called method successOnNthCall: callCount = " + getCallCount());
        if (successOnCount <= getCallCount()) {
            log.info("Returning Successfully : callCount = " + getCallCount());
            resetCallCount();
            return;
        } else {
            log.info("Throwing Exception : callCount = " + getCallCount());
            throw new RuntimeException("Success count " + successOnCount + " more than current count " + getCallCount());
        }


    }

    private void resetCallCount() {
        callCount = 0;
    }

    private void increaseCallCount() {
            callCount++;
    }


    public void throwIOException() throws IOException {
        throw new IOException("IOException");
    }
}
