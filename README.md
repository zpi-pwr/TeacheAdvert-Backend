# AdvertBoard Backend

Backend of the application

# Methods

### Sign Up

```
/auth/signup
```

header

```
Content-Type: application/json
```

method

```
POST
```

data

```
{"name":<USERNAME>, "email":<EMAIL>,"password":<PASSWORD>}
```

### Log in

```
/auth/login
```

header

```
Content-Type: application/json
```

method

```
POST
```

data

```
{"email":<EMAIL>,"password":<PASSWORD>}
```

### Current user

```
/user/me
```

header

```
Authorization: Bearer <TOKEN_HERE>
```

method

```
GET
```
