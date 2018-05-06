package com.ttn.stm.statemachinestrater;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class CustomSerializer<S,E> extends StateMachineContextSerializer<S, E> {

  public StateMachineContext<S, E> create(Kryo kryo, Input input, Class<StateMachineContext<S, E>> clazz)
  {
    return super.read(kryo, input, clazz);
  }
  
}
