package com.ttn.stm.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class TestAction implements Action<String, String>{

    
    public void sendEmail()
    {
        System.out.println("sending Email .................");
    }


    @Override
    public void execute(StateContext<String, String> context) {
        // TODO Auto-generated method stub
        sendEmail();
    }
}