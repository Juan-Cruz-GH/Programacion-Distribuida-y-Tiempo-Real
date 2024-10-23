# Comando para generar los .py a partir del .proto:

```
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. ./chat.proto
```
