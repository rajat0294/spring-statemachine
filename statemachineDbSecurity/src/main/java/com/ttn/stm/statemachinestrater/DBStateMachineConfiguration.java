package com.ttn.stm.statemachinestrater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.annotation.OnEventNotAccepted;
import org.springframework.statemachine.annotation.OnTransitionStart;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.AbstractStateMachineModelFactory;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.data.RepositoryState;
import org.springframework.statemachine.data.RepositoryStateMachineModelFactory;
import org.springframework.statemachine.data.RepositoryTransition;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;

@Configuration
@EnableStateMachineFactory
public class DBStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String>{

  @Autowired
  private StateRepository<? extends RepositoryState> stateRepository;
  
  @Autowired
  private TransitionRepository<? extends RepositoryTransition> transitionRepository;
  
  @Autowired
  private ApplicationContext context;
  
  @Override
  public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
      model
          .withModel()
              .factory(modelFactory());
  }

  @Bean
  public StateMachineModelFactory<String, String> modelFactory() {
      StateMachineModelFactory<String, String> factory=new RepositoryStateMachineModelFactory(stateRepository, transitionRepository);
      ((AbstractStateMachineModelFactory<String, String>) factory).setBeanFactory(context);
      return factory;
  }
  
 
}
