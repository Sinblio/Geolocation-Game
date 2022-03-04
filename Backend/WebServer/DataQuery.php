<?php
	$servername="mysql.coms-309-ks-4.misc.iastate.edu:3306";
	$username="ks4";
	$password="CS309_ks4";
	$dbname="game_info";

	// Create information
	$conn = new mysqli($servername, $username, $password, $dbname);

	// Check connection
	if($conn->connect_error)
	{
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "SELECT * FROM game_info.user_stats";
	$result = $conn->query($sql);

	if($result->num_rows > 0)
	{
		//output the data from each row
		while($row = $result->fetch_assoc())
		{
			echo "uid: " . $row["uid"]. "power-ups_used: " . $row["power-ups_used"]. "date_created: " . $row["date_created"]. "last_login: " . $row["last_login"]. "times_extracted: " . $row["times_extracted"]. "players_infected: " . $row["players_infected"]. "games_played: " . $row["games_played"]. "<br>";
		}
	} 
	else
	{
		echo "0 results";
	}
	$conn->close();
?>