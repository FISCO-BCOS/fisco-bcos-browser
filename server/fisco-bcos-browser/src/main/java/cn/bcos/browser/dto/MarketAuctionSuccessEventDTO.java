package cn.bcos.browser.dto;

import java.sql.Timestamp;

public class MarketAuctionSuccessEventDTO {
    private int blockNumber;
    private String blockHash;
    private String contractAddress;
    private String contractName;
    private long transactionIndex;
    private String transactionHash;
    private int eventIndex;
    private String transferFromAddress;
    private String transferFromID;
    private String transferFromName;
    private String transferFromGroup;
    private String transferToAddress;
    private String transferToID;
    private String transferToName;
    private String transferToGroup;
    private String warrantID;
    private String price;
    private String warrantDetail;
    private Timestamp blockTimestamp;
    
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getContractAddress() {
        return contractAddress;
    }
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public int getBlockNumber() {
        return blockNumber;
    }
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }
    public String getBlockHash() {
        return blockHash;
    }
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
    public long getTransactionIndex() {
        return transactionIndex;
    }
    public void setTransactionIndex(long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }
    public String getTransactionHash() {
        return transactionHash;
    }
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    public int getEventIndex() {
        return eventIndex;
    }
    public void setEventIndex(int eventIndex) {
        this.eventIndex = eventIndex;
    }
    public String getTransferFromAddress() {
        return transferFromAddress;
    }
    public void setTransferFromAddress(String transferFromAddress) {
        this.transferFromAddress = transferFromAddress;
    }
    public String getTransferFromID() {
        return transferFromID;
    }
    public void setTransferFromID(String transferFromID) {
        this.transferFromID = transferFromID;
    }
    public String getTransferFromName() {
        return transferFromName;
    }
    public void setTransferFromName(String transferFromName) {
        this.transferFromName = transferFromName;
    }
    public String getTransferFromGroup() {
        return transferFromGroup;
    }
    public void setTransferFromGroup(String transferFromGroup) {
        this.transferFromGroup = transferFromGroup;
    }
    public String getTransferToAddress() {
        return transferToAddress;
    }
    public void setTransferToAddress(String transferToAddress) {
        this.transferToAddress = transferToAddress;
    }
    public String getTransferToID() {
        return transferToID;
    }
    public void setTransferToID(String transferToID) {
        this.transferToID = transferToID;
    }
    public String getTransferToName() {
        return transferToName;
    }
    public void setTransferToName(String transferToName) {
        this.transferToName = transferToName;
    }
    public String getTransferToGroup() {
        return transferToGroup;
    }
    public void setTransferToGroup(String transferToGroup) {
        this.transferToGroup = transferToGroup;
    }
    public String getWarrantID() {
        return warrantID;
    }
    public void setWarrantID(String warrantID) {
        this.warrantID = warrantID;
    }
    public String getWarrantDetail() {
        return warrantDetail;
    }
    public void setWarrantDetail(String warrantDetail) {
        this.warrantDetail = warrantDetail;
    }
    public Timestamp getBlockTimestamp() {
        return blockTimestamp;
    }
    public void setBlockTimestamp(Timestamp blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }
   
}
