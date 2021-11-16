# Perritos felices

Bienvenid@ al repositorio de perritos felices, este proyecto nació debido a la invitación al Hackathon 
orquestado por [AristiDevs](https://www.youtube.com/watch?v=SI7vGES9fPA), el cual tiene como fecha limite de entrega el 14 de noviembre del 2021 y cuya 
premisa es ayudar a resolver cualquier problematica que se te ocurra y que mejor que ayudar a nuestros amigos perrunos.


## ¿Qué es?


Perritos felices es una aplicación móvil que busca satisfacer las necesidades fisiológicas y de esparcimiento 
de los perros que viven con nosotros y que por diferentes cuestiones no somos capaces de brindarles. 


La mayoría de nuestros amigos caninos no tienen suficiente con un paseo alrededor de la manzana, necesitan correr 
libremente y quemar toda la energía. Para ello en necesario un paseo que dure entre 30 y 60 minutos, dependiendo de 
la raza y la edad del perro.


Muchas veces no tenemos el tiempo necesario para brindarles el paseo que se merecen por todo el amor incondicional 
que nos dan es ahí donde Perritos felices busca ayudar, brindando una plataforma en la cual una persona puede registrarse 
ya sea como dueño de un perrito o como paseador dependiendo sus necesidades.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/login.png?alt=media&token=7434752d-9352-41c3-baa2-a12c03f4fc75)
> Tipos de usuario


## ¿Qué puede hacer un dueño?


Cualquier persona que se registre como dueño de un perrito podrá dar de alta a sus perritos en el sistema, así como publicar 
un paseo para un día y una hora dada esperando la respuesta de un paseador.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/owner.png?alt=media&token=ea33d1a8-badf-4372-8630-b57251905fa0)
> Funciones que pueden realizar los dueños de un perrito


## ¿Qué puede hacer un paseador?


Como paseador estas al servicio de los perritos que necesitan el paseo, una vez un usuario dueño publica un nuevo paseo, este 
aparecerá en tu sección de “Buscar paseos” (increíblemente todo en tiempo real OO:), podrás aceptar dicho paseo y entonces una vez llegue el día recogerás al perrito y 
le darás el paseo que se merece, si señor, nada como ganarse la vida paseando perritos.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/walker.png?alt=media&token=2ca382ca-cbb5-4b44-92dd-92f5e7171041)
> Funciones que pueden realizar los peseadores de perritos

## Alcances


En el barrio donde vivo hay un chico que ofrece sus servicios como paseador de perro y de él fue que tome la inspiración para esta aplicación. Como pasos 
futuros en caso de querer continuar con la aplicación serian:

- Implementar un sistema de calificación de paseadores y perros (así es, luego hay perros bien tremendos XD)
- Implementar un sistema de pagos ya sea in-app o pago en efectivo
- Implementar un sistema de gestión de ubicación para tener un control del recorrido que hace el perrito (saber que ruta tomó el paseador)


## Datos técnicos


La aplicación esta divida por features (login, owner y walker) el login fue implementado bajo la arquitectura MVI, el resto de 
features fueron implementados con MVVM. Como backend se hizo uso únicamente de Firebase (Firebase auth, firestore y Storage).


Muchas gracias por tomarse la molestia de leer hasta aqui. 


Un saludo.


## Descarga el APK 


https://drive.google.com/file/d/1ZjwE0W5c_MlgzpMUvqsh71eY8EXFG3nf/view?usp=drivesdk
