
var PROTO_PATH = __dirname +'/../../../RPC_Node/protos/meds.proto';

var fs = require('fs');
var parseArgs = require('minimist');
var path = require('path');
var _ = require('lodash');
var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var fetch = require('isomorphic-fetch');
var packageDefinition = protoLoader.loadSync(
  PROTO_PATH,
  {keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
  });
var routeguide = grpc.loadPackageDefinition(packageDefinition).routeguide;

var meds_list=[];

async function listMeds(call) {
  let promise = getDoctorPatients(call.id);
  await promise.then(function (response) {
    meds_list = response;
  }).catch(error => {
    if (error.status === 500) {
      notification.error({
        message: 'Server error',
      });
    }
  });
  console.log(meds_list);
  _.each(meds_list, function (med) {
    call.write(med);
  });
  call.end();
}

const request = (options) => {

  options = Object.assign({}, {}, options);

  return fetch(options.url, options)
    .then(response =>
      response.json().then(json => {
        if(!response.ok) {
          return Promise.reject(json);
        }
        return json;
      })
    );
};

function getDoctorPatients() {

  return request({
    url: "http://localhost:8081/api/auth/medication",
    method: 'POST',
    body: '9'
  });
}
function getServer() {
  var server = new grpc.Server();
  server.addProtoService(routeguide.RouteGuide.service, {
    listMeds: listMeds
  });
  return server;
}

if (require.main === module) {
  // If this is run as a script, start a server on an unused port
  var routeServer = getServer();
  routeServer.bind('0.0.0.0:50051', grpc.ServerCredentials.createInsecure());
  routeServer.start();
}
exports.getServer = getServer;
