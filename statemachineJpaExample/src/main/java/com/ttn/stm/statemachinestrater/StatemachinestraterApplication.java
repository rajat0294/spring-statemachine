package com.ttn.stm.statemachinestrater;

import java.io.Serializable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;


@SpringBootApplication
@EntityScan
public class StatemachinestraterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatemachinestraterApplication.class, args);
	}
	
	@Bean
    public StateMachinePersist<String, String, ContextEntity<String, String, Serializable>> persist() {
         return new StateMachinePersist<String, String, ContextEntity<String, String, Serializable>>() {

           @Override
            public StateMachineContext<String, String> read(
                    ContextEntity<String, String, Serializable> order) throws Exception {
                return order.getStateMachineContext();
            }

            @Override
            public void write(StateMachineContext<String, String> context,
                    ContextEntity<String, String, Serializable> order) throws Exception {
                order.setStateMachineContext(context);
            }
        };
    }
}
