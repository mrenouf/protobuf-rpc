/**
 * 
 */
package com.bitgrind.protobuf.rpc.http;

import com.bitgrind.protobuf.rpc.MessagePb.Response;

public class Result<ResponseType> {
  private final ResponseType response;
  private final Response failure;
  private final int statusCode;
  private final String statusText;
  private final Throwable exception;
  
  public Result(int statusCode, String statusText, ResponseType response) {
    this.statusCode = statusCode;
    this.statusText = statusText;
    this.response = response;
    this.exception = null;
    this.failure = null;
  }

  public Result(int httpStatus, String statusText, Response failure) {
    this.statusCode = httpStatus;
    this.statusText = statusText;
    this.response = null;
    this.exception = null;
    this.failure = failure;
  }

  public Result(int statusCode, String statusText) {
    this.statusCode = statusCode;
    this.statusText = statusText;
    this.response = null;
    this.exception = null;
    this.failure = null;
    }

  public Result(Throwable exception) {
    this.statusCode = 0;
    this.statusText = null;
    this.response = null;
    this.exception = exception;
    this.failure = null;
  }
  
  public boolean isSuccess() {
    return hasResponse();
  }

  public boolean hasException() {
    return exception != null;
  }
  
  public Throwable getException() {
    return exception;
  }

  public boolean hasStatusCode() {
    return statusCode != 0;
  }
  
  public int getStatusCode() {
    return statusCode;
  }
  
  public boolean hasStatusText() {
    return statusText != null;
  }

  public String getStatusText() {
    return statusText;
  }
  
  public boolean hasFailureResponse() {
    return failure != null;
  }
  
  public Response getFailure() {
    return failure;
  }
  
  public boolean hasResponse() {
    return response != null;
  }
  
  public ResponseType getResponse() {
    return response;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (hasStatusCode()) {
      sb.append(getStatusCode());
      if (hasStatusText())
        sb.append(": ").append(getStatusText());
      sb.append(" ");
    }
    if (hasResponse()) {
      sb.append(getResponse());
    } else if (hasFailureResponse()) {
      sb.append(getFailure());
    } else if (hasException()) {
      sb.append(getException());
    }
    return sb.toString().trim();
  }
}