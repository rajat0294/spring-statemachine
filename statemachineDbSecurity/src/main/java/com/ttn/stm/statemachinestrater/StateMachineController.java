package com.ttn.stm.statemachinestrater;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.ActionRepository;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryAction;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateMachineController {

  @Autowired
  private StateRepository<JpaRepositoryState> stateRepository;

  @Autowired
  private TransitionRepository<JpaRepositoryTransition> transitionRepository;
  
  @Autowired
  private ActionRepository<JpaRepositoryAction> actionRepository;

  @Autowired
  private StateMachineFactory<String, String> stateMachineFactory;

  /*@Autowired
  private StateMachine<String,String> stateMachine;*/
  
  @Autowired
  OrderRepository orderRepository;

  @Autowired
  DefaultStateMachineAdapter<String, String, Order> orderSmAdapter;
  
  @Autowired
  EntityManager em;
  
  


  @RequestMapping(value = "/state", method = RequestMethod.POST)
  public String createState() {
    JpaRepositoryState stateS1 = new JpaRepositoryState("S1", true);
    JpaRepositoryState stateS2 = new JpaRepositoryState("S2");
    JpaRepositoryState stateS3 = new JpaRepositoryState("S3");
    stateS1.setMachineId("machine1");
    stateS2.setMachineId("machine1");
    stateS3.setMachineId("machine1");
    stateRepository.save(stateS1);
    stateRepository.save(stateS2);
    stateRepository.save(stateS3);
    return "states added to machine";

  }

  @RequestMapping(value = "/event", method = RequestMethod.POST)
  public java.lang.String createEvent() throws Exception {
    JpaRepositoryState stateS1 = stateRepository.findOne((long) 1);
    JpaRepositoryState stateS2 = stateRepository.findOne((long) 2);
    JpaRepositoryState stateS3 = stateRepository.findOne((long) 3);
    JpaRepositoryTransition transitionS1ToS2 = new JpaRepositoryTransition(stateS1, stateS2, "E1");
    JpaRepositoryTransition transitionS2ToS3 = new JpaRepositoryTransition(stateS2, stateS3, "E2");
    HashSet actions=new HashSet<>();
    JpaRepositoryAction action=actionRepository.findOne((long)1);
    JpaRepositoryAction action2=actionRepository.findOne((long)2);
    actions.add(action);
    actions.add(action2);
    transitionS1ToS2.setActions(actions);
    transitionS1ToS2.setMachineId("machine1");
    transitionS2ToS3.setMachineId("machine1");
    transitionRepository.save(transitionS1ToS2);
    transitionRepository.save(transitionS2ToS3);
    
    return "transition created succesfully";

  }
  
  @RequestMapping(value = "/action", method = RequestMethod.POST)
  public java.lang.String createAction() throws Exception {
   
   JpaRepositoryAction action1=new JpaRepositoryAction();
   action1.setSpel("new com.ttn.stm.action.TestAction().sendEmail()");
   actionRepository.save(action1);
   JpaRepositoryAction action2 =new JpaRepositoryAction();
   action2.setName("testAction");
   actionRepository.save(action2);
    return "action created succesfully";

  }
  

  @RequestMapping(value = "/replaceevent", method = RequestMethod.POST)
  public java.lang.String replaceEvent() throws Exception {
    JpaRepositoryState stateS2 = stateRepository.findOne((long) 2);
    JpaRepositoryState stateS3 = stateRepository.findOne((long) 3);
    JpaRepositoryTransition transitionS1ToS2 = transitionRepository.findOne((long) 2);
    JpaRepositoryTransition transitionS2ToS3 = new JpaRepositoryTransition(stateS2, stateS3, "E3");
    transitionRepository.delete(transitionS1ToS2);
    transitionRepository.save(transitionS2ToS3);
    return "transition created succesfully";

  }


  @Transactional
  @RequestMapping(value = "/order", method = RequestMethod.POST)
  public String createOrder() throws Exception {
    StateMachine<String, String> newstateMachine = stateMachineFactory.getStateMachine("machine1");
    Order order = new Order();
    orderRepository.save(order);
    newstateMachine.start();
    orderSmAdapter.persist(newstateMachine, order);
    em.flush();
    em.clear();
    //bean.onStateChanged();
    return newstateMachine.getState().getId().toString();

  }

  @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
  public String getCurrentStateById(@PathVariable("id") Long id) throws Exception {
    Order order = orderRepository.findOne(id);
    // StateMachine<OrderState, OrderEvent> newstateMachine = stateMachineFactory.getStateMachine();
    StateMachine<String, String> stateMachine = orderSmAdapter.restore(order,"machine1");
    return stateMachine.getState().getId().toString() + " "
        + stateMachine.getState().getStates().toString();
  }


  @RequestMapping(value = "/order/{id}/{event}", method = RequestMethod.PUT)
  @Transactional
  public java.lang.String fireEvent(@PathVariable("id") Long id,
      @PathVariable("event") String event) throws Exception {
    // StateMachine<OrderState, OrderEvent> newstateMachine =
    // stateMachineFactory.getStateMachine();\
    Order order = orderRepository.findOne(id);
    StateMachine<String, String> stateMachine = orderSmAdapter.restore(order,"machine1");
    if (stateMachine.sendEvent(event)) {
      System.out.println(stateMachine.getTransitions().toString());
      orderSmAdapter.persist(stateMachine, order);
      em.flush();
      em.clear();
      return event.toString() + "event accepted";
    } else {
      return event.toString() + " event not allowed at this state";
    }

  }



}
