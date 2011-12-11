
DROP TABLE IF EXISTS ExpCraftTable;

CREATE TABLE ExpCraftTable (
 id BIGINT NOT NULL AUTO_INCREMENT,
 player VARCHAR(100),
 module VARCHAR(5),
 exp NUMERIC(8,2),
 PRIMARY KEY (id)
);

INSERT INTO ExpCraftTable (player,module,exp)
SELECT
	 name
	,'W'
	,WoodCuttingExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'De'
	,DefenceExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'Dx'
	,DexterityExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'Sc'
	,ScavengerExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'Fm'
	,FarmingExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'D'
	,DiggingExp
  FROM ExperienceTable
UNION ALL
SELECT
	 name
	,'M'
	,MiningExp
  FROM ExperienceTable;
