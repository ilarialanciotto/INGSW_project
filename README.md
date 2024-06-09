# INGSW_project

//Italian 
Quesa applicazione gestiste un interprete dei comandi che esegue varie azioni su oggeti quali cerchio,rettangolo e immagine o gruppi di oggetti, alcune di queste azioni perimettono anche undo-redo
i possibili comandi sono 
●	new circle (5.0) (3.1,4.5)
crea un cerchio di raggio 5.0 in posizione  (3.1,4.5) e ne restituisce l’ID
●	new img (“./pippo.png”) (6.1,4.6)
crea un cerchio un’immagine a partire dal contenuto del file “./pippo.png” in posizione  (6.1,4.6) e ne restituisce l’ID
Rimozione di un oggetto o un gruppo di oggetti 
●	del id1
rimuove l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1
Spostamento di un oggetto o un gruppo di oggetti 
●	mv id1 (5.9,8.2)
sposta l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 nella posizione (5.9,8.2)
●	mvoff id1 (5.9,8.2)
sposta l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 nella posizione ottenuta sommando (5.9,8.2) alla posizione corrente
Ridimensionamento di un oggetto o un gruppo di oggetti 
●	scale id1 2.0
ridimensiona l’oggetto (o il gruppo di oggetti) identificato  dall’ID id1 con un fattore di scala pari a 2.0
Visualizzazione delle proprietà di un oggetto, un gruppo di oggetti o di un tipo di oggetti
●	ls id1
visualizza le proprietà dell’oggetto identificato da id1 o l’elenco degli oggetti parte del gruppo identificato da id1
●	ls circle
visualizza l’elenco degli oggetti di tipo circle
●	ls all
visualizza l’elenco di tutti gli oggetti
●	ls groups
Creazione di un gruppo di oggetti
●	grp id1, id2, id3
crea un nuovo gruppo che contiene 3 i elementi identificati da id1, id2 e id3. Ciascuno di essi può essere l’identificativo di un oggetto o di un gruppo. Il comando visualizza l’identificativo generato per il gruppo creato.
Rimozione di un gruppo di oggetti
●	ungrp gid
rimuove il gruppo identificato da id (senza rimuovere i suoi componenti!)
Calcolo area (perimetro) 
●	area id1
calcola l’area dell’oggetto identificato da id1 o la somma delle aree degli oggetti parte del gruppo identificato da id1
●	perimeter rectangle
calcola la somma dei perimetri degli oggetti di tipo rectangle
●	area all
calcola la somma delle aree di tutti gli oggetti.
Durante l'invio dei vari comandi se si sbaglia a inserire un comando apparirà un messaggio di errore e la cronologia dei comandi precedenti verrà cancellata dalla schermata e bisognerà reinserire il comando corretto
Se invece non sono presenti oggetti o gruppi potrebbe apparire un messaggio di errore di object or group not found oppure nel caso di ls circle / groups verrà visualizzata una finestra vuota , mentre nel caso di area o perimetro verrà visualizzato area / perimetro = 0 
La creazione o rimozione di gruppi di oggetti invece verrà viasualizzata nella seconda finestra

//English
This application maintains a command interpreter that performs various actions on objects such as circles, rectangles and images or groups of objects, some of these actions also allow undo-redo
Possible commands are 
● New Circle (5.0) (3.1,4.5)
creates a circle of radius 5.0 in place (3.1,4.5) and returns its ID
● new img ("./pippo.png") (6.1,4.6)
creates a circle on an image from the contents of the "./pippo.png" file in place (6.1,4.6) and returns its ID
Removing an Object or Group of Objects 
● ID1
removes the object (or group of objects) identified by the id1 ID
Moving an Object or Group of Objects 
● MV ID1 (5.9,8.2)
moves the object (or group of objects) identified by the id1 ID to the location (5.9,8.2)
● MVOFF ID1 (5.9,8.2)
moves the object (or group of objects) identified by the id1 ID to the position obtained by summing (5.9,8.2) to the current position
Resizing an Object or Group of Objects 
● Stairs ID1 2.0
resizes the object (or group of objects) identified by the id1 ID with a scale factor of 2.0
Viewing the Properties of an Object, Object Group, or Object Type
● LS ID1
Displays the properties of the object identified by ID1 or the list of objects that are part of the group identified by ID1
● LS Circle
Displays the list of objects of type Circle
● LS ALL
View the list of all objects
● LS Groups
Creating an Object Group
● GRP ID1, ID2, ID3
Create a new group that contains 3 items identified by ID1, ID2, and ID3. Each of them can be the identifier of an object or a group. The command displays the identifier generated for the group that you created.
Removing an Object Group
● UNGRP GID
Removes the group identified by ID (without removing its components!)
Area Calculation (Perimeter) 
● Area ID1
Calculates the area of the object identified by ID1 or the sum of the areas of the objects that are part of the group identified by ID1
● Perimeter Rectangle
Calculates the sum of the perimeters of rectangle objects
● All Area
Calculates the sum of the areas of all objects.
During the sending of the various commands, if you make a mistake in entering a command, an error message will appear and the history of previous commands will be deleted from the screen and you will have to re-enter the correct command. If, on the other hand, there are no objects or groups, an error message of object or group not found may appear, or in the case of ls circle / groups a blank window will be displayed, while in the case of area or perimeter it will display area / perimeter = 0 
The creation or removal of groups of objects will be displayed in the second window
