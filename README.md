# Perritos felices

Bienvenid@ al repositorio de perritos felices, este proyecto nació debido a la invitación al Hackathon 
orquestado por [AristiDevs](https://www.youtube.com/watch?v=SI7vGES9fPA), el cual tiene como fecha limite de entrega el 14 de noviembre del 2021 y cuya 
premisa es ayudar a resolver alguna problemática (cual sea que se te haya ocurrido).

##¿Qué es?
Perritos felices es una aplicación móvil que busca satisfacer las necesidades fisiológicas y de esparcimiento 
de los perros que viven con nosotros y que por diferentes cuestiones no somos capaces de brindarles. 


La mayoría de nuestros amigos caninos no tienen suficiente con un paseo alrededor de la manzana, necesitan correr 
libremente y quemar toda la energía. Para ello en necesario un paseo que dure entre 30 y 60 minutos, dependiendo de 
la raza y la edad del perro.


Muchas veces no tenemos el tiempo necesario para brindarles el paseo que se merecen por todo el amor incondicional 
que nos dan es ahí donde Perritos felices busca ayudar, brindando una plataforma en la cual una persona puede registrarse 
ya sea como dueño de un perrito o como paseador dependiendo sus necesidades.


##¿Qué puede hacer un dueño?
Cualquier persona que se registre como dueño de un perrito podrá dar de alta a sus perritos en el sistema, así como publicar 
un paseo para un día y una hora dada esperando la respuesta de un paseador.

##¿Qué puede hacer un paseador?
Como paseador estas al servicio de los perritos que necesitan el paseo, una vez un usuario dueño publica un nuevo paseo, este 
aparecerá en tu sección de “Buscar paseos”, podrás aceptar dicho paseo y entonces una vez llegue el día recogerás al perrito y 
le darás el paseo que se merece, si señor, nada como ganarse la vida paseando perritos.

##Datos técnicos
La aplicación esta divida por features (login, owner y walker) el login fue implementado bajo la arquitectura MVI, el resto de 
features fueron implementados con MVVM. Como backend se hizo uso únicamente de Firebase (Firebase auth, firestore y Storage).
