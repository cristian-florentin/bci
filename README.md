#BCI Login Project

The objective of this application is to sign-up and login users.

#Build and Working Instructions

- The application works with H2 database, and it creates all the tables automatically, so is no needed to run any sql file before rune it.
- It needs Java 8
- For sign-up users, needs the following payload, fields "email" and "passwords" are required.
> POST /sign-up
> >     { "name": String, 
> >        "email": String, 
> >        "password": String, 
> >        "phones": 
> >            [ { "number": long, 
> >                "citycode": int, 
> >                "contrycode": String }
> >              ] }
- Password must have 1 uppercase letter, 2 numbers, and lenght from 8 to 12 letters and numbers.
- Sign-up request will generate a Token for login to the platform.
- For Login, needs the following payload with the token generated before.
> POST /log-in
> > {"token" : string }
- this will reload a new token and retrieve user data.

