# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc
import warnings

import chat_pb2 as chat__pb2

GRPC_GENERATED_VERSION = '1.67.0'
GRPC_VERSION = grpc.__version__
_version_not_supported = False

try:
    from grpc._utilities import first_version_is_lower
    _version_not_supported = first_version_is_lower(GRPC_VERSION, GRPC_GENERATED_VERSION)
except ImportError:
    _version_not_supported = True

if _version_not_supported:
    raise RuntimeError(
        f'The grpc package installed is at version {GRPC_VERSION},'
        + f' but the generated code in chat_pb2_grpc.py depends on'
        + f' grpcio>={GRPC_GENERATED_VERSION}.'
        + f' Please upgrade your grpc module to grpcio>={GRPC_GENERATED_VERSION}'
        + f' or downgrade your generated code using grpcio-tools<={GRPC_VERSION}.'
    )


class ChatServerStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.Conectar = channel.unary_unary(
                '/grpc.ChatServer/Conectar',
                request_serializer=chat__pb2.NombreCliente.SerializeToString,
                response_deserializer=chat__pb2.Confirmacion.FromString,
                _registered_method=True)
        self.Desconectar = channel.unary_unary(
                '/grpc.ChatServer/Desconectar',
                request_serializer=chat__pb2.NombreCliente.SerializeToString,
                response_deserializer=chat__pb2.Confirmacion.FromString,
                _registered_method=True)
        self.EnviarMensaje = channel.unary_unary(
                '/grpc.ChatServer/EnviarMensaje',
                request_serializer=chat__pb2.Mensaje.SerializeToString,
                response_deserializer=chat__pb2.Confirmacion.FromString,
                _registered_method=True)
        self.SolicitarHistorial = channel.unary_unary(
                '/grpc.ChatServer/SolicitarHistorial',
                request_serializer=chat__pb2.Vacio.SerializeToString,
                response_deserializer=chat__pb2.HistorialResponse.FromString,
                _registered_method=True)
        self.ChatStream = channel.unary_stream(
                '/grpc.ChatServer/ChatStream',
                request_serializer=chat__pb2.Vacio.SerializeToString,
                response_deserializer=chat__pb2.Mensaje.FromString,
                _registered_method=True)


class ChatServerServicer(object):
    """Missing associated documentation comment in .proto file."""

    def Conectar(self, request, context):
        """Conectar cliente al chat
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def Desconectar(self, request, context):
        """Desconectar cliente del chat
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def EnviarMensaje(self, request, context):
        """Enviar un mensaje al chat
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def SolicitarHistorial(self, request, context):
        """Solicitar el historial de mensajes
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ChatStream(self, request, context):
        """Stream para recibir mensajes en tiempo real
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_ChatServerServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'Conectar': grpc.unary_unary_rpc_method_handler(
                    servicer.Conectar,
                    request_deserializer=chat__pb2.NombreCliente.FromString,
                    response_serializer=chat__pb2.Confirmacion.SerializeToString,
            ),
            'Desconectar': grpc.unary_unary_rpc_method_handler(
                    servicer.Desconectar,
                    request_deserializer=chat__pb2.NombreCliente.FromString,
                    response_serializer=chat__pb2.Confirmacion.SerializeToString,
            ),
            'EnviarMensaje': grpc.unary_unary_rpc_method_handler(
                    servicer.EnviarMensaje,
                    request_deserializer=chat__pb2.Mensaje.FromString,
                    response_serializer=chat__pb2.Confirmacion.SerializeToString,
            ),
            'SolicitarHistorial': grpc.unary_unary_rpc_method_handler(
                    servicer.SolicitarHistorial,
                    request_deserializer=chat__pb2.Vacio.FromString,
                    response_serializer=chat__pb2.HistorialResponse.SerializeToString,
            ),
            'ChatStream': grpc.unary_stream_rpc_method_handler(
                    servicer.ChatStream,
                    request_deserializer=chat__pb2.Vacio.FromString,
                    response_serializer=chat__pb2.Mensaje.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'grpc.ChatServer', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('grpc.ChatServer', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class ChatServer(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def Conectar(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/grpc.ChatServer/Conectar',
            chat__pb2.NombreCliente.SerializeToString,
            chat__pb2.Confirmacion.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def Desconectar(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/grpc.ChatServer/Desconectar',
            chat__pb2.NombreCliente.SerializeToString,
            chat__pb2.Confirmacion.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def EnviarMensaje(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/grpc.ChatServer/EnviarMensaje',
            chat__pb2.Mensaje.SerializeToString,
            chat__pb2.Confirmacion.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def SolicitarHistorial(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/grpc.ChatServer/SolicitarHistorial',
            chat__pb2.Vacio.SerializeToString,
            chat__pb2.HistorialResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ChatStream(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(
            request,
            target,
            '/grpc.ChatServer/ChatStream',
            chat__pb2.Vacio.SerializeToString,
            chat__pb2.Mensaje.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)
