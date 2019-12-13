let web3Utils = require("web3-utils");
var gm = require('./SM2Sign')

let utils = web3Utils
utils.sha4 = function (value) {
    value = new Buffer(value);
    var _digstData = gm.sm3Digest(value);
    var digstData = new Buffer(_digstData, 'hex').toString('hex');
    console.log("SM3digstData:",_digstData);
    return digstData;
}
module.exports = utils

