HOW TO INSTALL 

 

Ceci est un guide du débutant pour prendre en main le projet et le faire fonctionner sur votre ordinateur. Vous pouvez, bien sûr, utiliser d’autres logiciels que ceux proposés ci-dessous, mais ce sont eux que nous vous recommandons. 

Nous avons choisi IntelliJ comment environnement de développement (IDE) Java. Il est performant, puissant et est efficace en termes de gestion de projet avec Git et GitHub. Et concernant la base de données, nous avons fait le choix d’un serveur MySQL avec Xampp. Nous utilisant aussi la version 17 de java. 

Une fois votre IDE et votre serveur installés, commencez par démarrer votre serveur Xampp et créez une base de données « danmachi » pour que notre application puisse interagir avec. (Clic sur le bouton Admin en bleu pour aller sur phpMyAdmin) 

Une image contenant texte, capture d’écran, logiciel, Icône d’ordinateur

Description générée automatiquementUne image contenant texte, logiciel, Icône d’ordinateur, Page web

Description générée automatiquement 

Vous pouvez maintenant ouvrir IntelliJ et créer un nouveau projet (File -> New -> Project From Version Control) et ouvrir avec Git : https://github.com/Deathvanos/Projet_Danmachi.git 

Une image contenant texte, capture d’écran, logiciel, Logiciel multimédia

Description générée automatiquementUne image contenant texte, capture d’écran, logiciel, Logiciel multimédia

Description générée automatiquement 

Si nécessaire, configurez le SDK en java 17 et/ou construire le projet avec Maven. Puis lancer l’application en cliquant sur la flèche verte. 

FormeUne image contenant texte, capture d’écran, Police

Description générée automatiquement 

L’application se lance et vous n’avez plus qu’à vous rendre sur votre navigateur web préféré et aller sur http://localhost:8080/. 

HOW TO USE

 

En arrivant sur la page d’accueil de notre application Danmachi, nous vous recommandons de créer deux comptes afin de découvrir l’ensemble des fonctionnalités du site. Un utilisateur normal et un administrateur. Pour cela, inscrivez-vous deux fois (consultez votre boite mail avec inscription). 

Pour mettre en place un administrateur, retournez sur phpMyAdmin et allez dans la table « user » de la base de données « danmachi ». Remplacer la valeur 0 par 1 à votre utilisateur « admin ». 

Une image contenant texte, logiciel, Icône d’ordinateur, nombre

Description générée automatiquement 

Vous pouvez maintenant retourner dans l’application Danmachi. Nous vous conseillons de commencer par vous connecter avec l’administrateur pour créer des objets dans « Items management » puis d’alimenter le magasin dans « Shop management ».  

Connectez vous ensuite avec votre compte utilisateur normal pour « jouer ». 
