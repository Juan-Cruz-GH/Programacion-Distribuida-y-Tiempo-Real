import grpc
from book_service_pb2_grpc import BookServiceStub
from book_service_pb2 import AddBookRequest, ListBooksRequest, Book

def run():
    # Crear un canal hacia el servidor gRPC
    with grpc.insecure_channel('localhost:8080') as channel:
        # Crear el stub del cliente
        stub = BookServiceStub(channel)

        # Listar todos los libros
        list_books_request = ListBooksRequest()
        list_books_response = stub.ListBooks(list_books_request)
        
        print("List of books:")
        for book in list_books_response.books:
            print(book)

if __name__ == "__main__":
    run()
