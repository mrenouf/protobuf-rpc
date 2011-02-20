package com.bitgrind.protobuf.rpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLite.Builder;


public abstract class AbstractProtobufService<T extends MessageLite> implements ProtobufService<T> {
  private final Logger logger = Logger.getLogger(getClass().getName());

  @SuppressWarnings("unchecked")
  T cast(MessageLite message) {
    return (T) message;
  }

  @Override
  public MessageLite service(MessageLite message) {
    logger.log(Level.FINE,"service: {0}", message);
    return handleRequest(cast(message));
  }

  @Override
  public abstract Builder newBuilder();

  public abstract MessageLite handleRequest(T request);
}
