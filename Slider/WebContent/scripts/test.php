<?php

function endsWith( $str, $sub ) {
   return ( substr( $str, strlen( $str ) - strlen( $sub ) ) === $sub );
}

function watch($dirname){

	$retour = '';
	$dir = opendir($dirname);
	 
	while($file = readdir($dir)) {
	
		if(is_dir($dirname.'/'.$file)){
			watch($dirname.'/'.$file);
		}else{
			if($file != '.' && $file != '..')
			{
				$retour .= 'fichier :'.$dirname.'/'.$file;
			}
		}
		
	}
	
	closedir($dir);
	
	return $retour;
}

echo watch('../json');

?>