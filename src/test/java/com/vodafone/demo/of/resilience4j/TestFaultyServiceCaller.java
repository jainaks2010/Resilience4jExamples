package com.vodafone.demo.of.resilience4j;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest(classes = StartMe.class)
public class TestFaultyServiceCaller {

    @SpyBean
    private FaultyServiceCaller testFaultyServiceCaller;


    @RepeatedTest(10)
    public void testAlwaysThrowException(RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        testFaultyServiceCaller.alwaysThrowException();
        if (currentRepetition <= 5) {
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).alwaysThrowException();
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).fallBackMethod(any());
        } else {
            Mockito.verify(testFaultyServiceCaller, Mockito.times(0)).alwaysThrowException();
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).fallBackMethod(any());
        }
    }


    @RepeatedTest(10)
    public void testThrowExceptionForEvenNumbers(RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        try {
            testFaultyServiceCaller.throwExceptionForEvenNumbers(currentRepetition);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if(currentRepetition == 1 || currentRepetition == 3 ){
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).throwExceptionForEvenNumbers(anyInt());
            Mockito.verify(testFaultyServiceCaller, Mockito.times(0)).fallBackMethod(any());
        }else if(currentRepetition == 2 || currentRepetition == 4){
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).throwExceptionForEvenNumbers(anyInt());
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).fallBackMethod(any());
        }else{
            Mockito.verify(testFaultyServiceCaller, Mockito.times(0)).throwExceptionForEvenNumbers(anyInt());
            Mockito.verify(testFaultyServiceCaller, Mockito.times(1)).fallBackMethod(any());
        }


    }

}
