mvn package
cd target

rm -rf peerA
rm -rf peerB
rm -rf server

mkdir peerA
mkdir peerB
mkdir server

cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar peerA/ 
cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar peerB/ 
cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar server/

cp -r ../rfc peerB/

cd server
java -cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar main/ServerMain & PID_S=$!

cd ../peerA
java -cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar main/ClientMainA & PID_A=$!

cd ../peerB
java -cp p2p-0.0.1-SNAPSHOT-jar-with-dependencies.jar main/ClientMainB & PID_B=$!

wait $PID_A
wait $PID_B

echo "Test Complete Killing Server"
kill -9 $PID_S
