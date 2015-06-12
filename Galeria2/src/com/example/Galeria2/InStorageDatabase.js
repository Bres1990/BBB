/**
 * Created by Adam on 2015-06-12.
 */
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('data/imagedb');

db.serialize(function() {
    db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");
    console.log("Database created!");
    /*db.run("INSERT INTO image (runtime) VALUES (?)", new Date().getTime());

    db.each("SELECT * FROM demo", function(err, row) {
        console.log("This app was run at " + row.runtime);
    });*/
});

db.close();
