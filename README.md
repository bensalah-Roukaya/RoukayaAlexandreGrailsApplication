# Description:
Ce projet s'agit d'une plateforme de gestion d’annonces et fournit une API permettant de gérer des annonces.

L'ensembre des sevices web disponibles via le point d'entrée: `/projet/api/annonces` sont:


*   Récuperer la liste des annonces
*   Créer une annonce

Les fonctionnalités disponibles via le point d'entrée: `/projet/api/annonce` sont:

*   Récuperer une annonce
*   Modifier la totalité d'une annonce
*   Mofifier partiellement une annonce
*   Supprimer une annonce

Cette API utilise ces codes de status pour résumer le résultat de l’opération :

*   200 : OK
*   201 : Created
*   204 : No Content (delete)
*   400 : Bad Request
*   401 : Unauthorized
*   403 : Forbidden
*   404 : Not Found
*   405 : Method not allowed
*   500 : Internal Server Error

Le fichier `annonce API.postman_collection_IbenSalah_Trujillo.json` contient une serie d'appels pour tester le bon foctionnement de notre API

