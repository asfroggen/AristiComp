# Perritos felices

Bienvenid@ al repositorio de perritos felices, este proyecto nació debido a la invitación al Hackathon 
orquestado por [AristiDevs](https://www.youtube.com/watch?v=SI7vGES9fPA), el cual tiene como fecha limite de entrega el 14 de noviembre del 2021 y cuya 
premisa es ayudar a resolver alguna problemática (cual sea que se te haya ocurrido).


## ¿Qué es?


Perritos felices es una aplicación móvil que busca satisfacer las necesidades fisiológicas y de esparcimiento 
de los perros que viven con nosotros y que por diferentes cuestiones no somos capaces de brindarles. 


La mayoría de nuestros amigos caninos no tienen suficiente con un paseo alrededor de la manzana, necesitan correr 
libremente y quemar toda la energía. Para ello en necesario un paseo que dure entre 30 y 60 minutos, dependiendo de 
la raza y la edad del perro.


Muchas veces no tenemos el tiempo necesario para brindarles el paseo que se merecen por todo el amor incondicional 
que nos dan es ahí donde Perritos felices busca ayudar, brindando una plataforma en la cual una persona puede registrarse 
ya sea como dueño de un perrito o como paseador dependiendo sus necesidades.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501388.png?alt=media&token=cba464c5-79ea-4926-afbf-8eb202c601b0)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501392.png?alt=media&token=1c99a315-6042-4846-8af1-767c618cd38c)
> Tipos de usuario


## ¿Qué puede hacer un dueño?


Cualquier persona que se registre como dueño de un perrito podrá dar de alta a sus perritos en el sistema, así como publicar 
un paseo para un día y una hora dada esperando la respuesta de un paseador.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501420.png?alt=media&token=b339686e-61f4-41a3-84ad-1349a43d13e3)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501437.png?alt=media&token=91fca253-a2af-4733-a497-bbd831e6c6ca)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501447.png?alt=media&token=4134e22a-80ad-4255-8a4f-5895e5f98bb0)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501455.png?alt=media&token=49241ddf-bb9d-4e92-baf1-b49673c3af47)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501466.png?alt=media&token=00fb86c3-8e31-4e33-93bf-2c9f598d23c0)
> Funciones que pueden realizar los dueños de un perrito


## ¿Qué puede hacer un paseador?


Como paseador estas al servicio de los perritos que necesitan el paseo, una vez un usuario dueño publica un nuevo paseo, este 
aparecerá en tu sección de “Buscar paseos”, podrás aceptar dicho paseo y entonces una vez llegue el día recogerás al perrito y 
le darás el paseo que se merece, si señor, nada como ganarse la vida paseando perritos.

![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501488.png?alt=media&token=0c8313f8-b39c-41c9-895f-04d3fa54e47f)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636501492.png?alt=media&token=7923ef30-3496-4be2-92bf-4b3b088b8073)
![](https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/Screenshot_1636502070.png?alt=media&token=1dc40b60-661b-4128-a0b6-0cf999958008)
> Funciones que pueden realizar los peseadores de perritos

## Datos técnicos


La aplicación esta divida por features (login, owner y walker) el login fue implementado bajo la arquitectura MVI, el resto de 
features fueron implementados con MVVM. Como backend se hizo uso únicamente de Firebase (Firebase auth, firestore y Storage).
