import grpc
from concurrent import futures
import time
import experimento_pb2
import experimento_pb2_grpc

class MyServiceServicer(experimento_pb2_grpc.MyServiceServicer):
    def SimpleResponse(self, request, context):
        data_length = len(request.data)
        print(f"Received {data_length} bytes from the client.")
        return experimento_pb2.MyResponse(message="Hello, gRPC!")

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    experimento_pb2_grpc.add_MyServiceServicer_to_server(MyServiceServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server started on port 50051")
    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == '__main__':
    serve()
