$vm1 = "vm1"
$vm2 = "vm2"
$server_ip = "192.168.0.187"
$server_port = "8080"


# Empezar el servidor vm1 en background
Write-Output "Comenzando servidor en $vm1..."
$serverProcess = Start-Process -NoNewWindow -FilePath "vagrant" -ArgumentList "ssh $vm1 -c 'cd ../../vagrant && java ServerEjercicio3.java $server_port'" -PassThru

# Asegura que comienza el servidor antes del cliente
Start-Sleep -Seconds 10

# Ejecutar el cliente en vm2 en background
Write-Output "Comenzando cliente en $vm2..."
$clientProcess = Start-Process -NoNewWindow -FilePath "vagrant" -ArgumentList "ssh $vm2 -c 'cd ../../vagrant && java ClientEjercicio3.java $server_ip $server_port'" -PassThru

# Esperar a que ambos procesos terminen
Write-Output "Esperando a que los procesos terminen..."
$serverProcess.WaitForExit()
$clientProcess.WaitForExit()

Write-Output "Fin."