
  <?php
  if (!include('lib')) {
  
 }
   echo 'lolololol';
  // Les chaînes suivantes sont valides en JavaScript mais pas en JSON

	// Le nom et la valeur doivent être entourrés de double-guillemets.
	// Les simple-guillements ne sont pas valides
	$bad_json = "{ 'bar': 'baz' }";
	json_decode($bad_json); // null
	
	// le nom doit être entourré de double-guillemets
	$bad_json = '{ bar: "baz" }';
	json_decode($bad_json); // null
	
	// la virgule de fin n'est pas autorisée
	$bad_json = '{ bar: "baz", }';
	json_decode($bad_json); // null
	
		
	// Encode les données.
	$json = json_encode(
	    array(
	        1 => array(
	            'English' => array(
	                'One',
	                'January'
	            ),
	            'French' => array(
	                'Une',
	                'Janvier'
	            )
	        )
	    )
	);
	
	// Définie les erreurs
	$constants = get_defined_constants(true);
	$json_errors = array();
	foreach ($constants["json"] as $name => $value) {
	 if (!strncmp($name, "JSON_ERROR_", 11)) {
	  $json_errors[$value] = $name;
	 }
	}
	
	// Affiche les erreurs pour les différentes profondeurs.
	foreach (range(4, 3, -1) as $depth) {
	    var_dump(json_decode($json, true, $depth));
	    echo 'Dernière erreur : ', $json_errors[json_last_error()], PHP_EOL, PHP_EOL;
	}
  ?>