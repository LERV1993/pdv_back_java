
# Backend JAVA - Programación de Vanguardia 

Backend con JAVA 17 para conectarse a una base de datos y consumir su información desde distintos endpoints.


## Configuración

El la raíz del proyecto se debe crear un archivo .env con la siguiente configuración:

- URL=jdbc:mysql://localhost:3306/table_example
- USERNAME=root
- PASSWORD=

Para el ejemplo se utiliza la base de datos por defecto que crea [Xampp/Lampp](https://www.apachefriends.org/es/index.html) pero se puedo usar cualquier base SQL.



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

# TestController

Controlador de ejemplo para pruebas rápidas que expone endpoints `GET` y `POST` a través del servicio `TestService`. Retorna información sobre el método HTTP, fecha/hora de la solicitud, estado y un mensaje de saludo.

---

## Endpoints

### GET `/test`

- **Descripción:**  
  Retorna información sobre la solicitud GET.

- **Request:**
  ```http
  GET /test

- **Response:**
```json
{
  "method": "GET",
  "time": "2025-09-14T18:00:00",
  "status": "ok",
  "message": "hello!"
}
```

## Authors

- [@Andres-ann](https://github.com/Andres-ann)
- [@LERV1993](https://github.com/LERV1993)
- [@omarlosinno](https://github.com/omarlosinno)
- [@RauloGti](https://github.com/RauloGti)
- [@ynunezgon](https://github.com/ynunezgon)