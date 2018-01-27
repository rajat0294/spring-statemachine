package com.ttn.stm.statemachinestrater;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.statemachine.StateMachineContext;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders", indexes = @Index(columnList = "currentState"))
public class Order extends AbstractPersistable<Long> implements ContextEntity<String, String, Long> { // NOSONAR
  
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
   
    StateMachineContext<String, String> stateMachineContext; // NOSONAR

    
    
    String currentState;

    public String getCurrentState() {
      return currentState;
    }

    public void setCurrentState(String currentState) {
      this.currentState = currentState;
    }

    @Override
    public void setStateMachineContext(StateMachineContext<String, String> stateMachineContext) {
        this.currentState = stateMachineContext.getState();
        this.stateMachineContext = stateMachineContext;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return super.isNew();
    }

    @Override
    public StateMachineContext<String, String> getStateMachineContext() {
      // TODO Auto-generated method stub
      return this.stateMachineContext;
    }
    
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }
}
