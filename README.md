# SpringCloudConfig-GitRepository

En los próximos días, subiré la aplicación de inversión que he estado desarrollando.

Es una aplicación sencilla, que sirve para simular el contrato de diferentes productos de inversión a escoger, y comprobar durante el tiempo, si somos tan buenos como creemos cuando invertimos.

La aplicación está basada en microservicios desarrollados con Java Spring Boot.
Cuenta de un servidor de configuración, un servidor de registro y descubrimiento de servicios (Eureka) y los propios servicios, que se registrarán en el servidor Eureka y se servirán de Feign para descubrir el resto de servicios.
Los microservicios proporcionan la lógica a la aplicación, que consisitrán en una Api Rest Full, la aplicación visual que los consume y un servicio adicional para refrescar diariamente las cotizaciones de los productos de inversión.
La autenticación de la aplicación y los servicios Rest Api atacan un servidor de LDAP externo y la información se guarda en una instancia de Postgres externa.
Swagger está disponible para progar y obtener información de los servicios Rest.

He creado un contenedor para cada componente, desplegando todos ellos con Docker Compose. Próximamente los desplegaré en minikube, como paso previo a desplegarlos en GKE.
