/**
 * Created by Adam on 2015-06-12.
 */
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('data/imagedb');

var name, address, latitude, longitude, rating;

function insertImage(name, address, latitude, longitude)
{
    this.name = name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;

    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("INSERT INTO image (Name, Address, Rating, Latitude, Longitude VALUES (?)", name, address, 0.0, latitude, longitude);
    });

    db.close();
}

function updateImage(name, address, rating, latitude, longitude)
{
    this.name = name;
    this.address = address;
    this.rating = rating;
    this.latitude = latitude;
    this.longitude = longitude;

    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("UPDATE image SET Address = ", address, ", Rating = ", rating, ", Latitude = ", latitude, ", Longitude = ", longitude, "WHERE Name = ", name);
    });

    db.close();
}

function deleteImage(name)
{
    this.name = name;

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
    this.name = name;
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("SELECT * FROM image WHERE name = ", name);
        // TODO wyswietlanie krotki
    })
    db.close();
}

function getImageByName(address)
{
    this.address = address;
    db.serialize(function() {
        db.run("CREATE TABLE IF NOT EXISTS image (Name TEXT NOT NULL, Address TEXT NOT NULL, Rating FLOAT DEFAULT 0.0, Latitude DOUBLE, Longitude DOUBLE)");

        db.run("SELECT * FROM image WHERE address = ", address);
        // TODO wyswietlanie krotki
    })
    db.close();
}
