var express = require('express');
var router = express.Router();
var fs = require('fs');
var exec = require('child_process').exec, child;
var client = require('mongodb').MongoClient;

//GET
router.get('/', function(req, res, next) {

	//name
	var name = '';

	//outputarray
	var outputarray = new Array();

	//metadata database connect
	client.connect('mongodb://203.153.146.39/metadata', function(err,db){
			
		//kflow collection connect
		db.collection("kflow").find().toArray(function(err, result){

			//simulator name assign
			name = result[0].simulator.name;		

			//ourputarray
			for(var i in result[0].output){

				outputarray[i] = JSON.stringify(result[0].output[i].name);
				outputarray[i] = outputarray[i].substring(1,outputarray[i].length-1);
				//1개를 뺌

			}
		})
		
	})

	//jar 파일 연결
	//predictor connect
	child = exec('java -jar C:\\Coding\\Simulation_DataSearch\\Predict_Test_fat.jar', function(err,stdout,stderr){
		
		//error o
		if(err)
			console.log(err.code);
		//console.log(stdout);

		//stdout
		var std = stdout;

		//standard array
		var standard = new Array();
		
		//줄바꿈으로 split
		standard = std.split("\n");
		//console.log(standard);

		//substring
		for(var i=0; i<standard.length - 2; i++)
			standard[i] = standard[i].substring(3);

		//outputarray, standard, name render
		res.render('kflow_test',{outputarray:outputarray, standard:standard, name:name});
	})

});

module.exports = router;
