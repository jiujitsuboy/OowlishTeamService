# Oowlish TEAM API

### Reference Documentation

This API enhanced these following urls 
[Teams](https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams) and [Users](https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users) by adding the concept of team role to users of a team.
The following are the functionalities offered by this API.

* Retrieve all the **Users** of the API *(passthrough)*.
* Retrieve an specific **User** and see all his details *(passthrough)*.
* Retrieve all the **Teams** of the API *(passthrough)*.
* Retrieve an specific **Team** and see all his details ***(Enhanced endpoint. Adds the role of every team member)***.
* Create **Team Roles**.
* Assign **Team Roles** to a **Users** on a specific **Team**.
* Search **Role's** by **Membership**.
* Search **Membership's** by **Role**.

### API endpoints

The following list depict the available endpoints of the API:

1. Authentication

   * [User Sign Up (POST)](http://localhost:8082/api/v1/auth/users)
                
     Request:

       ```
        curl --location --request POST 'http://localhost:8082/api/v1/auth/users' \
             --header 'Content-Type: application/json' \
             --header 'Accept: application/json' \
             --header 'If-None-Match: 098e97de3b61db55286f5f2812785116f' \
             --data-raw '{
                            "username": "scott2",
                            "password": "tiger",
                            "firstName": "Bruce",
                            "lastName": "Scott2",
                            "email": "bruce2@scott.db",
                            "role": "ROLE_ADMIN"
                          }'
       ```
     Response:

       ```
         {
             "links": [
                 {
                    "rel": "user-signin",
                    "href": "http://localhost:8082/api/v1/auth/token"
                 }
             ],
             "id": "443fc613-430e-42eb-8ba8-6709a803cb45",
             "username": "scott2",
             "firstName": "Bruce",
             "lastName": "Scott2",
             "email": "bruce2@scott.db",
             "password": "Ciphered...",
             "role": "ROLE_ADMIN"
         }
       ```
   * [User Sign In (POST)](http://localhost:8082/api/v1/auth/token)

     Request:

          curl --location --request POST 'http://localhost:8082/api/v1/auth/token' \
                --header 'Content-Type: application/json' \
                --header 'Accept: application/json' \
                --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                --data-raw '{
                               "username": "scott2",
                               "password": "tiger"
                            }'

     Response:

           {
             "links": [
                        {
                           "rel": "user-team",
                           "href": "http://localhost:8082/api/v1/team/443fc613-430e-42eb-8ba8-6709a803cb45"
                        }
                      ],
             "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d",
             "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJEcmVhbSBUZWFtIEFQSSIsImV4cCI6MTYzOTc3ODM4NywiaWF0IjoxNjM5Nzc3NDg3fQ.G-bpC59K4nPvZ7xYxTJ67JCUm41IZUE70AviRtk2RUBb2E4X5y0qjr08LPTJpkl-5c7CqkBC7XkyEtLPITYfFd-vs0AIB1JkfuLIykvte1QZ6jTjfdgteqX7QXOJfd0n5xjoXcWT0Z_oR84t9OSSLpneNpROKrOvROyxf6mrNK7M8FnNnzTDqk-fDHBbNrCZKG1GX2PawGocnOUw_qPd2Y08MVoaYzHj61MAKsqTn_9rEViY0T9mRLCgTaekHewN9dAdIGsg7UR0s78nY7Hnd8d3bcPeLLeR1o3tZwyXCqBML0hPQ9NyvmuPmepKbgtEmJOMNhLKT4Fk9yaU5IXnlb7WWkmboFsIm-gIOWfYSgW2Qv1RjVwx-aCZ5sxFACcIrqIBVgLiYMrulzKzVxZFyMSLDW3SrJKuT8DYABqiY6kuoGopx_vjL_3J-bEdhoZrTEJ6JweEIt1LQiYD0deQpL61EsolkYriJgKpxn5VNhFexn6IacHfLZP11jc0e95Wu-Rlc1cbt4tCUMepY0SibHHkHL6B-5AzCeQrWZkIxh1HIhO23bZ72_lZDHzeteHotXFqCaXUGqo5wS4M7uBNpUgp1JL8IW68SmbTXlnhWeyQI3Ybdh_f3QDqZGyizMm3AIy42tPRgBHsA3Swpcrlb50DTg8QEajlW18MbAUFs28",
             "username": "scott2",
             "userId": "443fc613-430e-42eb-8ba8-6709a803cb45"
        }       

   * [User Refresh Tokens (POST)](http://localhost:8082/api/v1/auth/token/refresh)

     Request:
   
           curl --location --request POST 'http://localhost:8082/api/v1/auth/token/refresh' \
                --header 'Content-Type: application/json' \
                --header 'Accept: application/json' \
                --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                --data-raw '{
                               "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d"
                           }'
   
     Response:
   
           {
             "links": [],
             "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d",
             "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJEcmVhbSBUZWFtIEFQSSIsImV4cCI6MTYzOTc3ODY4NywiaWF0IjoxNjM5Nzc3Nzg3fQ.Jud34UpYN1d1L_NSVGvgmLraXmiGf5uTjQI5tIUp9PjeP9UEc70SQcy-WlDX1-hO0P8bxyfuOy48eH0D169CIIEGs4I87qgR8HugQFvzuw4vaZiiDH4JX-By-BuSdm6-NDg5XjS3EHBtGnE3IID7qgAA0DHaPp-cZZ82bsyCGbDm8OhwA6uMPwZSG-oariWULtBGds44iTpXivKC8dutvwVvoKcfVP1FTdB9YHwfULabqZ-PmYkoI8cI_Jd6p97VdzBfin2bg-56s0UxbPeGs5LcYnFgNp-gWOYlNqaeF9QMlsKinnm0wbhWRWgu6EAW-UdwcKeLGN4r9skZ01BtneQaeDNWOGak9119gPU5xHlx8Vu1Cz1VCGzyMdvmcU4SKAq7JYCthUBkTeXSGPO3IzRdgGb_86o3XptObZvDssbeSV-cAhjjz9UiuIVbrkeEZD0frd6GfyRig3NMUdlTQOu9Uj0Pyh85a1cqX_nh-dKV0EFOyXrhIlAr9AHDwAyx6lqfD3tl8bjzgID5bSUsA0ZTmZxWgpiz4BnLF3otBGkHe62hCHZltD8_3G8GxRkC88mwYgPGehlcZH91LZs6lfwTLqn0gejiaAdcJTde5FFVXvJkMd6H07ELwW01w27XyRGRB4wUTdu_2uCzC27rUltu0PYYk3MJs-J_eDAEjpY",
             "username": "scott2",
             "userId": "443fc613-430e-42eb-8ba8-6709a803cb45"
           }
   
   * [User Sign Out (DELETE)](http://localhost:8082/api/v1/auth/token)

     Request:

           curl --location --request DELETE 'http://localhost:8082/api/v1/auth/token' \
                --header 'Content-Type: application/json' \
                --header 'Accept: application/json' \
                --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                --data-raw '{
                                "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d"
                           }'
       
     Response:
       ```
          No content
       ```
2. Team
   * [Get all teams (GET)](http://localhost:8082/api/v1/teams)

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/teams' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6IkRyZWFtIFRlYW0gQVBJIiwiZXhwIjoxNjY5NDM4Mjc0LCJpYXQiOjE2Njk0MzczNzR9.A3aHzqaw9-B4zRE1qRc1BlFKjxUf84CuCy9V4EAAMpi1ld5J-9Q49GTzOOdm6uueLF8XjPezDculzO_mNMAQ4lFjZ2qXMAXNqn-QXE4NfGrNAuTRP_ZFWLaYnt2dv93N9lXps_THQfBCiuwHvFIyYPx-LwB1v6Yl6gbH1JTAZMdoMD5xbzqGINvH3mblGK0OxS6OF8vqSp0Rq660e5KXTWMC--BzQIcRKdk3VLbNsTvR2k6C4LSx7iaG5W96B552y5hgZb0nidxV6b_dUteRmzFFgMq8AtHX9h5QhgKE2OWXTAQlqbJWYfEjCSY1RuFxpDmpdIf9-caOgQzyqH8aDwyz2SiqgkUGuVnaxR8hQAONvKjU43JnjwbUSHqG-OulJ83yzH7rvOyKKELcMjMOXd7LoSTJk5QakE29IbB2Ykd68ZdAyea0sWWuYck1rhAyZby5V0m5EWuM5k0Ch1AyQAyIJN83GJAPdtHKe79F7wEvyJF9Xc0PlIo30TP8nd-VIDsviUhK9mRklNGEMCY0CP_P9HNSp5zeWrhRNjoy8UOyS-q-fb1hBvy9cs5V7N0DYrz53ox3WL2w8IAfN8JUHtl7tIF_mXg4-PGlf-v_Ig2elfYg4KwSRGSsCwU3O-CNYM6I3Z0X2J_IxHOW9SlHM9a6LnpXxDTScxiJRUdbv5k'
       ```
     Response:

       ```
         [
           {
             "name": "Ordinary Coral Lynx",
             "id": "7676a4bf-adfe-415c-941b-1739af07039b"
           },
           {
             "name": "Weekly Peach Wildebeest",
             "id": "5071b4fc-43f2-47a2-8403-e934dc270606"
           },
           {
             "name": "Surrounding Gold Pheasant",
             "id": "7cf0d32d-036f-40b6-86ea-2473d4ccaecd"
           },
          ...
         ]
       ```
   * [Get team (GET)](http://localhost:8082/api/v1/teams/{{teamId}})

     Request:

       ```
        curl --location -g --request GET '{{urk}}/teams/7676a4bf-adfe-415c-941b-1739af07039b' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6IkRyZWFtIFRlYW0gQVBJIiwiZXhwIjoxNjY5NDM4Mjc0LCJpYXQiOjE2Njk0MzczNzR9.A3aHzqaw9-B4zRE1qRc1BlFKjxUf84CuCy9V4EAAMpi1ld5J-9Q49GTzOOdm6uueLF8XjPezDculzO_mNMAQ4lFjZ2qXMAXNqn-QXE4NfGrNAuTRP_ZFWLaYnt2dv93N9lXps_THQfBCiuwHvFIyYPx-LwB1v6Yl6gbH1JTAZMdoMD5xbzqGINvH3mblGK0OxS6OF8vqSp0Rq660e5KXTWMC--BzQIcRKdk3VLbNsTvR2k6C4LSx7iaG5W96B552y5hgZb0nidxV6b_dUteRmzFFgMq8AtHX9h5QhgKE2OWXTAQlqbJWYfEjCSY1RuFxpDmpdIf9-caOgQzyqH8aDwyz2SiqgkUGuVnaxR8hQAONvKjU43JnjwbUSHqG-OulJ83yzH7rvOyKKELcMjMOXd7LoSTJk5QakE29IbB2Ykd68ZdAyea0sWWuYck1rhAyZby5V0m5EWuM5k0Ch1AyQAyIJN83GJAPdtHKe79F7wEvyJF9Xc0PlIo30TP8nd-VIDsviUhK9mRklNGEMCY0CP_P9HNSp5zeWrhRNjoy8UOyS-q-fb1hBvy9cs5V7N0DYrz53ox3WL2w8IAfN8JUHtl7tIF_mXg4-PGlf-v_Ig2elfYg4KwSRGSsCwU3O-CNYM6I3Z0X2J_IxHOW9SlHM9a6LnpXxDTScxiJRUdbv5k'
       ```
     Response:

       ```
         {
            "id": "7676a4bf-adfe-415c-941b-1739af07039b",
            "name": "Ordinary Coral Lynx",
            "teamLeadId": "b12fa35a-9c4c-4bf9-8f32-27cf03a1f190",
            "members": [
              {
                "id": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
                "role": "Developer",
                "links": [
                  {
                     "rel": "self",
                     "href": "http://localhost:8082/api/v1/users/371d2ee8-cdf4-48cf-9ddb-04798b79ad9e"
                  }
                ]
             },
             {
                "id": "54383a18-425c-4f50-9424-1c4c27e776dd",
                "role": "Developer",
                "links": [
                  {
                     "rel": "self",
                     "href": "http://localhost:8082/api/v1/users/54383a18-425c-4f50-9424-1c4c27e776dd"
                  }
                ]
             },
            ...
           "links": []
         }
       ```
   
3. User
   * [Get user (GET)](http://localhost:8082/api/v1/users/{{userId}})

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/users/fd282131-d8aa-4819-b0c8-d9e0bfb1b75c' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODE2MzksImlhdCI6MTY2OTQ4MDczOX0.czw-9Mk4Ghd8LsYbA5gy8x-CRKM4GfbnqJyJjH4LI_4ZqAiGtRnDl20QdPJrmwPPZSWls_OkWlenkfxbuZszCKnCfWn5k0wWjO7kthgPlSMm9YrPct28Gf28W6v0L6iwmPpa24XaU2ywozo_2r65V9VucbCWAEAzvOyWqPneEuPxilCNCAQi5-4BRF2EXgKkGXWoNwFoFMgmZbUtX4pULo1K19pVOWNjaF2ljt3LPGdbpWbkDuG8MOEXLvKjDIUTqm9_eQNGCkUcU362TF5HEyJBCuG0gt4q-sqDGMs2Nt3-Uc1Ue_m3jxaYURUjJXVHlvQi6a6tEQN6xjx0ozMaeypEbGwiIMf04So0mP67U2LKLUOT_IvtX4JVMleQx1i50ZRV-bU572mpPuNh6vs20aHAlCHJLZc1_Lq48y9mUXQtbrmp31sDmtXr-CjMSKH_WsLVLg28eJFD4Di8IKcuiw21UpfNyiNTnSVlfIyRg1tUjv06mbO_9eLS7zxJ98A6dTC9XpW582Ruu5r9N6EwRQwxe4Npw4P93eDQE3W97-YnybRjSjXKN6DZITMw2I-hUFlJVcSRgldb4d2oJsTB_ZJokwRXUcvUeMT4CFY_Z1NSnPZxJssOylF5I4EJeAXC2JsnaVe2c1h08Q-tf_KTMwB7h3hE2rqCuJ4HOUdgrJU'
       ```
     Response:

       ```
         {
           "firstName": "Gianni",
           "lastName": "Wehner",
           "avatarUrl": "https://cdn.fakercloud.com/avatars/rude_128.jpg",
           "displayName": "gianniWehner",
           "location": "Brakusstad",
           "id": "fd282131-d8aa-4819-b0c8-d9e0bfb1b75c"
         }
       ```
   * [Get all users (GET)](http://localhost:8082/api/v1/users/)

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/users' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODE2MzksImlhdCI6MTY2OTQ4MDczOX0.czw-9Mk4Ghd8LsYbA5gy8x-CRKM4GfbnqJyJjH4LI_4ZqAiGtRnDl20QdPJrmwPPZSWls_OkWlenkfxbuZszCKnCfWn5k0wWjO7kthgPlSMm9YrPct28Gf28W6v0L6iwmPpa24XaU2ywozo_2r65V9VucbCWAEAzvOyWqPneEuPxilCNCAQi5-4BRF2EXgKkGXWoNwFoFMgmZbUtX4pULo1K19pVOWNjaF2ljt3LPGdbpWbkDuG8MOEXLvKjDIUTqm9_eQNGCkUcU362TF5HEyJBCuG0gt4q-sqDGMs2Nt3-Uc1Ue_m3jxaYURUjJXVHlvQi6a6tEQN6xjx0ozMaeypEbGwiIMf04So0mP67U2LKLUOT_IvtX4JVMleQx1i50ZRV-bU572mpPuNh6vs20aHAlCHJLZc1_Lq48y9mUXQtbrmp31sDmtXr-CjMSKH_WsLVLg28eJFD4Di8IKcuiw21UpfNyiNTnSVlfIyRg1tUjv06mbO_9eLS7zxJ98A6dTC9XpW582Ruu5r9N6EwRQwxe4Npw4P93eDQE3W97-YnybRjSjXKN6DZITMw2I-hUFlJVcSRgldb4d2oJsTB_ZJokwRXUcvUeMT4CFY_Z1NSnPZxJssOylF5I4EJeAXC2JsnaVe2c1h08Q-tf_KTMwB7h3hE2rqCuJ4HOUdgrJU'
       ```
     Response:

       ```
         [
           {
             "displayName": "gianniWehner",
             "id": "fd282131-d8aa-4819-b0c8-d9e0bfb1b75c"
           },
           {
             "displayName": "jarenKerluke",
             "id": "fa1529de-5f20-49a7-ad25-a494008dd322"
           },
           {
             "displayName": "marionKertzmann",
             "id": "aa569071-6ade-4ff6-b567-6466fcbae74a"
           },
           ...
         ]
       ```
4. Roles
   * [Get role (GET)](http://localhost:8082/api/v1/role/{{roleId}})

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/role/1' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODE2MzksImlhdCI6MTY2OTQ4MDczOX0.czw-9Mk4Ghd8LsYbA5gy8x-CRKM4GfbnqJyJjH4LI_4ZqAiGtRnDl20QdPJrmwPPZSWls_OkWlenkfxbuZszCKnCfWn5k0wWjO7kthgPlSMm9YrPct28Gf28W6v0L6iwmPpa24XaU2ywozo_2r65V9VucbCWAEAzvOyWqPneEuPxilCNCAQi5-4BRF2EXgKkGXWoNwFoFMgmZbUtX4pULo1K19pVOWNjaF2ljt3LPGdbpWbkDuG8MOEXLvKjDIUTqm9_eQNGCkUcU362TF5HEyJBCuG0gt4q-sqDGMs2Nt3-Uc1Ue_m3jxaYURUjJXVHlvQi6a6tEQN6xjx0ozMaeypEbGwiIMf04So0mP67U2LKLUOT_IvtX4JVMleQx1i50ZRV-bU572mpPuNh6vs20aHAlCHJLZc1_Lq48y9mUXQtbrmp31sDmtXr-CjMSKH_WsLVLg28eJFD4Di8IKcuiw21UpfNyiNTnSVlfIyRg1tUjv06mbO_9eLS7zxJ98A6dTC9XpW582Ruu5r9N6EwRQwxe4Npw4P93eDQE3W97-YnybRjSjXKN6DZITMw2I-hUFlJVcSRgldb4d2oJsTB_ZJokwRXUcvUeMT4CFY_Z1NSnPZxJssOylF5I4EJeAXC2JsnaVe2c1h08Q-tf_KTMwB7h3hE2rqCuJ4HOUdgrJU'
       ```
     Response:

       ```
         {
           "id": 1,
           "name": "Developer",
           "links": [
              {
                "rel": "role",
                "href": "http://localhost:8082/api/v1/role/1"
              }
            ]
         }
       ```
   * [Get all roles  (GET)](http://localhost:8082/api/v1/role/)

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/role' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODE2MzksImlhdCI6MTY2OTQ4MDczOX0.czw-9Mk4Ghd8LsYbA5gy8x-CRKM4GfbnqJyJjH4LI_4ZqAiGtRnDl20QdPJrmwPPZSWls_OkWlenkfxbuZszCKnCfWn5k0wWjO7kthgPlSMm9YrPct28Gf28W6v0L6iwmPpa24XaU2ywozo_2r65V9VucbCWAEAzvOyWqPneEuPxilCNCAQi5-4BRF2EXgKkGXWoNwFoFMgmZbUtX4pULo1K19pVOWNjaF2ljt3LPGdbpWbkDuG8MOEXLvKjDIUTqm9_eQNGCkUcU362TF5HEyJBCuG0gt4q-sqDGMs2Nt3-Uc1Ue_m3jxaYURUjJXVHlvQi6a6tEQN6xjx0ozMaeypEbGwiIMf04So0mP67U2LKLUOT_IvtX4JVMleQx1i50ZRV-bU572mpPuNh6vs20aHAlCHJLZc1_Lq48y9mUXQtbrmp31sDmtXr-CjMSKH_WsLVLg28eJFD4Di8IKcuiw21UpfNyiNTnSVlfIyRg1tUjv06mbO_9eLS7zxJ98A6dTC9XpW582Ruu5r9N6EwRQwxe4Npw4P93eDQE3W97-YnybRjSjXKN6DZITMw2I-hUFlJVcSRgldb4d2oJsTB_ZJokwRXUcvUeMT4CFY_Z1NSnPZxJssOylF5I4EJeAXC2JsnaVe2c1h08Q-tf_KTMwB7h3hE2rqCuJ4HOUdgrJU'
       ```
     Response:

       ```
         [
           {
              "id": 1,
              "name": "Developer",
              "links": [
                {
                  "rel": "role",
                  "href": "http://localhost:8082/api/v1/role/1"
                }
              ]
           },
           {
              "id": 2,
              "name": "Product Owner",
              "links": [
                {
                  "rel": "role",
                  "href": "http://localhost:8082/api/v1/role/2"
                }
              ]
           },
           {
              "id": 3,
              "name": "Tester",
              "links": [
                {
                  "rel": "role",
                  "href": "http://localhost:8082/api/v1/role/3"
                }
              ]
           }
         ]
       ```
   * [Get role by membership (GET)](http://localhost:8082/api/v1/role/{{userId}}/{{teamId}})

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/role/371d2ee8-cdf4-48cf-9ddb-04798b79ad9e/7676a4bf-adfe-415c-941b-1739af07039b' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODI3MjcsImlhdCI6MTY2OTQ4MTgyN30.SfX1d8R783hbHsuPnCFkKS8C82ACurRhoIyQnUQ0izN27h7F-kQYfovjvhhPnvPVmk7kNJd128sZohmTP96bMNma5Qot9b1vcp8SIfYwahR7BVE3aoyuFccfU-4jGJMBasS9UVYLvHHjS2DTrj3yrlaRA3CnyYLMY2LfnrvsPV4ItG_THTYl1pqLsNLmemyLp19oNKgHFbZlBLRotqTsQCmcVqRijWhZX0IKOjvsNB3BBZwKhrmr3t72BpVhqOa-8vUnzv9OVnPQsRjck5duFi9ZPj-LtY1eXF2jXNSSOT_vFna9LGy310FmJ_7UfqNutJphr2Xp5mdpjOt2_EyyalZcNpKwGv2V4iUupYLT2r0_kWxuIU4oxFTcmW2IrVpX0Or4M-ns7jVQYCBoGpV1XWSGKOjD6NJWyE72iOxPTyJg3HlLOfT3L_LuGn7Sr_ALxi_aoHB_pvFP-OiP9yXkWMeH6mR8KkJ12MKwrQhZw6M7S-121-95gKalBl6JRym8vuWWAFcm2NzE9toQwrNFAgLTrrOnF7l1-6aTd0W-W97jUD7knBHTp2eRwEHVx7Rw2pVQFAAyDBjSCB8o21nIu_nV7va2zXjBj6sTJHT4lq4oD9RCuTW5P8gYMky_WhLO-jM_vjR1Kdo8uFBijfE94oBQPZGl6x5Skyrj08sWJf0'
       ```
     Response:

       ```
         {
            "id": 1,
            "idTeam": "7676a4bf-adfe-415c-941b-1739af07039b",
            "idUser": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
            "role": {
                 "id": 5,
                 "name": "Product Owner",
                 "links": []
            },         
            "links": [
                {
                    "rel": "role",
                    "href": "http://localhost:8082/api/v1/role/1"
                }
            ]
         }
       ```
   * [Create role (POST)](http://localhost:8082/api/v1/role/)

     Request:

          ```
           curl --location --request POST 'http://localhost:8082/api/v1/role/' \
                --header 'Content-Type: application/json' \
                --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODI3MjcsImlhdCI6MTY2OTQ4MTgyN30.SfX1d8R783hbHsuPnCFkKS8C82ACurRhoIyQnUQ0izN27h7F-kQYfovjvhhPnvPVmk7kNJd128sZohmTP96bMNma5Qot9b1vcp8SIfYwahR7BVE3aoyuFccfU-4jGJMBasS9UVYLvHHjS2DTrj3yrlaRA3CnyYLMY2LfnrvsPV4ItG_THTYl1pqLsNLmemyLp19oNKgHFbZlBLRotqTsQCmcVqRijWhZX0IKOjvsNB3BBZwKhrmr3t72BpVhqOa-8vUnzv9OVnPQsRjck5duFi9ZPj-LtY1eXF2jXNSSOT_vFna9LGy310FmJ_7UfqNutJphr2Xp5mdpjOt2_EyyalZcNpKwGv2V4iUupYLT2r0_kWxuIU4oxFTcmW2IrVpX0Or4M-ns7jVQYCBoGpV1XWSGKOjD6NJWyE72iOxPTyJg3HlLOfT3L_LuGn7Sr_ALxi_aoHB_pvFP-OiP9yXkWMeH6mR8KkJ12MKwrQhZw6M7S-121-95gKalBl6JRym8vuWWAFcm2NzE9toQwrNFAgLTrrOnF7l1-6aTd0W-W97jUD7knBHTp2eRwEHVx7Rw2pVQFAAyDBjSCB8o21nIu_nV7va2zXjBj6sTJHT4lq4oD9RCuTW5P8gYMky_WhLO-jM_vjR1Kdo8uFBijfE94oBQPZGl6x5Skyrj08sWJf0' \
                --data-raw '{
                       "name": "QA"
                  }'
          ```
     Response:

          ```
            {
               "id": 4,
               "name": "QA",
               "links": [
                 {
                    "rel": "role",
                    "href": "http://localhost:8082/api/v1/role/4"
                 }
               ]
            }
          ```
   * [Assign role (PATCH)](http://localhost:8082/api/v1/role/)

     Request:

          ```
           curl --location --request PATCH 'http://localhost:8082/api/v1/role/' \
                --header 'Content-Type: application/json' \
                --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ik9vd2xpc2ggVGVhbSBBUEkiLCJleHAiOjE2Njk0ODI3MjcsImlhdCI6MTY2OTQ4MTgyN30.SfX1d8R783hbHsuPnCFkKS8C82ACurRhoIyQnUQ0izN27h7F-kQYfovjvhhPnvPVmk7kNJd128sZohmTP96bMNma5Qot9b1vcp8SIfYwahR7BVE3aoyuFccfU-4jGJMBasS9UVYLvHHjS2DTrj3yrlaRA3CnyYLMY2LfnrvsPV4ItG_THTYl1pqLsNLmemyLp19oNKgHFbZlBLRotqTsQCmcVqRijWhZX0IKOjvsNB3BBZwKhrmr3t72BpVhqOa-8vUnzv9OVnPQsRjck5duFi9ZPj-LtY1eXF2jXNSSOT_vFna9LGy310FmJ_7UfqNutJphr2Xp5mdpjOt2_EyyalZcNpKwGv2V4iUupYLT2r0_kWxuIU4oxFTcmW2IrVpX0Or4M-ns7jVQYCBoGpV1XWSGKOjD6NJWyE72iOxPTyJg3HlLOfT3L_LuGn7Sr_ALxi_aoHB_pvFP-OiP9yXkWMeH6mR8KkJ12MKwrQhZw6M7S-121-95gKalBl6JRym8vuWWAFcm2NzE9toQwrNFAgLTrrOnF7l1-6aTd0W-W97jUD7knBHTp2eRwEHVx7Rw2pVQFAAyDBjSCB8o21nIu_nV7va2zXjBj6sTJHT4lq4oD9RCuTW5P8gYMky_WhLO-jM_vjR1Kdo8uFBijfE94oBQPZGl6x5Skyrj08sWJf0' \
                --data-raw '{
                      "idUser":"371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
                      "idTeam": "7676a4bf-adfe-415c-941b-1739af07039b",
                      "role": {
                         "id":2,
                         "name": "Product Owner"
                      }    
                }'
          ```
     Response:

          ```
            {
               "id": 1,
               "idTeam": "7676a4bf-adfe-415c-941b-1739af07039b",
               "idUser": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
               "role": {
                  "id": 2,
                   "name": "Product Owner",
                   "links": []
               },
               "links": [
                 {
                    "rel": "role",
                    "href": "http://localhost:8082/api/v1/role/1"
                 }
               ]
            }
          ```
### API features

* Java/Spring boot maven project.
* MySQL DB
* Testconteiner (for integration test)
* Oauth2 Authentication/Authorization
* Hateoas support
* Etag support
* [Swagger documentation](http://localhost:8082/v2/api-docs) and [Swagger UI](http://localhost:8082/swagger-ui.html)
* [PostMan collection](postman/oowlish.postman_collection.json)

### How to run the API

* From the console line in the root folder of the project execute the following command to generate the .jar file.
  ```
  ./mvnw clean package
  ```
* Now run the application by:

   ```
   ./mvnw spring-boot:run
   ```
* The API by default will be reachable in the following URL *http://localhost:8082*
* *(Optional)* Use [PostMan collection](postman/oowlish.postman_collection.json) to hit the endpoints.

### Setting Database
This API use a MySQL database so we need to configure an instance with the following parameters:
  * Credentials by default.
    * user: root
    * password: 12345
  * We need to create a database in the MySQL server called ***oowlishroledb***.
      ```
     create database oowlishroledb;
     ```
  * Database should be expose at default port 3306.
  * Spring boot will populate 3 initial roles in the role table:
    * *Developer*
    * *Project Manager*
    * *Tester*
    
### Orchestration with Containers
This project comes with a [docker-compose](docker-compose.yaml) file, which start the following containers:
* mysql: MySQL server exposed on default port (3304)
* app: Spring boot application (Rest API).

To start these containers execute the following command

   ```
     docker-compose up
   ```
     
To stop these containers execute the following command

   ```
     docker-compose down
   ```
### Code Coverage
Complete coverage of the following layers:
* Controller
* Service
* Entities
* Exceptions
* Hateoas

![](images/coverage.png)

#### Web version

![](images/coverageReport.png)

### Swagger UI

![](images/swagger-UI.png)

1. Authentication
   ![](images/Swagger-Authentication.png)
2. Team
   ![](images/Swagger-Team.png)
3. Player
   ![](images/Swagger-User.png)
4. Transfer List
   ![](images/Swagger-Role.png)