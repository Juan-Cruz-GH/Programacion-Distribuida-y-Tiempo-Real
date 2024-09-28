$vm1 = "vm1"
$server_ip = "192.168.0.187"
$server_port = "8080"


# Empezar el servidor vm1 en background
Write-Output "Comenzando servidor en $vm1..."
Start-Process -NoNewWindow -FilePath "vagrant" -ArgumentList "ssh $vm1 -c 'cd ../../vagrant && java ServerEjercicio3.java $server_port'" -PassThru


# Le da tiempo al servidor para levantar (no es necesario)
Start-Sleep -Seconds 10


# Ejecutar el cliente en el Host
Write-Output "Comenzando cliente en el Host"
java ClientEjercicio3.java $server_ip $server_port


Write-Output "Fin."