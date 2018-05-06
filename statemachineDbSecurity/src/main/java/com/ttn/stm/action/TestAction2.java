package com.ttn.stm.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("testAction")
public class TestAction2 implements Action<String, String>{

    @Override
    public void execute(StateContext<String, String> context) {
      System.out.println("hello! Everyone i am the method that has been called by using bean name");   
    }

    
    
}
