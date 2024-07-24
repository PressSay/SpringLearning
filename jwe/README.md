= OAuth 2.0 Resource Server Sample

This sample demonstrates integrating Resource Server with a mock Authorization Server, though it can be modified to integrate
with your favorite Authorization Server. This resource server is configured to accept JWE-encrypted tokens.

With it, you can run the integration tests or run the application as a stand-alone service to explore how you can
secure your own service with OAuth 2.0 Bearer Tokens using Spring Security.

== 1. Running the tests

To run the tests, do:

```bash
./gradlew integrationTest
```

Or import the project into your IDE and run `OAuth2ResourceServerApplicationTests` from there.

=== What is it doing?

By default, the tests are pointing at a mock Authorization Server instance.

The tests are configured with a set of hard-coded tokens originally obtained from the mock Authorization Server,
and each makes a query to the Resource Server with their corresponding token.

The Resource Server decrypts the token and subsquently verifies it with the Authorization Server and authorizes the request, returning the phrase

```bash
Hello, subject!
```

where "subject" is the value of the `sub` field in the JWT returned by the Authorization Server.

== 2. Running the app

To run as a stand-alone application, do:

```bash
./gradlew bootRun
```

Or import the project into your IDE and run `OAuth2ResourceServerApplication` from there.

Once it is up, you can get token as the following:

```bash
curl --insecure -i -X POST -H "Content-Type:application/x-www-form-urlencoded" -d '{"username":"lpq","password":"io","grant_type":"Password","client_id":"spring-s1"}' yourKeycloakpath/realms/master/protocol/openid-connect/token
```

And then make this request:

```bash
curl -H "Authorization: Bearer $TOKEN" localhost:8080
```

Which will respond with the phrase:

```bash
Hello, subject!
```

where `subject` is the value of the `sub` field in the JWT returned by the Authorization Server.

Or this:

```bash
curl -H "Authorization: Bearer $TOKEN" localhost:8080/message
```

Will respond with:

```bash
secret message
```

== 2. Testing against other Authorization Servers

_In order to use this sample, your Authorization Server must encrypt using the public key available in the sample.
Also it must support JWTs that either use the "scope" or "scp" attribute._

To change the sample to point at your Authorization Server, simply find this property in the `application.yml`:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:9000/realms/master/protocol/openid-connect/certs
```

And change the property to your Authorization Server's JWK set endpoint:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: https://localhost:9000/pf/JWKS
```

If your Authorization Server does not support RSA_OAEP_256 or AESGCM, then you can change these values in `OAuth2ResourceServerSecurityConfiguration`:

```java

```

And then you can run the app the same as before:

```bash
./gradlew bootRun
```

Make sure to obtain valid tokens from your Authorization Server in order to play with the sample Resource Server.
To use the `/` endpoint, any valid token from your Authorization Server will do.
To use the `/message` endpoint, the token should have the `message:read` scope.
