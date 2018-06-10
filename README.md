# Scala-U2F-Server

--------------------

The Scala U2F server is built based on open-source Yubico Java U2F server library. 

The Scala U2F server is used to illustrate the U2F registration and authentication flow and provide instructions on how to develop a standalone U2F server using Scala.

U2F Server Deployment
------------
+ Download the source repository from GitHub.
```
git clone https://github.com/winstonhong/scala-u2f-server
``` 
+ Run Scala U2F Server
```
cd scala-u2f-server
sbt run
```

U2F Registration Demo
------------
+ Access the link "https://yubikey.example.com:8080/settings" to launch the U2F registration demo
+ Click **Register**
+ Touch Yubico U2F token to accomplish U2F registration
+ "Registration completed" will be displayed if U2F token has been registered successfully

U2F Authentication Demo
------------
+ Access the link "https://yubikey.example.com:8080/settings" to launch the U2F authentication demo
+ Click **Authenticate**
+ Touch Yubico U2F token to accomplish U2F authentication
+ "Authentication completed" will be displayed if U2F token has been authenticated successfully

References
-------
Yubico Java U2F Server https://github.com/Yubico/java-u2flib-server
Server-side U2F library for Java https://developers.yubico.com/java-u2flib-server/

Support
-------
Scala U2F Server is supported by [winstonhong](https://github.com/winstonhong) @ [inbaytech](https://github.com/inbaytech)
