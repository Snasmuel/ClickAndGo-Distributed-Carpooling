LOAD CSV WITH HEADERS FROM 'file:///routes.csv' AS row
MERGE (p:Point {id: row.lat + "_" + row.lon})
ON CREATE SET 
    p.lat = toFloat(row.lat), 
    p.lon = toFloat(row.lon)
FOREACH (_ IN CASE WHEN row.type = 'Location' THEN [1] ELSE [] END | 
    SET p:Location, p.name = row.name)
FOREACH (_ IN CASE WHEN row.type = 'Stop' THEN [1] ELSE [] END | 
    SET p:Stop);

LOAD CSV WITH HEADERS FROM 'file:///routes.csv' AS row
WITH row WHERE row.prev_lat IS NOT NULL
MATCH (curr:Point {id: row.lat + "_" + row.lon})
MATCH (prev:Point {id: row.prev_lat + "_" + row.prev_lon})
MERGE (prev)-[r:NEXT_STOP {route_id: row.route_id}]->(curr)
ON CREATE SET 
    r.distance = toFloat(row.dist_from_prev), 
    r.sequence = toInteger(row.sequence);

MATCH (n:Point)-[r:NEXT_STOP]->(n) 
DELETE r;
