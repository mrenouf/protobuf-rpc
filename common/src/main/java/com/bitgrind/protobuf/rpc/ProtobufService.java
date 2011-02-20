package com.bitgrind.protobuf.rpc;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLite.Builder;

public interface ProtobufService<T extends MessageLite> {
  Builder newBuilder();
  MessageLite service(MessageLite request);
}
