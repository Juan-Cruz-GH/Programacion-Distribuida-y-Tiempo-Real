#include <math.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>

void error(char *msg) {
  perror(msg);
  exit(0);
}

int main(int argc, char *argv[]) {
  int sockfd, portno;
  struct sockaddr_in serv_addr;
  struct hostent *server;

  if (argc < 3) {
    fprintf(stderr, "Client:: usage %s hostname port\n", argv[0]);
    exit(0);
  }

  // Toma el numero de puerto de los argumentos
  portno = atoi(argv[2]);

  // Crea el file descriptor del socket para la conexion
  // 	AF_INET - familia del protocolo - ipv4 protocols internet
  // 	SOCK_STREAM - tipo de socket
  sockfd = socket(AF_INET, SOCK_STREAM, 0);

  if (sockfd < 0) {
    error("Client:: ERROR opening socket");
  }

  // Toma la direccion del server de los argumentos
  server = gethostbyname(argv[1]);
  if (server == NULL) {
    fprintf(stderr, "Client:: ERROR, no such host\n");
    exit(0);
  }
  bzero((char *)&serv_addr, sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;

  // Copia la direccion ip y el puerto del servidor a la estructura del socket
  bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr,
        server->h_length);
  serv_addr.sin_port = htons(portno);

  // Descriptor - direccion - tamaño direccion
  if (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
    error("Client:: ERROR connecting");
  }

  // Send 4 messages, each with different sizes
  char buffer[1000000];
  int inicial = 10;
  for (int i = 1; i < 7; i++) {
    int msg_len = inicial;
    clock_t t; 
    t = clock(); 
    // Clean buffer
    bzero(buffer, msg_len);

    // Fill buffer with '$'
    memset(buffer, '$', msg_len);

    // Send message to socket
    printf("Client:: Sending a %d (10^%d) bytes long message\n", msg_len, i);
    if (write(sockfd, buffer, msg_len) < 0) {
      error("Client:: ERROR writing to socket");
    }

    // Await server's confirmation
    read(sockfd, buffer, strlen("Recieved."));
    t = clock() - t; 
    double time_taken = ((double)t)/CLOCKS_PER_SEC; // in seconds 
    bzero(buffer, msg_len);

    inicial = inicial * 10;
    printf("Tiempo: %f\n", time_taken);
  }
  close(sockfd);

  return 0;
}