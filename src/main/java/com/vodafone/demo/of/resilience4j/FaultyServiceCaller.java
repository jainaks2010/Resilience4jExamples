package com.vodafone.demo.of.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Slf4j
public class FaultyServiceCaller {

    @Autowired
    private AnyFaultyService anyFaultyService;

    @CircuitBreaker(name = "backendWhichAlwaysThrowsException",fallbackMethod = "fallBackMethod")
    public void alwaysThrowException(){
        log.info("Called method : alwaysThrowException");
        anyFaultyService.alwaysThrowRuntimeException();
    }

    @CircuitBreaker(name = "backendWhichThrowsExceptionOnEvenNumbers",fallbackMethod = "fallBackMethod")
    public void throwExceptionForEvenNumbers(Integer number){
        log.info("Called method : throwExceptionForEvenNumbers with number:"+number);
        anyFaultyService.throwExceptionOnCondition(number,Utils.oddEven(),new NumberFormatException());
    }

    @Retry(name = "backendWhichThrowsExceptionAfterGivenCount",fallbackMethod = "fallBackMethod")
    public void backendWhichSucceedAfterThrowingException(){
        log.info("Called method : backendWhichThrowsExceptionAfterGivenCount");
        anyFaultyService.setSuccessOnCount(3);
        anyFaultyService.successOnNthCall();
    }

    @Retry(name = "backendWhichThrowsExceptionAfterGivenCount",fallbackMethod = "fallBackMethod")
    public void backendWhichThrowsExceptionAfterGivenCount(){
        log.info("Called method : backendWhichThrowsExceptionAfterGivenCount");
        anyFaultyService.setSuccessOnCount(4);
        anyFaultyService.successOnNthCall();
    }

    @Retry(name = "backendWhichThrowsValidationException",fallbackMethod = "fallBackMethod")
    public void backendWhichThrowsValidationException() throws IOException {
        log.info("Called method : backendWhichThrowsKnownException");
        anyFaultyService.throwIOException();
    }

    public void fallBackMethod(Throwable throwable){
      log.error("In Fallback method Error thrown "+throwable.getMessage());
    }


}
