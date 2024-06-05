Pour configurer le port utilisé par le serveur d'application (tomcat), la propriété à modifier dans le fichier
application.properties est
`server.port=8085`

Pour accéder à la documentation openapi, il faut démarrer l'application, puis naviguer sur les pages suivantes :

- http://localhost:8085/swagger-ui/index.html (documentation générée de type swagger, permet également de tester
  manuellement l'api)

- http://localhost:8085/v3/api-docs.yaml (contrat au format yaml)
- http://localhost:8085/v3/api-docs (contrat au format json)