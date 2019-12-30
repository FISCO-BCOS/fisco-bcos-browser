import utils from "./sm_sha"
let web3Abi = require("web3-eth-abi");
var _ = require('underscore');

let ethAbi = web3Abi;
console.log(utils)
ethAbi.smEncodeFunctionSignature = function (functionName) {
    if (_.isObject(functionName)) {
        functionName = utils._jsonInterfaceMethodToString(functionName);
    }

    return "0x" + utils.sha4(functionName).slice(0, 8);
}
ethAbi.smEncodeEventSignature = function (functionName) {
    if (_.isObject(functionName)) {
        functionName = utils._jsonInterfaceMethodToString(functionName);
    }

    return "0x" + utils.sha4(functionName);
};
ethAbi.smEncodeFunctionCall = function (jsonInterface, params) {
    return this.smEncodeFunctionSignature(jsonInterface) + this.encodeParameters(jsonInterface.inputs, params).replace('0x', '');
};
module.exports = ethAbi
