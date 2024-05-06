# Open with github "Projet_Danmachi" project in IntelliJ

1. Open IntelliJ
2. File -> New -> Project from version controle
3. Select github [your name] -> Projet_Danmachi and choose your work directory

if neccessary :
5. Setup the SDK to java 17 (go to src/main/java/com/isep/appli/ProjetDanmachiApplication.java)
6. Build maven project : Press Ctrl + Shift + 0 + A and tap "Add Maven projects"
7. Reaload Moven Projects : recreate
8. Maven build scripts found : load Maven Project

.idea folder will be created (in gitignore)  
Projet_Danmachi.iml file will be created (in gitignore)  
target folder will be created (in gitignore)  
You must create your table "danmachi" in phpMyAdmin from xampp and start the serveur  


# Comment merger ma branche sur le master ?
0. Ne pas oublier de commit et push sa branche sur github
1. Aller dans IntelliJ -> onglet (du bas) Git
2. Se mettre sur la branche Local.master (clic droit ->check out)
3. Clic droit sur ma branche et clic sur " merge "maBranche" into master
4. Optionnel : on peut supprimer sa branche si on ne compte plus travailler dessus
5. Toujours en étant dans la branche Local.master : clic droit sur Local.master et "push"

