#!/bin/bash
# 1. Correct container name (from mongo0 to mongo1)
TARGET_CONTAINER="mongo1"

# 2. Correct URI
# Using network_mode: host on VM1, so we can use localhost or VM1's IP.
MONGO_URI="mongodb://127.0.0.1:27017/lsmsdb?replicaSet=rs0"

echo "-----------------------------------"
echo "Waiting 10 seconds for Replica Set election to stabilize..."
sleep 10

import_collection() {
    local COLLECTION=$1
    local FILE_PATH="DataSet/${COLLECTION}_collection.json"
    local TARGET_PATH="/tmp/${COLLECTION}_collection.json"

    echo ""
    echo "-----------------------------------"
    echo "Processing collection: $COLLECTION"

    # Check local file existence
    if [ ! -f "$FILE_PATH" ]; then
        echo "ERROR: File $FILE_PATH does not exist locally!"
        return
    fi

    # Copy to the correct container
    sudo docker cp "$FILE_PATH" $TARGET_CONTAINER:$TARGET_PATH

    # Execute mongoimport in the correct container
    sudo docker exec $TARGET_CONTAINER mongoimport --uri "$MONGO_URI" --collection "$COLLECTION" --file $TARGET_PATH --jsonArray --mode upsert 
    
    echo "$COLLECTION processed!"
}

# --- EXECUTION IMPORT ---

import_collection "users"
import_collection "cars"
import_collection "analytics"
import_collection "bookings"
import_collection "rides"

echo "-----------------------------------"
echo "Mongo setup completed."

# --- NEO4J ---
echo ""
echo "-----------------------------------"
echo "Populating neo4j db"

# 3. Correct Neo4j container name (from neo4j_db to neo4j)
NEO4J_CONTAINER="neo4j"

if [ -f "DataSet/routes.csv" ]; then
    # Copy to the correct container
    sudo docker cp DataSet/routes.csv $NEO4J_CONTAINER:/var/lib/neo4j/import/
    
    # Cypher Import
    # Note: Removed user/pass because docker-compose has NEO4J_AUTH=none
    cat DataSet/graph_db_import_data_query.cypher | sudo docker exec -i $NEO4J_CONTAINER cypher-shell
    
    echo "Graph db successfully populated"
else
    echo "ERROR: File DataSet/routes.csv not found!"
fi
