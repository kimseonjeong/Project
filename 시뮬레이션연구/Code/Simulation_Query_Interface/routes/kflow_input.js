var express = require('express');
var router = express.Router();
var client = require('mongodb').MongoClient;
var fs = require('fs');

/* GET home page. */
router.get('/', function(req, res, next) {

	//input 매개변수 array
	var inputarray = new Array();

	//string 
	var total = '';

	//file name
	var filename = 'kflow_result.csv';

	//title name
	var name = '';

	//metadata database connect
	client.connect('mongodb://203.153.146.39/metadata', function(err,db){

		//kflow collection connect
		db.collection('kflow').find().toArray(function(err, result){

			//simulation name
			name = result[0].simulator.name;

			//input 매개변수 array assign
			for(var i in result[0].input){

				inputarray[i] = JSON.stringify(result[0].input[i].name);
				inputarray[i] = inputarray[i].substring(1,inputarray[i].length-1);
				//맨뒤에꺼 하나 뺌
			}
			
		})

	})

	//simulation database connect
	client.connect('mongodb://203.153.146.39/simulation', function(err,db){
		
		//kflow collection connect
		db.collection('kflow').find().toArray(function(err, result){

			//string 
			//file 상단 
			total += "thickness,Umach,AOA,RE,Cl,Cdt,Cdp,Cdf,Cm\n";

			//변수를 받아옴
			for(var i in result){
				
				//thickness 
				total += result[i].input.thickness + ",";

				//Umach
				total += result[i].input.Umach + ",";

				//AOA
				total += result[i].input.AOA + ",";

				//RE
				total += result[i].input.RE + ",";

				//Cl
				total += result[i].output.aerodynamic.Cl + ",";

				//Cdt
				total += result[i].output.aerodynamic.Cdt + ",";

				//Cdp
				total += result[i].output.aerodynamic.Cdp + ",";

				//Cdf
				total += result[i].output.aerodynamic.Cdf + ",";
				
				//Cm
				total += result[i].output.aerodynamic.Cm;
				
				//줄바꿈
				total += "\n";
				
			}

			//csv파일로 출력
			fs.writeFileSync('./kflow_result.csv', total, 'utf-8');

			//title, name, inputarray, filename render
			res.render('kflow_input',{ title: '시뮬레이션 서비스 시스템', name:name, items:inputarray, filename:filename});

		})

	})
});


module.exports = router;
