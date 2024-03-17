-----------------------------------------------------TABLE DES UTILISATEURS-------------------------------------------------------------------------------------
ALTER SEQUENCE utilisateur_id_seq INCREMENT BY 1;

insert into Utilisateur(id,nom,age,poste,competences,note,image)
values(1,'Bob MARLEY',48,'Architecte Java','JEE,TALEND,BO','4,5','assets/images/bob.png');

insert into Utilisateur(id, nom,age,poste,competences,note,image)
values(2, 'Alice DUPOND', 34,'Ingenieur BI', 'HTML,CSS,MSBI','2,5' ,'assets/images/alice.png');

insert into Utilisateur(id, nom,age,poste,competences,note,image)
values(3, 'Charles CAPAIN', 33,'Consultant Java', 'BI,QLIKVIEW,PYTHON','5', 'assets/images/Charles.png');

insert into Utilisateur(id, nom,age,poste,competences,note,image)
values(4, 'Rob BOB', 23,'Consultant Power BI', 'MAVEN,HIBERNATE,AIA','3,5', 'assets/images/rob.png');

insert into Utilisateur(id, nom,age,poste,competences,note,image)
values(5, 'Kilian MISERE', 38,'Informaticien', 'KAFKA,QLIKSENCE,C++','4','assets/images/Kilian.png');

insert into Utilisateur(id, nom,age,poste,competences,note,image)
values(6, 'Roland FRANCK', 40,'Ingenieur Decisionnel', 'TEST,APPELLE,INTERNET','3,7','assets/images/Roland.png');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
