package com.ttn.stm.statemachinestrater;

import java.util.EnumSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer.History;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State,Event>{
   
    @Override
    public void configure(StateMachineStateConfigurer<State,Event> states)
            throws Exception {
        states
            .withStates()
                .initial(State.RUNNING)
                .state(State.POWEROFF)
                .end(State.END)
                .and()
                .withStates()
                .parent(State.RUNNING)
                .initial(State.WASHING)
                .state(State.RINSING)
                .state(State.DRYING)
                .history(State.HISTORY, History.SHALLOW);
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<State,Event> transitions)
            throws Exception {
        transitions
        .withExternal()
        .source(State.WASHING).target(State.RINSING)
        .event(Event.RINSE)
        .and()
    .withExternal()
        .source(State.RINSING).target(State.DRYING)
        .event(Event.DRY)
        .and()
    .withExternal()
        .source(State.RUNNING).target(State.POWEROFF)
        .event(Event.CUTPOWER)
        .and()
    .withExternal()
        .source(State.POWEROFF).target(State.HISTORY)
        .event(Event.RESTOREPOWER)
        .and()
    .withExternal()
        .source(State.RUNNING).target(State.END)
        .event(Event.STOP);
        
}

    public Action<State, Event> receivePayment() {
        return context -> test();
    }

    private void test() {
        System.out.println("hi i am test method");
    }

    public Action<State, Event> action2() {
        return context -> test();
    }

}
