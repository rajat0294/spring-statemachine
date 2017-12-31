package com.ttn.stm.statemachinestrater;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State,Event>{
   /* @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config
        .withConfiguration()
        .listener(loggingListener());
    }*/

    @Override
    public void configure(StateMachineStateConfigurer<State,Event> states)
            throws Exception {
        states
            .withStates()
                .initial(State.S1)
                .states(EnumSet.allOf(State.class));
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<State,Event> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(State.S1)
                .target(State.S2)
                .event(Event.E1)
                .action(receivePayment())
            .and()
            .withExternal()
                .source(State.S2)
                .target(State.S3)
                .event(Event.E2)
                .action(action2());
        
}

    public Action<State,Event> receivePayment() {
        return context -> test();
    }

    private  void test() {
     System.out.println("hi i am test method");        
    }
    
    public Action<State,Event> action2() {
        return context -> test();
    }

    }
