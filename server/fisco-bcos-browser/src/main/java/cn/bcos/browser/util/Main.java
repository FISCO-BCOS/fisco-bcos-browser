package cn.bcos.browser.util;

import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.slf4j.Logger;

import cn.bcos.browser.contract.BuildSolidityParams;
import cn.bcos.browser.service.GovernService;

public class Main {
    
    
    public static void main(String[] args) throws Throwable {
        String abi = "{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"}";
        AbiDefinition ABI = BuildSolidityParams.toABI(abi);
        System.out.println(ABI.getName());
        System.out.println(ABI.getType());
    }
}
