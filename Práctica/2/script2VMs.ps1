$vm1 = "vm1"
$vm2 = "vm2"
$server_ip = "192.168.0.187"
$server_port = "8080"


# Empezar el servidor vm1 en background
Write-Output "Comenzando servidor en $vm1..."
Start-Process -NoNewWindow -FilePath "vagrant" -ArgumentList "ssh $vm1 -c 'cd ../../vagrant && java ServerEjercicio3.java $server_port'" -PassThru


# Ejecutar el cliente en vm2
Write-Output "Comenzando cliente en $vm2..."
vagrant ssh $vm2 -c "cd ../../vagrant && java ClientEjercicio3.java $server_ip $server_port"


Write-Output "Fin."