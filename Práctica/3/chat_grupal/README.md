# Librerías requeridas:

1. `pip install fpdf`
2. `pip install grpcio grpcio-tools`

# Comando para generar los .py a partir del .proto:

```
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. ./chat.proto
```
