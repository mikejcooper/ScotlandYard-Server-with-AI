'use strict';
var net = require('net');
var events = require('events');
var util = require('util');


function PlayerServer() {
    //reference PlayerSever
    var player_server = this;
    //Make Object an EventEmitter ('requiring' events)
    events.EventEmitter.call(player_server);

    this.server = net.createServer(function(player) {


        //'on' method expects two arguments (name,function) - Creates event to be emitted
        //Decode data from input during initialisation.
        player.on('data', function(data) {
            var player_data = JSON.parse(data);
            var student_id = player_data.student_id;

            if (player_data.type == 'REGISTER') {
                player_server.on('register', function(){});
                player_server.emit('register', player, student_id);

            }
            if (player_data.type == 'MOVE') {
                player_server.on('move', function () {});
                player_server.emit('move', player, player_data);
            }
        });
    });
}

util.inherits(PlayerServer, events.EventEmitter);


PlayerServer.prototype.onInput = function(player, data) {
    //console.log('[Fuction called - onInput]');
};

PlayerServer.prototype.listen = function(port) {
    this.server.listen(port);
   // console.log('[Fuction called - listen]');

};


PlayerServer.prototype.close = function() {
    this.server.close();
   // console.log('[Fuction called - close]');
};


module.exports = PlayerServer;