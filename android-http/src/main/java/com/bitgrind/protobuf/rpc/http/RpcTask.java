/**
 * 
 */
package com.bitgrind.protobuf.rpc.http;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.bitgrind.protobuf.rpc.MessagePb.Request;
import com.bitgrind.protobuf.rpc.MessagePb.Response;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLite.Builder;

public class RpcTask<ResponseType extends MessageLite> extends AsyncTask<MessageLite, String, Result<ResponseType>> {
  private final HttpClient client = new DefaultHttpClient();
  private final String serviceName;
  private final Builder builder;
  private final URI endpoint;
  
  public RpcTask(URI endpoint, String serviceName, Builder builder) {
    this.endpoint = endpoint;
    this.serviceName = serviceName;
    this.builder = builder;
  }
  
  @SuppressWarnings("unchecked")
  private ResponseType cast(MessageLite o) {
    return (ResponseType) o;
  }
  
  @Override
  protected final Result<ResponseType> doInBackground(MessageLite... params) {
    HttpPost post = new HttpPost(endpoint);
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/x-www-form-urlencoded");
    Request request = Request.newBuilder().setName(serviceName).setBuffer(params[0].toByteString()).build();
    post.setEntity(new ByteArrayEntity(request.toByteArray()));

    try {
      Log.d("RpcTask", "Sending request");
      HttpResponse httpResponse = client.execute(post);
      Log.d("RpcTask", "HTTP Response: " + httpResponse.getStatusLine().toString());
      switch (httpResponse.getStatusLine().getStatusCode()) {
        case 200:
          Response response = Response.parseFrom(httpResponse.getEntity().getContent());
          switch (response.getStatus()) {
            case FAILURE:
              Log.d("RpcTask", "Failure: " + response.getStatusText());
              return new Result<ResponseType>(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase(), response);
            case SUCCESS:
              Log.d("RpcTask", "Success");
              builder.mergeFrom(response.getBuffer());
              return new Result<ResponseType>(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase(), cast(builder.build()));
            default:
              throw new IllegalStateException("Unhandled response status: " + response.getStatus());
          }
        default:
          return new Result<ResponseType>(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
      }
    }
    catch (IOException e) {
      Log.d("RpcTask", "Caught exception: ", e);
      return new Result<ResponseType>(e);
    }
  }
}