package com.bitgrind.protobuf.rpc;

import org.junit.Test;

import com.bitgrind.protobuf.rpc.SamplePb.Item.Category;

public class JsonFormatTest {

  @Test
  public void testJsonFormat() {
    MessagePb.Request.Builder builder = MessagePb.Request.newBuilder();
    builder.setName("Test");

    SamplePb.ActivityStream stream = SamplePb.ActivityStream.newBuilder()
        .setTimestamp((int) System.currentTimeMillis() / 1000)
        .addItems(SamplePb.Item.newBuilder()
            .setCategory(Category.NEWS)
            .setLabel("Foo Bar")
            .setProcessed(true)
            .setIndex(8)
            .setContact(SamplePb.Contact
                .newBuilder()
                .setEmail("foo1@example.com")
                .setRank(64)
                .setName("John Doe")))
        .addItems(SamplePb.Item
            .newBuilder()
            .setLabel("Foo Bar")
            .setCategory(Category.NEWS)
            .setProcessed(true)
            .setIndex(1)
            .setContact(SamplePb.Contact
                .newBuilder()
                .setEmail("foo2@example.com")
                .setRank(11)
                .setName("John Schmoe")))
        .addItems(SamplePb.Item.newBuilder()
            .setCategory(Category.NEWS)
            .setLabel("Foo Bar")
            .setProcessed(true)
            .setIndex(6)
            .setContact(SamplePb.Contact.newBuilder()
                .setEmail("foo3@example.com")
                .setRank(24)
                .setName("John Blow")))
        .addItems(SamplePb.Item.newBuilder()
            .setCategory(Category.NEWS)
            .setLabel("Foo Bar")
            .setProcessed(true)
            .setIndex(7)
            .setContact(SamplePb.Contact.newBuilder()
                .setEmail("foo4@example.com")
                .setRank(17)
                .setName("John Foe")))
        .build();

    System.out.println(JsonFormat.printToString(stream));

    System.out.println(JsonFormat.printToString(builder.setBuffer(stream.toByteString()).build()));
  }
}
