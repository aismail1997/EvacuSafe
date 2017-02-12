<?php
	$db_host = 'localhost'; 
	$db_username =  'myuser';
	$db_pass =      'mypassword';
	$db_name =      'myDB'; 

	mysql_connect("$db_host","$db_username","$db_pass", TRUE) or die(mysql_error());
	mysql_select_db("$db_name") or die("no database by that name");
	 
	$id=$_REQUEST['id'];
	 
	$r=mysql_query("select * from sample where id='$id'",$con);

	while($row=mysql_fetch_array($r))
	{
		$flag[name]=$row[name];
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>