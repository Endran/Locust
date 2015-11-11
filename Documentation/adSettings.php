<?php

	define("SITE_MYSQL_SERVER",     "mysql03.totaalholding.nl");
	define("SITE_MYSQL_USERNAME",   "endran_readonly");
	define("SITE_MYSQL_PASSWORD",   "P{#Qg=r(=.%S");
	define("SITE_MYSQL_DATABASE",   "endran_locust");
	define("SITE_MYSQL_TABLE",   "AdSettings");

	function getAdSettings(){
		$pdo = new PDO('mysql:host=' . SITE_MYSQL_SERVER . ';dbname=' . SITE_MYSQL_DATABASE, SITE_MYSQL_USERNAME, SITE_MYSQL_PASSWORD);
		$stmt = $pdo->prepare('SELECT * FROM ' . SITE_MYSQL_TABLE . ' WHERE id=0');
		$stmt->execute();
		return $stmt->fetchAll();
	}		
		
	$rows = getAdSettings();

	echo json_encode($rows);
	
	$row = null;
	$stmt = null;
	$pdo = null;
?>
