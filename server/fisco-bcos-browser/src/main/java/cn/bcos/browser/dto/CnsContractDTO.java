package cn.bcos.browser.dto;

public class CnsContractDTO {
    public String contractName;
    public String contractAddress;
    public String startBlock;
    public String endBlock;
    public String abi;
    public String code;
    public String bin;
    public String version;
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getContractAddress() {
        return contractAddress;
    }
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getStartBlock() {
        return startBlock;
    }
    public void setStartBlock(String startBlock) {
        this.startBlock = startBlock;
    }
    public String getEndBlock() {
        return endBlock;
    }
    public void setEndBlock(String endBlock) {
        this.endBlock = endBlock;
    }
    public String getAbi() {
        return abi;
    }
    public void setAbi(String abi) {
        this.abi = abi;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getBin() {
        return bin;
    }
    public void setBin(String bin) {
        this.bin = bin;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

}
