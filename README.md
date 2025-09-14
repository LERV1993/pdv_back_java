
# Backend JAVA - Programación de Vanguardia 

Backend con JAVA 17 para conectarse a una base de datos y consumir su información desde distintos endpoints.


## Configuración

El la raíz del proyecto se debe crear un archivo .env con la siguiente configuración:

- URL=jdbc:mysql://localhost:3306/table_example
- USERNAME=root
- PASSWORD=

Para el ejemplo se utiliza la base de datos por defecto que crea [Xaamp/Laamp](https://www.apachefriends.org/es/index.html) pero se puedo usar cualquier base SQL.



## Installation

```bash
git clone <URL_REPO>

cd <Carpeta con proyecto>
```

*Completar archivo .env con los parámetros datos detallados anteriormente.*

Correr proyecto:
```bash
mvn clean package && mvn spring-boot:run
```


## Authors

- [@Andres-ann](https://github.com/Andres-ann)
- [@LERV1993](https://github.com/LERV1993)
- [@omarlosinno](https://github.com/omarlosinno)
- [@RauloGti](https://github.com/RauloGti)
- [@ynunezgon](https://github.com/ynunezgon)