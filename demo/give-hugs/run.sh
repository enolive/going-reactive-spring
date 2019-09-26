#!/bin/sh

cd backend
mvn spring-boot:run &

cd ..

cd frontend
ng serve -o
