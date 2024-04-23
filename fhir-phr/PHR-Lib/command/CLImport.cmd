ECHO OFF
SET PATH="C:\Program Files\Java\jdk1.8.0_102\bin"

rem setlocal ENABLEDELAYEDEXPANSION

keytool -v -alias PHR_BU_CLIENT_CA -importcert -file ./cacert.pem -storepass phr_client_store -keystore ./truststore.key
keytool -v -alias PHR_BU_SERVER_CA -importcert -file ./gsdomainvalsha2g2.cer -storepass phr_client_store -keystore ./truststore.key

keytool -v -importkeystore -srcstoretype pkcs12 -srckeystore ./phr.pfx -srcstorepass phr-backu_pcl12 --storepass phr-backu_pcl12 -deststoretype pkcs12 -destkeystore ./keystore.key


REM -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Djavax.net.ssl.trustStore=D:/opt/phr/cert/truststore.key
