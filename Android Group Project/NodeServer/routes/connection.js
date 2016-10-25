var MongoClient = require('mongodb').MongoClient;

var db_singleton = null;

var getConnection= function getConnection(callback)
{
    if (db_singleton)
    {
        callback(null,db_singleton);
    }
    else
    {
           //placeholder: modify this-should come from a configuration source
    	// mongodb://cmpe-277:cmpe-277@ds021751.mlab.com:21751/cmpe277
    		 //mongodb://localhost:27017/AndroidDB
        var connURL = "mongodb://cmpe-277:cmpe-277@ds021751.mlab.com:21751/cmpe277"; 
       // var connURL = "mongodb://localhost:27017/AndroidDB";
        MongoClient.connect(connURL,function(err,db){

            if(err)
                console.log("Error creating new connection "+err);
            else
            {
                db_singleton=db;    
                console.log("created new connection");

            }
            callback(err,db_singleton);
            return;
        });
    }
}

module.exports = getConnection;