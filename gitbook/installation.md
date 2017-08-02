# Installation and deployement

This chapter describes how to build and run SLAM. The prefered method is running the SLAM from docker file.

### Running SLAM from source code

In order to build the project download the source code from the GitHub repository:  
[https://github.com/indigo-dc/slam](https://github.com/indigo-dc/slam)

#### Compilation and testing

To compile project use maven running the following command:

```
mvn compile
```

In order to run unit test suite run:

```
mvn test
```

In order to generate test coverage report run :

```
mvn cobertura:cobertura
```

In order to run stylechecks call:

```
mvn verify
```

#### Running SLAM

SLAM is based on the [Spring Boot](http://projects.spring.io/spring-boot/) so in order to run the application simply call:

```
mvn spring-boot:run
```

Appliaction should be available under following link [https://localhost:8443](https://localhost:8443)

> In order to run SLAM there is a need for a running instances of MySQL and MongoDB **this documentation should be improved at this place**

If you want SLAM to be running in development mode simply activate Spring Boot _development_ profile.

> **Important notice** - in the current state the application should be started in development mode in order to be running, due to incomplete AII integration.

Development profile run:

```
mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=development"
```

### Running SLAM from Docker container

Docker repository link: [https://hub.docker.com/r/indigodatacloud/slam/](https://hub.docker.com/r/indigodatacloud/slam/)

#### Dependencies

##### CMDB

SLAM takes configuration data from INDIGO-CMDB \([https://github.com/indigo-dc/cmdb\](https://github.com/indigo-dc/cmdb\)\), such us: providers, services and users responsible for specific scope.

You need to set CMDB\_URL variable in order to find point CMDB instance.

##### IAM

INDIGO-IAM \([https://github.com/indigo-dc/iam\](https://github.com/indigo-dc/iam\)\) is a source of authentication and the only method to log in to the service. Authorisation is done based on e-mail attribute taken from INDIGO-IAM and configuration data from CMDB.

You need to set IAM\_URL and IAM\__TOKEN\_URL_ variables in order to find point IAM instance.

##### MYSQL

```
docker pull mysql/mysql-server

docker run --name slam-mysql -e MYSQL_DATABASE=slam -e MYSQL_ROOT_PASSWORD=root -d mysql/mysql-server
```

##### MONGODB

```
docker pull mongo

docker run --name slam-mongo -d mongo
```

#### SLAM WEB APP

example of docker command:

```
docker run -d -e PROVIDER_EMAIL='someonewithIAMaccount@domain.com' --link slammongo:mongo --link slammysql:mysql -e IAM_TOKEN_URL='https://iam-test.indigo-datacloud.eu/token' -e IAM_URL='https://iam-test.indigo-datacloud.eu' -e CMDB_URL='http://cmdb.hostname' -p 8443:8443 --name indigoslam indigodatacloud/slam:latest
```

##### Enviroment variables

* PROVIDER\_EMAIL is used to recognise the person that has a right to act as infrastructure provider \(for all providers\) and have possibility to see SLAs requests, negotiate and accept SLAs

* IAM\__TOKEN_\__URL  -- _URL for IAM service tokens, usually IAM\_URL/token

* IAM\_URL -- main URL for IAM service 

* CERT\_FILE -- certificate file that should be added to keystore \(to be main cert for the site\)

##### Adding cert for secured https access

There are two ways to add certificate:

* _Directly adding the cert_  
  To add a certificate you can add the following options to docker run command:

  * ```
    -e CERT_FILE=/certs/cert.cert -v /path/to/cert.cert:/certs/cert.cert
    ```

    Certificate is imported to java keystore, so it understands all certificate formats that keytool understands:

    _keytool can import X.509 v1, v2, and v3 certificates, and PKCS\#7 formatted certificate chains consisting of certificates of that type. The data to be imported must be provided either in binary encoding format, or in printable encoding format \(also known as Base64 encoding\) as defined by the_[_Internet RFC 1421 standard_](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html#EncodeCertificate)_. In the latter case, the encoding must be bounded at the beginning by a string that starts with "-----BEGIN", and bounded at the end by a string that starts with "-----END".  _\(more info here: [http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html\](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html\)\)

* _Providing custom keystore _  
  There is a standard java keystone used in the SLAM. In case you want to replace it by a keystone customized to your need you can add the following options to docker run command:

* ```
  -e KEYSTORE=/opt/pki/customKeystore.jks -e KEYSTORE_PASSWORD=’keystore_password’ -v /path/to/customKeystore.jks:/opt/pki/customKeystore.jks
  ```



### Upgrade procedure

As long as not stated differently in the release, upgrade procedure 
require only to replace SLAM WEB APP with the new version. 

