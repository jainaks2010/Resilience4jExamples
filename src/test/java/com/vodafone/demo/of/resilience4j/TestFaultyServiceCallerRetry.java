package com.vodafone.demo.of.resilience4j;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = StartMe.class)
public class TestFaultyServiceCallerRetry {

    @SpyBean
    private FaultyServiceCaller testFaultyServiceCaller;

    @Test
    public void testRetryNCallsSuccess(){
        testFaultyServiceCaller.backendWhichSucceedAfterThrowingException();
        Mockito.verify(testFaultyServiceCaller,Mockito.times(3)).backendWhichSucceedAfterThrowingException();
        Mockito.verify(testFaultyServiceCaller,Mockito.times(0)).fallBackMethod(any());
    }

    @Test
    public void testRetryNCallsFailed(){
        testFaultyServiceCaller.backendWhichThrowsExceptionAfterGivenCount();
        Mockito.verify(testFaultyServiceCaller,Mockito.times(3)).backendWhichThrowsExceptionAfterGivenCount();
        Mockito.verify(testFaultyServiceCaller,Mockito.times(1)).fallBackMethod(any());
    }

    @Test
    public void testRetryStopOnValidationException(){
        try {
            testFaultyServiceCaller.backendWhichThrowsValidationException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(testFaultyServiceCaller,Mockito.times(1)).backendWhichThrowsValidationException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(testFaultyServiceCaller,Mockito.times(1)).fallBackMethod(any());

    }
}
