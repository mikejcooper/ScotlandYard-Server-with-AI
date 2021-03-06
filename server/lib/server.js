'use strict'
var net = require('net');
var PlayerServer = require('./player_server.js');
var GameServer   = require('./game_server.js');
var game_server;
var player_server;

/**
 * Constructor for the class. Needs to initialise the players server
 * and game server, create the list of colours and initialise a game id
 * @constructor
 */
function Server() {
    this.server = net.createServer(function() {})

    this.game_server = new GameServer();
    this.player_server = new PlayerServer();
    this.game_id = -1;
    this.colours = ['Black', 'Blue', 'Green', 'Red', 'White', 'Yellow'];

}

/**
 * Function that will start up the server. It should set the game_server
 * and the player_server to listen on the correct ports. It should also
 * connect up the events emitted by the player server so that the correct
 * information is passed onto the game server
 * @param player_port
 * @param game_port
 */
Server.prototype.start = function(player_port, game_port) {
    this.game_server.listen(game_port);
    this.player_server.listen(player_port);
    this.ServerEvents(game_port);
}


Server.prototype.ServerEvents = function (game_port) {
    var self = this;

    this.game_server.on('initialised', function(inital_game_id){
        self.game_id = inital_game_id;
    })
    this.player_server.on('register', function(player) {
        self.game_server.addPlayer(player, self.getNextColour(), self.gameId());
    })
    this.player_server.on('move', function(player, move_data) {
        self.game_server.makeMove(player,move_data);
    })
}


/**
 * Function to close down the player server and the game server
 */
Server.prototype.close = function() {
    this.player_server.close();
    this.game_server.close();

}


/**
 * Function to retrieve the id of the game being played
 * @returns {number|*}
 */
Server.prototype.gameId = function() {
    return this.game_id;
}


/**
 * Function to extract the next colour out of the arrays of colours
 * When a colour is extracted, that colour is removed from the list
 * @returns {*}
 */

Server.prototype.getNextColour = function() {
    var current_colour = this.colours[0];
    this.colours = this.colours.splice(1,1);
    return current_colour;
}


module.exports = Server;

