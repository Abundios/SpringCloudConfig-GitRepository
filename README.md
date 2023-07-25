# SpringCloudConfig-GitRepository

En los próximos días, subiré la aplicación de inversión que he estado desarrollando estos últimos días.

Es una aplicación sencilla que sirve para simular el contrato de diferentes productos de inversión que escojaamos y comprobar durante el tiempo si somos tan buenos como creemos cuando invertimos.

La aplicación está basada en microservicios desarrollados con Java Spring Boot.
Cuenta de un servidor de configuración, un servidor de registro y descubrimiento de servicios (Eureka), un servicio de enrutamion de servicios (Zuul) y los propio servicios que proporcionan la lógica a la aplicación, básicamnte un servicio Rest Api Full, la aplicación visual que los consume y un servicio adicional para refrescar diariamente las cotizaciones de los productos de inversión.
La autenticación de la aplicación ataca un servidor de LDAP externo y la información se guarda en una instancia de Postgres externa.

He creado un contenedor para cada componente, desplegado todos ellos con Docker Compose. Próximamente los desplegaré en minikube, como paso previo a desplegarlos en GKE.
