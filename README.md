# AdvertBoard Backend

Backend of the application

## Path prefix to all queries
```
/api
```

# Queries

## User

### Sign Up
```
path: /auth/signup
header: Content-Type: application/json
method: POST
request body: {"name":<USERNAME>, "email":<EMAIL>,"password":<PASSWORD>}
```

### Log in
```
path: /auth/login
header: Content-Type: application/json
method: POST
request body: {"email":<EMAIL>,"password":<PASSWORD>}
```

### Current user
```
path: /user/me
header: Authorization: Bearer <TOKEN>
method: GET
```

## Advert

### Add advert
```
path: /advert/add
header: Authorization: Bearer <TOKEN>; Content-Type: application/json
method: POST
request body: {"title":<TITLE>, "tags":<TAGS>, "description":<DESC>, imgUrls:<IMGURLS>, subcategory:<SUBCAT>}
```

### Edit advert
```
path: /advert/edit
header: Authorization: Bearer <TOKEN>; Content-Type: application/json
method: POST
request body: {"id":<ID>, "title":<TITLE>, "tags":<TAGS>, "description":<DESC>, imgUrls:<IMGURLS>}
```

### Remove advert
```
path: /advert/remove
header: Authorization: Bearer <TOKEN>
method: POST
request param: id
```

### All adverts
```
path: /advert/all
method: GET
request param: page, limit, sort
```

### Get advert
```
path: /advert/get
method: GET
request param: id
```

## Category

### Add category
```
path: /category/add
header: Authorization: Bearer <TOKEN>; Content-Type: application/json
method: POST
request body: {"categoryName":<NAME>, "description":<DESC>}
```

### Remove category
```
path: /category/remove
header: Authorization: Bearer <TOKEN>
method: POST
request param: categoryName
```

### All categories
```
path: /category/all
method: GET
```

### Get category adverts
```
path: /category/get
method: GET
request param: categoryName, page, limit, sort
```

## Subcategory

### Add subcategory
```
path: /subcategory/add
header: Authorization: Bearer <TOKEN>; Content-Type: application/json
method: POST
request body: {"subcategoryName":<NAME>, "description":<DESC>, "category":<CAT>}
```

### Remove subcategory
```
path: /subcategory/remove
header: Authorization: Bearer <TOKEN>
method: POST
request param: subcategoryName
```

### Edit subcategory
```
path: /subcategory/add
header: Authorization: Bearer <TOKEN>; Content-Type: application/json
method: POST
request param: subCategoryName, newCategoryName
```

### All subcategories
```
path: /subcategory/all
method: GET
```

### Get subcategory adverts
```
path: /subcategory/get
method: GET
request param: categoryName, page, limit, sort
```


