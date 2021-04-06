<h2> Contributors </h2>
<a href="https://github.com/erwanZem">
  <img src="https://github.com/erwanZem.png?size=50">
</a>
<a href="https://github.com/Nadran20">
  <img src="https://github.com/nadran20.png?size=50">
</a>

# Application-Serveur-JAVA
<h3>Projet AppServ Java
Réservation/emprunt/retour médiathèque</h3>
<p>On désire gérer les réservations/emprunts/retours de documents pour une médiathèque. Le service ne
sera ouvert qu’aux abonnés référencés de la médiathèque (la création de nouveaux abonnés n’est pas
au programme de ce projet). Pour simplifier, on suppose que tout fonctionne 24h/24h. Les documents
concernés ici sont des DVDs mais cela doit pouvoir évoluer sans difficulté vers des livres, CDs, etc.</p>
<p><ul><li>
La réservation d’un DVD est possible à distance (depuis chez l’abonné) s’il est disponible en
médiathèque ; celui-ci est alors réservé pendant 2h et l’abonné doit passer l’emprunter à la
médiathèque (sinon la réservation est annulée).</li>
<li>L’emprunt d’un DVD sur place est possible si le DVD est disponible (ou réservé à l’abonné qui
vient l’emprunter).</li>
<li>Le retour d’un DVD se passe également sur place.</li></ul></p>
<p><ul><li><u>Chaque DVD a un numéro,</i> un titre et un indicateur booléen adulte, signifiant, s’il est à true, réservé
aux plus de 16 ans. Cette caractéristique est spécifique aux DVDs ; elle n’existe ni pour les livres, ni
pour les CDs.</li>
<li>Chaque abonné a un numéro, un nom et une date de naissance. Les autres informations sont laissées à
votre discrétion.</li>
</ul><p>Lors de la réservation ou de l’emprunt, on doit préciser ces 2 numéros. Lors du retour, le numéro de
DVD suffit (vous pouvez ramener un DVD trouvé dans la rue par exemple ou pour le compte de
quelqu’un d’autre). Pour simplifier, on suppose que ces numéros sont connus de l’abonné qui
réserve/emprunte/rend un document.</p>
<h3>Clients et serveurs</h3>
Une application serveur générale est lancée en permanence sur un ordinateur de la médiathèque
(ou ailleurs) d’adresse IP connue. Cette application attend
<ul><li> les demandes de réservation sur le port 3000,</li>
<li> les demandes d’emprunt sur le port 4000 et</li>
<li> les retours sur le port 5000.</li>
</ul>
<p>Chacune de ces 3 opérations correspondra donc à une application cliente se connectant au port
concerné et échangeant les données avec le service concerné, suivant le modèle logiciel vu en tps.</p>
<p><u>Ces 3 serveurs d’écoute sur 3 ports différents sont contractuels : vous ne pouvez pas choisir une
autre solution technique.</u></p>
<p>Un logiciel client de réservation est installé sur l’ordinateur des adhérents (à terme, une version
mobile sera envisagée). Lorsqu’un abonné lance ce client chez lui, le service de réservation lui
envoie le catalogue et lui demande son numéro d’abonné et le numéro de document qu’il souhaite
réserver.<p>
<p>Les logiciels clients d’emprunt et de retour sont installés sur des bornes dédiées à la
médiathèque.</p>
<h3>Coté serveur</h3>
<p>Les DVDs et les abonnés sont tous matérialisés par des objets créés au lancement du programme.
<u>L’ajout d’abonnés ou de DVDs n’est pas à faire pour ce projet.</u><br>
La tentative de réservation ou d’emprunt, si elle échoue, doit donner lieu à une exception porteuse
du motif de refus détaillé (« ce DVD est déjà emprunté », « vous n’avez pas l’âge pour emprunter
ce DVD », « ce livre est réservé jusqu’à 12h25 », etc). C’est la forme affichable (toString) de cette
exception qui sera renvoyé au client.</p>
<h3>L’interface Document que devra implémenter votre classe DVD</h3>
<p>public interface Document {<br>
int numero();<br>
void reservationPour(Abonne ab) throws ReservationException ;<br>
void empruntPar(Abonne ab) throws EmpruntException;<br>
// retour document ou annulation réservation<br>
void retour();<br>
}<p>
<br>
<p><u>Cette interface est contractuelle et vous n’avez pas le droit de la modifier</u><p>
<h3>Certifications BretteSoft©</h3>
<p>Les options ci-dessous permettent d’obtenir des niveaux de certifications BretteSoft© ; ne vous
lancez dans ces options qu’après avoir terminé et sécurisé le projet sur une copie.</p>
<ul><li>1. (« Géronimo » BretteSoft©)
Certains abonnés rendent les DVDs en retard (parfois avec un gros retard) ; d’autres dégradent
les DVDs qu’ils empruntent ; un abonné, suite à un retard de plus de 2 semaines ou à dégradation
de DVD constatée au retour, sera interdit d’emprunt pendant 1 mois.</li>
<li>2. (« Sitting bull » BretteSoft©)
Lors d’une réservation, si le DVD n’est pas disponible, on pourra proposer de placer une alerte
mail nous avertissant lors du retour du DVD. La certification suppose l’exploration des
bibliothèques de mail java et l’envoi d’un mail-test dans le contexte approprié à l’abonné Brette.</li></ul>
<h2>A rendre</h2>
<p>Le projet est à réaliser par trinômes (binômes si vraiment vous n’arrivez pas à former un trinôme)
de préférence au sein du même bigroupe. Les 3 séances de tp des semaines B-5 à B-7 lui sont
consacrées. La remise est fixée au plus tard le dimanche 10 janvier 23h59 sur le cours moodle :
dépôt du dossier sous forme d’UN fichier .zip nommé des noms des membres du groupe (mettez
juste NOM_Prenom de chaque membre, ni le groupe, ni ProjetJavaAppServeuretc…) et contenant
le code source (Java source files) et un rapport de présentation pdf de quelques pages dont le
contenu sera précisé en tp.</p>
<p><b>Aucun retard ne sera accepté (même avec points de pénalités).</b></p>
<i>Le projet est fait pour s’entrainer, s’entraider et assimiler les principes et techniques vues en
cours et TPs. Le coefficient est donc faible (1) en comparaison du dst (3) qui est un contrôle
individuel du niveau. Même si ce n’est pas le cœur du module AppServ, un découplage correct en
terme de packages et d’indépendance de code (cf BPO-2) sera partie prenante de la note.<i>
