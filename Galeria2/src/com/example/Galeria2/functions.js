var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('data/imagedb');

function insertImage(name, address)
{

    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("INSERT INTO image (Name, Address, Rating, Latitude, Longitude VALUES (?)", name, address, 0.0);
    });

    db.close();
}

function updateImage(name, address, rating, latitude, longitude)
{
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("UPDATE image SET Address = ", address, ", Rating = ", rating, ", Latitude = ", latitude, ", Longitude = ", longitude, "WHERE Name = ", name);
    });

    db.close();
}

function deleteImage(name)
{
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("DELETE FROM image WHERE Name = ", name);
        console.log("Image ", name, " deleted!");
    });

    db.close();
}

function getAllImages()
{
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("SELECT * FROM image");

    });

    db.close();
}

function getImageByName(name)
{
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("SELECT * FROM image WHERE name = ", name);

    })
    db.close();
}

function getImageByAddress(address)
{
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("SELECT * FROM image WHERE address = ", address);

    })
    db.close();
}

