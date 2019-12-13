console.log(process.env)
let api = process.env.API_PATH;

export default {
    GET_TBBLOCK_CHAIN_INFO: `${api}/home/blockChainInfo`,  //get blockChain overview

    GET_TXNBY_LASTFOURTEENDAY: `${api}/home/txnLately`, //The amount of transaction information to obtain the current group for a certain period of time

    GET_TBBLOCK_INFO: `${api}/block/blockList`,  //get blocks

    GET_TBTRASACTION_INFO: `${api}/transaction/transactionList`,  //Get a list of transaction information under the current group

    GET_TBNODE_COMNECTION: `${api}/node/nodeList`,  //get a lots of nodes

    GET_TBTRASACTION_BY_PKHASH: `${api}/transaction/transactionByHash`,  //Obtain transaction information based on transaction hash

    GET_TBTRASACTION__RECEIPT_BYPKHASH: `${api}/transaction/receiptByHash`, //Obtain transaction receipt information based on transaction hash

    ADD_NODECONFIG_ROW: `${api}/node/add`, //add a node

    EDIT_NODECONFIG_ROW: `${api}/node/update`, //edit node

    DELETE_NODECONFIG_ROW: `${api}/node/deleteById`,   //delete node

    // GET_TBPENDING_TRANSACTION_BYPKHASH: `${api}/pendingTransaction/transactionByHash`,//Obtain transaction information based on transaction hash

    GET_TBBLOCK_BYBLOCKHASH: `${api}/block/blockByHash`,  //Obtaining Block Information Based on Block Hash

    ADD_CONTRACT: `${api}/contract/add`,  //save a contract

    GET_CONTRACT_LIST: `${api}/contract/contractList`, //get a list of contracts

    DELETE_CONTRACT: `${api}/contract/deleteById`, //delete a contract

    UPDATE_CONTRACT: `${api}/contract/update`, //update a contract

    ADD_GROUP: `${api}/group/add`, //add a group

    GET_GROUP_LIST: `${api}/group/groupList`, //get groups

    DELETE_GROUP: `${api}/group/deleteById`, //get groups

    GET_BYTECODE: `${api}/transaction/code`, //Get contract binary code

    GET_TRANSACTION_ANALYZEDATA: `${api}/transaction/analyzeData`, //Get the data needed for transaction parsing based on the transaction hash call

    UPLOAD: `${api}/contract/addBatch`,

    ADDFUNCTION: `${api}/contract/addFunction`,

    GET_ABI_FUNCTION: `${api}/contract/function`,

    GET_ABI: `${api}/contract/abi`,

    GET_ENCRYPTT_TYPE: `${api}/node/getEncryptType`
}
