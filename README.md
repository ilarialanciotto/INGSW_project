# INGSW_project

<b> <H2> //Italian </H2> </b> <br>
Quesa applicazione gestiste un interprete dei comandi che esegue varie azioni su oggeti quali cerchio,rettangolo e immagine o gruppi di oggetti, alcune di queste azioni perimettono anche undo-redo <br>
i possibili comandi sono  <br>
●	new circle (5.0) (3.1,4.5) <br>
crea un cerchio di raggio 5.0 in posizione  (3.1,4.5) e ne restituisce l’ID <br>
●	new img (“./pippo.png”) (6.1,4.6) <br>
crea un cerchio un’immagine a partire dal contenuto del file “./pippo.png” in posizione  (6.1,4.6) e ne restituisce l’ID <br>
Rimozione di un oggetto o un gruppo di oggetti  <br>
●	del id1 <br>
rimuove l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 <br>
Spostamento di un oggetto o un gruppo di oggetti  <br>
●	mv id1 (5.9,8.2) <br>
sposta l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 nella posizione (5.9,8.2) <br>
●	mvoff id1 (5.9,8.2) <br>
sposta l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 nella posizione ottenuta sommando (5.9,8.2) alla posizione corrente <br>
Ridimensionamento di un oggetto o un gruppo di oggetti <br>
●	scale id1 2.0 <br>
ridimensiona l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 con un fattore di scala pari a 2.0 <br>
Visualizzazione delle proprietà di un oggetto, un gruppo di oggetti o di un tipo di oggetti <br>
●	ls id1 <br>
visualizza le proprietà dell’oggetto identificato da id1 o l’elenco degli oggetti parte del gruppo identificato da id1 <br>
●	ls circle <br>
visualizza l’elenco degli oggetti di tipo circle <br>
●	ls all  <br>
visualizza l’elenco di tutti gli oggetti   <br>
●	ls groups     <br>
Creazione di un gruppo di oggetti   <br>
●	grp id1, id2, id3  <br>
crea un nuovo gruppo che contiene 3 i elementi identificati da id1, id2 e id3. Ciascuno di essi può essere l’identificativo di un oggetto o di un gruppo. Il comando visualizza l’identificativo generato per il gruppo creato.  <br>
Rimozione di un gruppo di oggetti   <br>
●	ungrp gid   <br>
rimuove il gruppo identificato da id (senza rimuovere i suoi componenti)  <br>
Calcolo area (perimetro)   <br>
●	area id1    <br>
calcola l’area dell’oggetto identificato da id1 o la somma delle aree degli oggetti parte del gruppo identificato da id1  <br>
●	perimeter rectangle  <br>
calcola la somma dei perimetri degli oggetti di tipo rectangle    <br>
●	area all   <br>
calcola la somma delle aree di tutti gli oggetti.   <br>
Durante l'invio dei vari comandi se si sbaglia a inserire un comando apparirà un messaggio di errore e la cronologia dei comandi precedenti verrà cancellata dalla schermata e bisognerà reinserire il comando corretto.Se invece non sono presenti oggetti o gruppi potrebbe apparire un messaggio di errore di object or group not found oppure nel caso di ls circle / groups verrà visualizzata una finestra vuota , mentre nel caso di area o perimetro verrà visualizzato area / perimetro = 0 . La creazione o rimozione di gruppi di oggetti invece verrà viasualizzata nella seconda finestra

<br>

<b> <H1> //English  <br>
This application maintains a command interpreter that performs various actions on objects such as circles, rectangles and images or groups of objects, some of these actions also allow undo-redo <br>
Possible commands are  <br>
● New Circle (5.0) (3.1,4.5)  <br>
creates a circle of radius 5.0 in place (3.1,4.5) and returns its ID  <br>
● new img ("./pippo.png") (6.1,4.6)  <br>
creates a circle on an image from the contents of the "./pippo.png" file in place (6.1,4.6) and returns its ID   <br>
Removing an Object or Group of Objects   <br>
● del ID1
removes the object (or group of objects) identified by the id1 ID  <br>
Moving an Object or Group of Objects   <br>
● MV ID1 (5.9,8.2)   <br>
moves the object (or group of objects) identified by the id1 ID to the location (5.9,8.2)  <br>
● MVOFF ID1 (5.9,8.2)   <br>
moves the object (or group of objects) identified by the id1 ID to the position obtained by summing (5.9,8.2) to the current position  <br>
Resizing an Object or Group of Objects  <br>
● Scale ID1 2.0  <br>
resizes the object (or group of objects) identified by the id1 ID with a scale factor of 2.0   <br>
Viewing the Properties of an Object, Object Group, or Object Type   <br>
● LS ID1  <br>
Displays the properties of the object identified by ID1 or the list of objects that are part of the group identified by ID1  <br>
● LS Circle   <br>
Displays the list of objects of type Circle  <br>
● LS ALL  <br>
View the list of all objects  <br>
● LS Groups  <br>
Creating an Object Group  <br>
● GRP ID1, ID2, ID3  <br>
Create a new group that contains 3 items identified by ID1, ID2, and ID3. Each of them can be the identifier of an object or a group. The command displays the identifier generated for the group that you created. <br>
Removing an Object Group <br>
● UNGRP GID   <br>
Removes the group identified by ID (without removing its components)  <br>
Area Calculation (Perimeter)   <br>
● Area ID1   <br>
Calculates the area of the object identified by ID1 or the sum of the areas of the objects that are part of the group identified by ID1    <br>
● Perimeter Rectangle   <br>
Calculates the sum of the perimeters of rectangle objects   <br>
● All Area    <br>
Calculates the sum of the areas of all objects.   <br>
During the sending of the various commands, if you make a mistake in entering a command, an error message will appear and the history of previous commands will be deleted from the screen and you will have to re-enter the correct command. If, on the other hand, there are no objects or groups, an error message of object or group not found may appear, or in the case of ls circle / groups a blank window will be displayed, while in the case of area or perimeter it will display area / perimeter = 0 . The creation or removal of groups of objects will be displayed in the second window
