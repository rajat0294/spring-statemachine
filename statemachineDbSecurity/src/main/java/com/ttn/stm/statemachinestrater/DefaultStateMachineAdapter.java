package com.ttn.stm.statemachinestrater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultStateMachineAdapter<S, E, T> {

    @Autowired
    public DefaultStateMachineAdapter(StateMachineFactory<S, E> stateMachineFactory,
            StateMachinePersist<S, E, T> persister) {
        super();
        this.stateMachineFactory = stateMachineFactory;
        this.persister = persister;
    }

    StateMachineFactory<S, E> stateMachineFactory;

    StateMachinePersist<S, E, T> persister;


    public StateMachine<S, E> restore(T contextObject, String machineId) throws Exception {


        /*
         * StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine(); return
         * persister.restore(stateMachine, contextObject);
         */
        StateMachine<S, E> stateMachine;

        final StateMachineContext<S, E> context = persister.read(contextObject);
        if (machineId != null) {
            stateMachine = stateMachineFactory.getStateMachine(machineId);
        } else {
            stateMachine = stateMachineFactory.getStateMachine();
        }

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(new StateMachineFunction<StateMachineAccess<S, E>>() {

                    @Override
                    public void apply(StateMachineAccess<S, E> function) {
                        function.resetStateMachine(context);
                    }
                });
        stateMachine.start();
        return stateMachine;
    }


    public void persist(StateMachine<S, E> stateMachine, T contextObject) throws Exception {

        persister.write(new DefaultStateMachineContext<S, E>(stateMachine.getState().getId(), null,
                null, stateMachine.getExtendedState()), contextObject);
        // persister.persist(stateMachine, order);
    }

    public StateMachine<S, E> create() {
        StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();
        return stateMachine;
    }

}
