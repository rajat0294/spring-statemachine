package com.example.statemachineAnnotation;

import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(name="mymachine")
public class Bean1 {

    @OnStateChanged
    public void onStateChanged() {
        System.out.println("success.you got it right..........  ");
    }
}