# jwt-apigateway-security

## Regist an user

```
curl --location --request POST 'http://localhost:8888/auth/register'             http://localhost:8888/identity-service/auth/register \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D' \
--data-raw '{
    "name":"Basant",
    "password":"Pwd1",
    "email":"basant@gmail.com"
}'

```

## Generate token

```
curl --location --request POST 'http://localhost:9898/auth/token' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D' \
--data-raw '{
    "username":"Basant",
    "password":"Pwd1"
}'
```
## Access Swiggy-app

```
curl --location --request GET 'http://localhost:8888/swiggy/37jbd832' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCYXNhbnQiLCJpYXQiOjE2NzkwNTU4MDIsImV4cCI6MTY3OTA1NzYwMn0.Q0bwS5_16q1Z8K-p_flpmyRoJNFCyOhU2AMKSNYh66o' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D'
```

## Access Restaurant-service

```
curl --location --request GET 'http://localhost:8888/restaurant/orders/status/37jbd832' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCYXNhbnQiLCJpYXQiOjE2NzkwNTU1MDcsImV4cCI6MTY3OTA1NzMwN30.9nNAW1rx8RoTIrhn5Abtzg7RplvT9_d-U5EOwUcJZq8' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D'
```



http://localhost:8888/doctor-ms/api/doctors  
http://localhost:8888/patient-ms/api/patients
http://localhost:8888/appointment-ms/api/appointments
http://localhost:8888/medicalrecord-ms/api/medical-records
http://localhost:9898/auth/user
http://localhost:9898/auth/token
http://localhost:9898/swagger-ui/index.html#/auth-controller/getToken

INSERT INTO `menuitem` (`icon`, `path`, `title`) VALUES
('flaticon-menu-1', '/dashboard', 'dashboard'),
('flaticon-calendar', '/appointments', 'appointments'),
('flaticon-user-1', '/patients', 'patients'),
('flaticon-user', '/doctors', 'doctors'),
('flaticon-envelope', '/messages', 'messages'),
('flaticon-setting', '/settings', 'settings');

INSERT INTO `role` (`name`) VALUES
('admin'),
('patient'),
('doctor');


UPDATE menuitem
SET `display_order` = id
WHERE `display_order` IS NULL;




























