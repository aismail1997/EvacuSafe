<?php
	$db_host = 'localhost';  //RDS Endpoint...
	$db_username =  'myuser';
	$db_pass =      'mypassword';
	$db_name =      'myDB'; 

	mysql_connect("$db_host","$db_username","$db_pass", TRUE) or die(mysql_error());
	mysql_select_db("$db_name") or die("no database by that name");

	$id=$_REQUEST['id'];
	$name=$_REQUEST['name'];
	 
	$flag['code']=0;
	 
	if($r=mysql_query("UPDATE sample SET name = '$name' WHERE id ='$id'",$con))
	{
		$flag['code']=1;
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>