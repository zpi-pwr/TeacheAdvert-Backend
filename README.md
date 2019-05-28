# AdvertBoard Backend

Backend of the application

## Path prefix to all queries
```
/api
```

# Queries

## Auth

### Sign Up
path: 
```
/auth/signup
```

method: post
request body:
```
{"name":<USERNAME>, "email":<EMAIL>,"password":<PASSWORD>}
```
### Log in
path: 
```
/auth/login
```

method: post
request body:
```
{"email":<EMAIL>,"password":<PASSWORD>}
```
reponse body:
```
{"accessToken":<TOKEN>, "tokenType":<TYPE>
```

## User

### Me (GET)
path: 
```
/user/me
```

method: get
reponse body:
```
{
    "name": <NAME>,
    "email": <MAIL>,
    "emailVerified": true/false,
    "adverts": <ADVERTS>,
    "provider": <PROVIDER>,
    "profileView": {
        "id": <PROFILE_ID>,
        "visibleName": <VISIBLE_NAME>,
        "firstName": <FIRST_NAME>,
        "lastName": <LAST_NAME>,
        "telephoneNumber": <TEL_NUMBER>,
        "contactMail": <CONTACT_MAIL>
        "rating": <RATING>,
        "ratingCount": <RATING_COUNT>
    },
    "role": <ROLE>
}
```


### Me (POST)
path: 
```
/user/me
```

method: post
request body:
```
{
  "visibleName": <VISIBLE_NAME>,
  "firstName": <FIRST_NAME>,
  "lastName": <LAST_NAME>,
  "telephoneNumber": <TEL_NUMBER>,
  "contactMail": <CONTACT_MAIL>
}
```

### All
path:
```
/user/all
```
method: get
request parameters: page, limit, sort, nameContains (you can put anything that exists to the sort, f.e. profileVisibleName)
response body example:
```
{
    "content": [
        {
            "id": 1,
            "visibleName": "testUser"
        },
        {
            "id": 2,
            "visibleName": "testUser"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageSize": 20,
        "pageNumber": 0,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 2,
    "totalPages": 1,
    "last": true,
    "first": true,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "number": 0,
    "numberOfElements": 2,
    "size": 20,
    "empty": false
}
```

### Get
path:
```
user/get
```
method: get
request parameters: profileId
response body:
```
{
    "id": <ID>,
    "visibleName": <VISIBLE_NAME>,
    "firstName": <FIRST_NAME>,
    "lastName": <LAST_NAME>,
    "telephoneNumber": <TEL_NUM>,
    "contactMail": <MAIL>,
    "advertSummaryViews": <ADVERTS>
    "rating": <RATING>,
    "ratingCount": <RATING_COUNT>
    
}
```

### Rate profile
path:
```
user/rate
```
method: post
request parameters: profileId, rating ( 1 <= rating <= 5)

## Advert

### Add
path:
```
/advert/add
```
method: post
request body example:
```
{"title": "Title1", "tags": ["tag1", "tag2"], "description": "Lorem ipsum .... ", "conversationId': "2137"},
```

### Edit
path:
```
/advert/edit
```
method: post
request body example:
```
{"id": "1", "title": "Title1", "tags": ["tag1", "tag2"], "description": "Lorem ipsum .... "}
```

### Remove
path:
```
/advert/remove
```
method: post
request param: id

### Get
path:
```
/advert/get
```
method: get
response body example:
```
{
    "id": "1",
    "title": "abc",
    "date": "2019-05-09",
    "profileId": 1,
    "profileName": "testUser",
    "description": "ab",
    "tags": [
        "tag1",
        "tag2"
    ],
    "status": "EDITED",
    "conversationId': "2137"
}
```

### Browse
path:
```
/advert/browse
```
method: get
request parameters: page, limit, sort, titleContains, tags (list of strings)  
response body example:
```
{
    "content": [
        {
            "id": 1,
            "title": "abc",
            "pic": null,
            "date": "2019-05-09"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageSize": 20,
        "pageNumber": 0,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "first": true,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "number": 0,
    "numberOfElements": 1,
    "size": 20,
    "empty": false
}
```

