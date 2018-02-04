package com.example.statemachineAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    
    @Autowired
    private StateMachineFactory<String, String> stateMachine;
    
    @RequestMapping(value = "/yahoo", method = RequestMethod.GET)
    public String demoExec()
    {
        StateMachine<String, String> sm=stateMachine.getStateMachine("mymachine");
        sm.start();
        sm.sendEvent("E1");
        return sm.getState().toString();
    }
    
    

}
