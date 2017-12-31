package com.ttn.stm.statemachinestrater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/sm")
public class StateMachineTestController {
    
    @Autowired
    private StateMachineFactory<State, Event> machineFactory;
    
    private StateMachine<State, Event> stateMachine;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createMachine() throws Exception {
        stateMachine=machineFactory.getStateMachine();
        stateMachine.start();
        return new ResponseEntity<String>("successfully created state machine",HttpStatus.OK);     
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getMachine() throws Exception {
        return new ResponseEntity<String>(stateMachine.toString(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{event}", method = RequestMethod.PUT)
    public ResponseEntity<String> fireEvent(@PathVariable("event") Event event) throws Exception {

        if (stateMachine.sendEvent(event)) {
            System.out.println(stateMachine.getTransitions().toString());
            return new ResponseEntity<String>("event accepted successfully", HttpStatus.OK);
        }

        else {
            System.out.println(stateMachine.getTransitions().toString());
            return new ResponseEntity<String>("event not accepted", HttpStatus.OK);
        }


    }
    
    
}
