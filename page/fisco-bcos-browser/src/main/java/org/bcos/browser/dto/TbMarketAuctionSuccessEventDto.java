package org.bcos.browser.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TbMarketAuctionSuccessEventDto implements Serializable{
    private static final long serialVersionUID = 1743006021812704180L;
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
    private String transferToAddress;
    private String transferToID;
    private String transferToName;
    private String warrantID;
    private String price;
    private String warrantDetail;
    private Timestamp blockTimestamp;
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
    public String getWarrantID() {
        return warrantID;
    }
    public void setWarrantID(String warrantID) {
        this.warrantID = warrantID;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
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
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"blockHash\":\"")
                .append(blockHash).append('\"');
        sb.append(",\"contractAddress\":\"")
                .append(contractAddress).append('\"');
        sb.append(",\"contractName\":\"")
                .append(contractName).append('\"');
        sb.append(",\"transactionIndex\":")
                .append(transactionIndex);
        sb.append(",\"transactionHash\":")
                .append(transactionHash);
        sb.append(",\"eventIndex\":")
                .append(eventIndex);
        sb.append(",\"transferFromAddress\":")
                .append(transferFromAddress);
        sb.append(",\"transferFromID\":")
                .append(transferFromID);
        sb.append(",\"transferToAddress\":\"")
                .append(transferToAddress).append('\"');
        sb.append(",\"transferToID\":")
                .append(transferToID);
        sb.append(",\"transferToName\":\"")
                .append(transferToName).append('\"');
        sb.append(",\"warrantID\":\"")
                .append(warrantID).append('\"');
        sb.append(",\"price\":")
                .append(price);
        sb.append(",\"warrantDetail\":")
                .append(warrantDetail);
        sb.append(",\"blockTimestamp\":")
                .append(blockTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
