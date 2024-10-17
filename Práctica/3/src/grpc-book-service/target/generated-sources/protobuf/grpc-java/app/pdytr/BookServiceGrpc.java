package app.pdytr;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: book_service.proto")
public final class BookServiceGrpc {

  private BookServiceGrpc() {}

  public static final String SERVICE_NAME = "app.pdytr.BookService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<app.pdytr.BookServiceProto.AddBookRequest,
      app.pdytr.BookServiceProto.AddBookResponse> METHOD_ADD_BOOK =
      io.grpc.MethodDescriptor.<app.pdytr.BookServiceProto.AddBookRequest, app.pdytr.BookServiceProto.AddBookResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "app.pdytr.BookService", "AddBook"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              app.pdytr.BookServiceProto.AddBookRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              app.pdytr.BookServiceProto.AddBookResponse.getDefaultInstance()))
          .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("AddBook"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<app.pdytr.BookServiceProto.ListBooksRequest,
      app.pdytr.BookServiceProto.ListBooksResponse> METHOD_LIST_BOOKS =
      io.grpc.MethodDescriptor.<app.pdytr.BookServiceProto.ListBooksRequest, app.pdytr.BookServiceProto.ListBooksResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "app.pdytr.BookService", "ListBooks"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              app.pdytr.BookServiceProto.ListBooksRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              app.pdytr.BookServiceProto.ListBooksResponse.getDefaultInstance()))
          .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("ListBooks"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BookServiceStub newStub(io.grpc.Channel channel) {
    return new BookServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BookServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BookServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BookServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BookServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BookServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addBook(app.pdytr.BookServiceProto.AddBookRequest request,
        io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.AddBookResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_BOOK, responseObserver);
    }

    /**
     */
    public void listBooks(app.pdytr.BookServiceProto.ListBooksRequest request,
        io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.ListBooksResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_BOOKS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_BOOK,
            asyncUnaryCall(
              new MethodHandlers<
                app.pdytr.BookServiceProto.AddBookRequest,
                app.pdytr.BookServiceProto.AddBookResponse>(
                  this, METHODID_ADD_BOOK)))
          .addMethod(
            METHOD_LIST_BOOKS,
            asyncUnaryCall(
              new MethodHandlers<
                app.pdytr.BookServiceProto.ListBooksRequest,
                app.pdytr.BookServiceProto.ListBooksResponse>(
                  this, METHODID_LIST_BOOKS)))
          .build();
    }
  }

  /**
   */
  public static final class BookServiceStub extends io.grpc.stub.AbstractStub<BookServiceStub> {
    private BookServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceStub(channel, callOptions);
    }

    /**
     */
    public void addBook(app.pdytr.BookServiceProto.AddBookRequest request,
        io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.AddBookResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_BOOK, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listBooks(app.pdytr.BookServiceProto.ListBooksRequest request,
        io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.ListBooksResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_BOOKS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BookServiceBlockingStub extends io.grpc.stub.AbstractStub<BookServiceBlockingStub> {
    private BookServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public app.pdytr.BookServiceProto.AddBookResponse addBook(app.pdytr.BookServiceProto.AddBookRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_BOOK, getCallOptions(), request);
    }

    /**
     */
    public app.pdytr.BookServiceProto.ListBooksResponse listBooks(app.pdytr.BookServiceProto.ListBooksRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_BOOKS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BookServiceFutureStub extends io.grpc.stub.AbstractStub<BookServiceFutureStub> {
    private BookServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<app.pdytr.BookServiceProto.AddBookResponse> addBook(
        app.pdytr.BookServiceProto.AddBookRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_BOOK, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<app.pdytr.BookServiceProto.ListBooksResponse> listBooks(
        app.pdytr.BookServiceProto.ListBooksRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_BOOKS, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_BOOK = 0;
  private static final int METHODID_LIST_BOOKS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BookServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BookServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_BOOK:
          serviceImpl.addBook((app.pdytr.BookServiceProto.AddBookRequest) request,
              (io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.AddBookResponse>) responseObserver);
          break;
        case METHODID_LIST_BOOKS:
          serviceImpl.listBooks((app.pdytr.BookServiceProto.ListBooksRequest) request,
              (io.grpc.stub.StreamObserver<app.pdytr.BookServiceProto.ListBooksResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BookServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return app.pdytr.BookServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BookService");
    }
  }

  private static final class BookServiceFileDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier {
    BookServiceFileDescriptorSupplier() {}
  }

  private static final class BookServiceMethodDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BookServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BookServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BookServiceFileDescriptorSupplier())
              .addMethod(METHOD_ADD_BOOK)
              .addMethod(METHOD_LIST_BOOKS)
              .build();
        }
      }
    }
    return result;
  }
}
