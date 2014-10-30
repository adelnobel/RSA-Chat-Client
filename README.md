This is a simple chat server that uses RSA for encrypting the messages between the client and the server

The KDC provides a a public and private key for both client and the server, here are the steps to use it

1- Open the KDC and let the window open
2- Open the chat server, you should get a message saying that it's waiting for connections, if you didn't do step 1
you will get "Couldn't connect to KDC error!". 
The server will communicate with the KDC to generate a public & private key for it.
The KDC server will save the public key along with the ID of the server in a database for future requests from clients 
who want to get the public key of the server (SQLite database was used for this purpose)

3- Open the chat client and it should establish the connection with the KDC to generate it's public & private keys
Then a connection will be established with the chat server, if the KDC or the chat server were not running 
you will get an error
 



Notes:
1- The server ip, port, key length, server id, client id, KDC port are defined STATICALLY within their correspondeing 
classes, they can be changed.

2- Currently, the message length is assumed to be < key length / 8 , to ensure that the when the ascii message 
is converted to integer, that integer will be less than the MODULUS n. Otherwise the message will be congruent to a number 
less than n and the message will be lost after applying the MODULUS operation. The correct way to do this is by making all 
messages of uniform size that's less than the key length but this feature is NOT IMPLEMENTED. Currently, the default key length 
is 1024, therefore a message length should roughly be less than 128 characters otherwise it won't be encrypted/decrypted correctly!

