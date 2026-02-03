#agent   jdk17,jdk21
java -javaagent:./org.jacoco.agent-0.8.11-runtime.jar=includes=*,output=tcpserver,port=18513,address=*,append=true,destfile=/jacoco.exec -jar api-server-0.0.1-SNAPSHOT.jar --server.port=9999

