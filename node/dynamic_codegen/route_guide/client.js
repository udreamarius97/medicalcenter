'use strict';
var PROTO_PATH = __dirname +'/../../../node/protos/meds.proto'

var http=require('http');
var async = require('async');
var fs = require('fs');
var parseArgs = require('minimist');
var path = require('path');
var _ = require('lodash');
var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
  PROTO_PATH,
  {keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
  });
var routeguide = grpc.loadPackageDefinition(packageDefinition).routeguide;
var client = new routeguide.RouteGuide('localhost:50051',
  grpc.credentials.createInsecure());

http.createServer(function(request, response){
  response.writeHead(200, {'Content-Type': 'text/html'});
  var myStream=fs.createReadStream(__dirname + '/index.html','utf8');
  myStream.pipe(response);
  var date2=new Date();
  if(date2.getHours()>8 && date2.getHours()<10){
    medDim.forEach(function(med){
      response.write(med.numeMed + ' '+med.tratment+ ' '+med.dozaj+';');
    })
  }
  else
  if(date2.getHours()>14 && date2.getHours()<16){
    medAmiaza.forEach(function(med){
      response.write(med.numeMed + ' '+med.tratment+ ' '+med.dozaj+';');
    })
  }
  else
  if(date2.getHours()>20 && date2.getHours()<22){
    medSeara.forEach(function(med){
      response.write(med.numeMed + ' '+med.tratment+ ' '+med.dozaj+';');
    })
  }
  else
  response.write("Nu sunt medicamente de luat");
}).listen(3000,'127.0.0.1');

var meds=[];
var medDim=[];
var medAmiaza=[];
var medSeara=[];

function runListFeatures(callback) {
  var id_patient = 15;

  console.log('Looking for medication');
  var call = client.listMeds(id_patient);
   call.on('data', function (feature) {
    console.log('Found feature ' +
      feature.name_med + ', ' +
      feature.dozaj + ', ' +
      feature.tratment);
    meds.push(feature);
  });
  call.on('end', callback);

}

function populate() {

  meds.forEach(function(med){
    var res = med.tratment.split('-');
    medDim.push({numeMed: med.name_med, dozaj: med.dozaj, tratment: res[0]});
    medAmiaza.push({numeMed: med.name_med, dozaj: med.dozaj, tratment: res[1]});
    medSeara.push({numeMed: med.name_med, dozaj: med.dozaj, tratment: res[2]});
  },{});

  console.log(medDim);
}
function main() {
  async.series([
    runListFeatures,
    populate
  ]);
}


setInterval(function(){
  var date=new Date();
  if (require.main === module) {
  if(date.getHours()===1 && date.getMinutes()===35 )
    main();
}},60000)

   if (require.main === module) {

    main();
}

exports.runListFeatures = runListFeatures;

//node ./dynamic_codegen/route_guide/server.js
//node ./dynamic_codegen/route_guide/client.js
