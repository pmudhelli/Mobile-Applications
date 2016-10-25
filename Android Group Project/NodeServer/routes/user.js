var http = require('http');
var URL = require('url');
var MongoClient = require('mongodb').MongoClient;
var Server = require('mongodb').Server;
var jQuery=require('./jquery');
var getConnection = require('./connection.js');

/*
 * GET users listing.
 */

exports.list = function(req, res){
  res.send("respond with a resource");
};

exports.home=function(req,res){
	
	res.render('Home');
};

exports.getPendingEvents=function(req,res){

	// FOR CLoud based instances
	 
	//MongoClient.connect("mongodb://cmpe-277:cmpe-277@ds021751.mlab.com:21751/cmpe277", function(err, db) {
	console.log("Entered");    
	var userid=req.param("userid");	   
	console.log("userid:"+userid);
	var result = {
		    validlogin: "true"
	};
	 getConnection(function(err,db)
			    {
			if(err) { return console.dir(err); }
			
			  var collection = db.collection('Events');
			  collection.aggregate([{$match:{ users: { $elemMatch: { "user_id": userid ,"user_status":0}}}},{$project:{event_id:1,event_name:1,_id:0,address:1,start_date_time:1,end_date_time:1}}]).toArray(function(err, docs) {
			  //collection.find({ "users.user_id": userid ,"users.user_status":0 },{event_id:1,event_name:1,_id:0,address:1,start_date_time:1}).toArray(function(err, docs) {			 
	            	 if(err){
	            		 console.log(err);
	            	 }
	            	 else{
	            		 console.log("sending to parser before");
	          			console.log(JSON.stringify(docs));    		      
	            	 res.send(JSON.stringify(docs));
	            	 } });
			  
			    });  
};

exports.acceptRequest=function(req,res){

	// FOR CLoud based instances
	 
	//MongoClient.connect("mongodb://cmpe-277:cmpe-277@ds021751.mlab.com:21751/cmpe277", function(err, db) {
	console.log("Entered");    
	var eventid=req.body.eventid;
	var userid=req.body.userid;
	var username = req.body.username;
	//eventid =1;
	//userid =1;
	console.log("req"+req);
	var result = {
		    approved: "true"
	};
	 getConnection(function(err,db)
			    {
			if(err) { return console.dir(err); }
			
			  var collection = db.collection('Events');
			  collection.update( { users: { $elemMatch: { "user_id": userid}},"event_id": eventid },
					   { $set: { "users.$.user_status" : 1,"users.$.user_name":username } }
			  );
			              			 //assert.equal(2, docs.length);
			             			//  console.log(JSON.stringify(docs));

	          			console.log(JSON.stringify(result));    		      
	            	 res.send(result);

			  
			    });  
};

exports.rejectRequest=function(req,res){

	// FOR CLoud based instances
	 
	//MongoClient.connect("mongodb://cmpe-277:cmpe-277@ds021751.mlab.com:21751/cmpe277", function(err, db) {
	console.log("Entered");    
	var eventid=req.body.eventid;
	var userid=req.body.userid;
	var result = {
		    rejected: "true"
	};
	 getConnection(function(err,db)
			    {
			if(err) { return console.dir(err); }
			
			  var collection = db.collection('Events');
			  collection.update( { users: { $elemMatch: { user_id: userid}},event_id:eventid },
					   { $set: { "users.$.user_status" : 2 } }
			  );
	            		 console.log("sending to parser before");
	          			console.log(JSON.stringify(result));    		      
	            	 res.send(result);
	            	 
			  
			    });  
};

exports.createEvent = function(req,res){
	 
	 // FOR CLoud based instances
	 //MongoClient.connect(“mongodb://admin:admin@ds045011.mongolab.com:45011/mydb", function(err, db) {
	 console.log("Inside createEvent");
	 var startDate = req.param("startDate");
	 var startTime = req.param("startTime");
	 var endDate = req.param("endDate");
	 var endTime = req.param("endTime");
	 var location = req.param("location");
	 var eventName = req.param("eventName");
	 var userId = req.param("userId");
	 var eventId = req.param("eventId");
	 var attendees = req.param("attendees");
	 var userName = req.param("userName");
	   
	 var att = [];
	 var a = {"user_id": userId, "user_name": userName, "latitude": "", "longitude": "", "user_status": 1};
	 att.push(a);
	  for(var i in attendees) {
	    var b = {"user_id": attendees[i], "user_name": "", "latitude": "", "longitude": "", "user_status": 0}
	   att.push(b);
	 }
	 
	 var data = {
	    event_id: eventId,
	    event_name: eventName,
	    address: location,
	    start_date_time: startDate+" "+startTime,
	    end_date_time: endDate+" "+endTime,
	    users: att
	   }
	 
	 //insertData(data);
	 getConnection(function(err,db) {
			if (err) { 
					console.dir(err); 
				}			
			  var collection = db.collection('Events');
			  console.log("before insertion");
			  collection.insert(data, function(err, docs) {
	            	 if(err) {
	            		 console.log(err);
	            	 } else {
	            		 	console.log("Done"); 
		          			res.send({"status":"Success"});
	            	   } 
	           });
		});
};

exports.getApprovedUserEvents = function(req,res){
	 
	 // FOR CLoud based instances
	 //MongoClient.connect(“mongodb://admin:admin@ds045011.mongolab.com:45011/mydb", function(err, db) {
	 console.log("Inside getUserEvents");
	  
	 var userId = req.param("userId");
	 console.log(userId);
	 
	   getConnection(function(err,db)
	       {
	   if(err) { return console.dir(err); }
	    
	     var collection = db.collection('Events');
	 
	  collection.aggregate([{$match:{ users: { $elemMatch: { "user_id": userId, "user_status": 1}}}},{$project:{event_id:1,event_name:1,_id:0,address:1,start_date_time:1,end_date_time:1,users:1}}]).toArray(function(err, docs) {
	 
	                      
	               if(err){
	                console.log(err);
	               } else {
	                console.log("getUserEvents Output:");
	              console.log(docs);
	                res.send(docs);
	               }
	             });
	      
	    });
};
	
exports.getUserLocation=function(req,res){
	
	//console.log(JSON.stringify(req));
	var eventId=req.param("event_id");
	//eventID
	//eventId=3;
	
	console.log("Event Id entered is "+eventId);
	 getConnection(function(err,db)
			    {
			if(err) { return console.dir(err); }
			
			  var collection = db.collection('Events');
			  collection.aggregate([    
			                        	 {$match: {"event_id":eventId }}            
	             ]).toArray(function(err, docs) {
	            	 if(err){
	            		 console.log(err);
	            	 }
	            	 else{
	            		 console.log("sending to parser before");
	          			if(docs.length==0){
	          				res.send({"users":"no data"});
	          			}
	          			else{
	            		 console.log(JSON.stringify(docs[0].users)); 
	          			res.send({"users":docs[0].users});
	          			}
	            	 } });
			  
			    });
};


exports.updateCurrentLocation=function(req,res){
	
	//event_id,user_id,latitude, longitude
	var event_id=req.param("event_id");
	var user_id=req.param("user_id");
	var lat=req.param("latitude");
	var long=req.param("longitude");
	
	//event_id=2;
	//	user_id=1;
	//lat="21.32423";
	//long="23.87391";
	
	console.log("Event Id entered is "+event_id+" userID "+user_id+" lat "+lat);
	getConnection(function(err,db)
			    {
			if(err) { return console.dir(err); }
			
			  var collection = db.collection('Events');
			  collection.update(
					  {"event_id":event_id,"users.user_id":user_id},
					  {$set:{ "users.$.latitude": lat, "users.$.longitude": long }},
				      function(err, results) {
				        if(err){
		            		 console.log(err);
		            	 }
		            	 else{
		            		 res.send({"status":"success"});
		            	 }
					    });
			    });
	
};

