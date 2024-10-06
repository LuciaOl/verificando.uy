# verificando.uy

ACA LA CONFIG GENERAL DEL application.properties QUE VA EN src/main/resources


spring.application.name=verificando
pp.base-url=http://localhost:8080
server.port=8080

# Configuraci?n de la base de datos relacional
# configuracion MySql - aca para probar van cambiando por loa local de ustedes
spring.datasource.url=jdbc:mysql://localhost:3306/verificando
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

# Configuraci?n de CouchDB - Base de datos NoSQL
spring.couchbase.connection-string=localhost  # Cambia esto por la URL de tu CouchDB
spring.couchbase.username=tu_usuario          # Cambia por tu nombre de usuario
spring.couchbase.password=tu_contrase?a       # Cambia por tu contrase?a
spring.couchbase.bucket.name=nombre_del_bucket # Cambia por el nombre de tu bucket
spring.couchbase.timeout=10000                # Tiempo de espera en milisegundos
spring.couchbase.ssl.enabled=false             # Habilitar SSL (true/false)


# Configuraci?n de cliente OAuth2
# spring.security.oauth2.client.registration.iduruguay.client-id=tu-client-id
# spring.security.oauth2.client.registration.iduruguay.client-secret=tu-client-secret
# spring.security.oauth2.client.registration.iduruguay.client-authentication-method=basic
# spring.security.oauth2.client.registration.iduruguay.authorization-grant-type=authorization_code
# spring.security.oauth2.client.registration.iduruguay.redirect-uri=${app.base-url}/iduy/user-info
# spring.security.oauth2.client.registration.iduruguay.scope=openid,profile,email

# Configuraci?n del proveedor OAuth2 (ID Uruguay)
spring.security.oauth2.client.provider.iduruguay.authorization-uri=https://auth.iduruguay.gub.uy/oauth2/authorize
spring.security.oauth2.client.provider.iduruguay.token-uri=https://auth.iduruguay.gub.uy/oauth2/token
spring.security.oauth2.client.provider.iduruguay.user-info-uri=https://auth.iduruguay.gub.uy/userinfo
spring.security.oauth2.client.provider.iduruguay.user-name-attribute=sub
spring.security.oauth2.client.provider.iduruguay.jwk-set-uri=https://auth.iduruguay.gub.uy/.well-known/jwks.json


# Mostrar consultas SQL en la consola
spring.jpa.show-sql=true
# Configuraci?n de la ubicaci?n de las entidades JPA
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# Configuracion Firebase
app.firebase.server-key=BKn7Kyh4u2B2DD-3LklZUZ46-Au6M4kvQ5tYPX-fr1iBSfHCBbfBVQgxbBgmaCcinSHI3A-IXR0ezcMV11PwHsM
#Correo para asociar a firebase:
#usuario: notificacionessgoce@gmail.com
#contrase?a: notificacionesSGOCE123
#https://console.firebase.google.com/u/3/?utm_source=welcome&utm_medium=email&utm_campaign=welcome_2021_CTA_A

#expo.notification.url=https://exp.host/--/api/v2/push/send?useFcmV1=true
