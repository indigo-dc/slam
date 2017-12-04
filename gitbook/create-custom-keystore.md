# Quick tutorial: how to create a custom keystore and use it in SLAM

These instructions show how to replace certificates in SLAM service with Let's Encrypt signed certificates using certbot utility. 
In case the certificates were obtained from another CA, the steps are similar. 

## **Obtain a certificate**

You can install the certbot tool on your machine (1st approach) or you can use the docker image certbot/certbot (2nd approach).

  ````
  # Install certbot tool (https://certbot.eff.org/#ubuntuxenial-other)
  $ sudo apt-get install software-properties-common
  $ sudo add-apt-repository ppa:certbot/certbot
  $ sudo apt-get update
  $ sudo apt-get install certbot
  $ sudo certbot certonly --standalone -d $HOSTNAME

  # The certificates should be in:
  $ sudo ls /etc/letsencrypt/live/$HOSTNAME
  cert.pem  chain.pem  fullchain.pem  privkey.pem  README
  ````
or
  ````
  $ sudo docker run -it --rm -p 80:80 -p 443:443 -v /etc/letsencrypt:/etc/letsencrypt/  certbot/certbot certonly --standalone -d $HOSTNAME
  # The certificates should be in:
  $ sudo ls /etc/letsencrypt/live/$HOSTNAME
  cert.pem  chain.pem  fullchain.pem  privkey.pem  README
  ````

## **Create a new keystore**

1. Create the keystore:
  
  ````
  keytool -genkey -alias $HOSTNAME -keyalg RSA -keystore KeyStore.jks -keysize 2048
  ````

2. Generate a CSR based on the new keystore:
  
  ````
  keytool -certreq -alias $HOSTNAME -keystore KeyStore.jks -file $HOSTNAME.csr
  ````
  
Answer each question when prompted. 

## Import the certificate into the keystore.

1. Convert the certificate and private key to PKCS 12 (.p12)

  ````
  openssl pkcs12 -export -in /etc/letsencrypt/live/$HOSTNAME/cert.pem -inkey /etc/letsencrypt/live/$HOSTNAME/privkey.pem -name $HOSTNAME -out $HOSTNAME.p12
  ````
  
2. Import the certificate (.p12 file) to the keystore
  
  ````
  keytool -importkeystore -deststorepass [password] -destkeystore KeyStore.jks -srckeystore $HOSTNAME.p12 -srcstoretype PKCS12
  ````
  Where the [**password**] is the original password set when the private key was created.  
  
  You can verify that the certificate has been imported with the following command:
  
  ````
  keytool -list -keystore Keystore.jks
  ````

## Import other certificate in the keystore

Since SLAM uses IAM https endpoint, you should also add its CA certificate to the keystore as trusted certificate:

  ````
  keytool -import -noprompt -trustcacerts -alias mychain -file [certificate in pem format] -storepass [password] -keystore KeyStore.jks
  ````

## Use the newly created keystore into SLAM

Add the following options to docker run command:

  ````
  -e KEYSTORE_PASSWORD=[password] -e KEYSTORE_ALIAS=[alias] -e KEYSTORE=/opt/pki/customKeystore.jks -v /path/to/KeyStore.jks:/opt/pki/customKeystore.jks
  ````

where [password] is the password set when the keystore Keystore.jks and [alias] is the alias associated to your certificate.

