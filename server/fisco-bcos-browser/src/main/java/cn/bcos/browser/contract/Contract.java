package cn.bcos.browser.contract;
import static org.bcos.web3j.abi.Utils.convert;

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
import com.alibaba.fastjson.JSONObject;

import cn.bcos.browser.service.GovernService;
import javafx.util.Pair;
public class Contract {
    //function
    //地址、某个方法的abi
    public static final String[] Ok_get = {
                                           "0x15a4ed442133f26e69d14ff85e550c90906449ef",
                                           "{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"}",
                                           };
    
    //测试数据
    public static final String[] TestEvent_index = {
            "0xba620015e3bdbebe06fc505a3e0e5d344baead0c",
            "{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"int256\"}],\"name\":\"index\",\"type\":\"event\"}"
            };
    
    public static final String[] WarrantEvent_transfer = TestEvent_index;
 
    public static final String methodName = "eth_call";
    public static final String jsonRPCformat =  "{\"from\":\"0x562583ff5f147efa07b95de17ea516bb53bc0d7b\",\"to\":\"%s\",\"data\":\"%s\",\"type\":\"0x0\",\"version\":\"0x1\"}";
    public static List<Type> call(String[] contract, String blockNumber)
    {
        //1. abi字符串转换为abi结构体
        AbiDefinition ABI = BuildSolidityParams.toABI(Ok_get[1]);
        //2. 拼jsonrpc字符串作为eth_call的参数
        String jsonRPCstr = String.format(jsonRPCformat, Ok_get[0], BuildSolidityParams.buildMethodId(ABI));
        //3. jsonrcp、blockNumber作为eth_call的参数
        Object[] params = new Object[] {JSONObject.parseObject(jsonRPCstr),blockNumber};
        //4. 调用eth_call
        String ret = (String)GovernService.getInfoByMethod(methodName,params);
        //5. 解析结果
        return FunctionReturnDecoder.decode(ret, convert(BuildSolidityParams.buildReturnParams(ABI)));
    }
    
    public static List<Type> call(String[] contract){
        return call(contract, "latest");
    }
    
    public static List<EventResult> parseTransactionReceipt(TransactionReceipt receipt, AbiDefinition ABI) {
        if(ABI == null) return null;
        List<Log> logs = receipt.getLogs();
        List<EventResult> eventResults = new ArrayList<EventResult>();
        int index = 0;
        for (Log log : logs) {
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
    
    public static EventResult getEvent(Log log, AbiDefinition eventAbi) {
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
    
    
 
    
    //以下是测试数据和测试函数
    public static final String Tx = "0xe2afc967dfec291c123357f7e87112f329c55e490737bc306b83466992e83f26";
    public static final String event = "{\"blockHash\":\"0x5fe8a43c2b089db516ff313898c534d4721e4efa4992225d40e909f92310d420\",\"address\":\"0xba620015e3bdbebe06fc505a3e0e5d344baead0c\",\"logIndex\":0,\"data\":\"0x0000000000000000000000000000000000000000000000000000000000000002\",\"topics\":[\"0xc06b1c220dfbf37021a9314f7d6f7d8c5e7264ea69176b509e01cbea454f7815\"],\"blockNumber\":241,\"transactionIndex\":0,\"type\":\"mined\",\"transactionHash\":\"0xe2afc967dfec291c123357f7e87112f329c55e490737bc306b83466992e83f26\",\"polarity\":false}";
    public static void TestParseEvent() {
        Object[] params = new Object[] {Tx};
        Object o  = GovernService.getInfoByMethod("eth_getTransactionReceipt",params);
        String ret = JSON.toJSONString(o);
        System.out.println(ret);
        TransactionReceipt receipt = JSONObject.parseObject(ret,TransactionReceipt.class);
        AbiDefinition ABI = BuildSolidityParams.toABI(TestEvent_index[1]);
        List<EventResult> ret2 =  parseTransactionReceipt(receipt, ABI);
        System.out.println(ret2 == null?"null":"not null");
    }
}
