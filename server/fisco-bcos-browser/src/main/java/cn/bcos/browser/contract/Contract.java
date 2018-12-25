package cn.bcos.browser.contract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.FunctionReturnDecoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bcos.browser.service.GovernService;
import javafx.util.Pair;
public class Contract {
    private static Timestamp timestamp;
    private static String txFrom;
    public static final String WarrantTransferEvent = "{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"}";
    public static final String MarketAuctionSuccessEvent = "{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":true,\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"price\",\"type\":\"uint256\"}],\"name\":\"AuctionSuccess\",\"type\":\"event\"}";
    
    public static final String methodName = "eth_call";
    public static final String jsonRPCCNSformat = "{\"data\":{\"contract\":\"%s\",\"version\":\"\",\"func\":\"%s\",\"params\":%s}}";
    
    public static void setTimestamp(Timestamp timestamp) {
        Contract.timestamp = timestamp;
    }
    
    public static Timestamp getTimestamp() {
        return Contract.timestamp;
    }
    public static String getTxFrom() {
        return txFrom;
    }

    public static void setTxFrom(String txFrom) {
        Contract.txFrom = txFrom;
    }
    
    public static List<String> cnsCall(String contractName, String functionName, String[] params, String blockNumber)
    {
        //1. 拼jsonrpc字符串作为eth_call的第一个参数
        String jsonRPCstr = String.format(jsonRPCCNSformat, 
                                            contractName, 
                                            functionName,
                                            params==null?"[]":JSON.toJSONString(params));
        
        //2. 和第二个参数blockNumber一起组合成参数数组
        Object[] obejcts = new Object[] {JSONObject.parseObject(jsonRPCstr),blockNumber};
        
        //3. 调用
        String ret = (String)GovernService.getInfoByMethod(methodName,obejcts);
        return JSONArray.parseArray(ret,String.class);
    }
    
    public static List<EventResult> parseTransactionReceipt(TransactionReceipt receipt, AbiDefinition ABI, String address) {
        if(ABI == null) return null;
        List<Log> logs = receipt.getLogs();
        List<EventResult> eventResults = new ArrayList<EventResult>();
        int index = 0;
        for (Log log : logs) {
            if(address.equals(log.getAddress()) == false) {
                System.out.println("log address: "+log.getAddress());
                //地址不匹配,下一个
                continue;
            }
            List<String> topics = log.getTopics();
            if (topics.size() < 1) // error
                continue;
            EventResult eventResult = getEvent(log, ABI);
            if (eventResult != null) {
                eventResult.setIndex(++index);
                eventResults.add(eventResult);
            }
        }
        return eventResults;
    }
    
    private static EventResult getEvent(Log log, AbiDefinition eventAbi) {
        // constructs events
        List<Pair<Integer, Integer>> paramsIndex = new ArrayList<Pair<Integer, Integer>>();
        List<TypeReference<?>> indexedParams = new ArrayList<TypeReference<?>>();
        List<TypeReference<?>> unindexedParams = new ArrayList<TypeReference<?>>();
        List<NamedType> params = eventAbi.getInputs();
        for (int i = 0; i < params.size(); ++i) {
            NamedType param = params.get(i);
            if (param.isIndexed()) {
                paramsIndex.add(new Pair<Integer, Integer>(1, indexedParams.size()));
                indexedParams.add(BuildSolidityParams.getType(param.getType()));
            } else {
                paramsIndex.add(new Pair<Integer, Integer>(0, unindexedParams.size()));
                unindexedParams.add(BuildSolidityParams.getType(param.getType()));
            }
        }
        Event event = new Event(eventAbi.getName(), indexedParams, unindexedParams);

        // parse logs
        EventValues eventValues = extractEventParameters(event, log);
        if (eventValues == null) { // parse failed?
            return null;
        }

        // constructs results
        EventResult eventResult = new EventResult(eventAbi.getName());
        for (int i = 0; i < params.size(); ++i) {
            eventResult.addParam(params.get(i));
            Pair<Integer, Integer> index = paramsIndex.get(i);
            if (index.getKey() > 0) {
                eventResult.addValue(eventValues.getIndexedValues().get(index.getValue()));
            } else {
                eventResult.addValue(eventValues.getNonIndexedValues().get(index.getValue()));
            }
        }
        return eventResult;
    }
    
    private static EventValues extractEventParameters(Event event, Log log) {

        List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }
  
}
