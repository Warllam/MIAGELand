
Création container docker : docker run --name miageland-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=miageland_bdd -p 3306:3306 -d mysql

connection a la database dans terminal docker : mysql -p

connection à la bd du projet : connect miageland_bdd;

Attention comme les droits d'accès aux endpoints sont strict voici l'adresse mail pour se connecter au gérant qui est déjà implémenté  :
paul@lamaud.fr