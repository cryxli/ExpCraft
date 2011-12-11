<?php

if (!@include_once('config.php')) {
	die(json_encode(array("status" => "no config.php found")));
}

$arg = $_SERVER["PATH_INFO"];
if (strtolower($arg) == "/config") {
	die(json_encode(array("status" => "ok", "playerFaces" => $conf['playerFaces'])));
} else if (strtolower($arg) == "/all" or strlen($arg) > 1) {
	$arg = substr($arg,1);
} else {
	$arg = "all";
}

$conn = @mysql_connect($conf['db']['url'], $conf['db']['user'], $conf['db']['pass']);
if (!$conn) {
	die(json_encode(array("status" => "no db connection")));
}
@mysql_select_db($conf['db']['database']) or die('"data":{"status":"no database selected"}');

# get all existing modules
$result = mysql_query("SELECT DISTINCT module FROM ExpCraftTable ORDER BY module");
if (!$result) {
	die(json_encode(array("status" => "no module data")));
}

$modules = array();
while ($row = mysql_fetch_assoc($result)) {
	array_push($modules, array("short" => $row['module'], "name" => $conf['module'][$row['module']]));
}
mysql_free_result($result);

# get all existing experience entries
$sql = <<<EOS
SELECT
	 c.player
	,c.module
	,IFNULL(t.exp,0) AS exp
	,1 + FLOOR(SQRT(IFNULL(t.exp,0) / ${conf['constant']})) AS level
  FROM
	(SELECT * FROM
		(SELECT DISTINCT player FROM ExpCraftTable) p
		CROSS JOIN
		(SELECT DISTINCT module FROM ExpCraftTable) m
	) c
  LEFT JOIN
	ExpCraftTable t
    ON t.player = c.player AND t.module = c.module
EOS;
if ($arg != "all") {
	$sql .= " WHERE c.player = '$arg'";
}
$sql .= " ORDER BY player, module";

$result = mysql_query($sql);
if (!$result) {
	die(json_encode(array("status" => "no player data")));
}
$exp = array();
while ($row = mysql_fetch_assoc($result)) {
	array_push($exp, array("player" => $row['player'], "module" => $row['module'], "exp" => $row['exp'], "level" => $row['level']));
}
mysql_free_result($result);

mysql_close($conn);

echo json_encode(array("status" => "ok", "modules" => $modules, "exp" => $exp));

?>