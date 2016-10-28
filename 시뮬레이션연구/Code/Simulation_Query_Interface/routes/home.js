var express = require('express');
var router = express.Router();
var client = require('mongodb').MongoClient;

/* GET home page. */
router.get('/', function(req, res, next) {

	//매개변수 array
	var array = new Array();

	//metadata접속
	client.connect('mongodb://203.153.146.39/metadata', function(err,db){

		//kflow collection connect
		db.collection('kflow').find().nextObject(function(err, result){ 

			//array push
			array.push(result.simulator.name); 

		})

		//simulation2 collection connect
		db.collection('simulation2').find().nextObject(function(err,result){ 

			//array push
			array.push(result.simulator.name); 

		})

		//simulation3 collection connect
		db.collection('simulation3').find().nextObject(function(err,result){

			//array push
			array.push(result.simulator.name); 

			//render title, array
			res.render('home', { title: '시뮬레이션 서비스 시스템' ,array:array});
			
		})		

	})
	
  	
});

module.exports = router;
