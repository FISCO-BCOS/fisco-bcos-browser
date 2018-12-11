package cn.bcos.browser.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.DynamicBytes;
import org.bcos.web3j.abi.datatypes.StaticArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.*;
import org.bcos.web3j.crypto.Hash;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.bcos.browser.service.GovernService;
import cn.bcos.browser.service.Main;



/**
 * TODO，该文件有没有自动生成的可能？研究下org.bcos.web3j.codegen
 * 
 * @author 李龙谱 负责三个功能： 0. 根据ABI接口，生成对应的哈希码
 *         1.根据ABI接口，将字符串类型的输入参数转化为相应类型的solidity输入参数； 2. 根据ABI接口，构造返回值的存储空间；
 */
public class BuildSolidityParams {
    //将字符串形式的abi，转换为AbiDefinition。
    public static AbiDefinition toABI(String abi) {
        return JSON.parseObject(abi, AbiDefinition.class);
    }
    
    public static String buildMethodId(AbiDefinition abi) {
        // copy org.bcos.web3j.abi.FunctionEncoder.java
        // 1. 拼接
        StringBuilder result = new StringBuilder();
        result.append(abi.getName()); // 方法名称
        result.append("(");
        String params = abi.getInputs().stream().map(NamedType::getType).collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        // 2. 求sha，并截取前10个字节
        byte[] hash = Hash.sha3(result.toString().getBytes());
        return Numeric.toHexString(hash).substring(0, 10);
    }

    /**
     * TODO 补全所有类型
     * 
     * @param params：输入参数
     * @param abi：ABI接口
     * @return solidity格式的输入参数
     */
    public static List<Type> javaParamsToSolidityParams(List<String> params, AbiDefinition abi) {
        List<Type> solidityParams = new ArrayList<Type>();
        for (int i = 0; i < abi.getInputs().size(); ++i) {
            NamedType nt = abi.getInputs().get(i);
            switch (nt.getType()) {
            case "address":
                Address addr = new Address(params.get(i));
                solidityParams.add(addr);
                break;
            case "uint256":
                Uint256 num = new Uint256(Integer.parseInt(params.get(i)));
                solidityParams.add(num);
                break;
            case "int256":
                Int256 intNum = new Int256(Integer.parseInt(params.get(i)));
                solidityParams.add(intNum);
                break;
            case "string":
                Utf8String str = new Utf8String(params.get(i));
                solidityParams.add(str);
                break;
            case "uint8":
                Uint8 uint8 = new Uint8(Integer.parseInt(params.get(i)));
                solidityParams.add(uint8);
                break;
            case "Bytes32":
            case "bytes32":
                Bytes32 bytes32 = new Bytes32(params.get(i).getBytes());
                solidityParams.add(bytes32);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported type encountered: " + nt.getType());
            }
        }
        return solidityParams;
    }

    // input:address[2]; return 2;
    private static int getArraySize(String arrayName) {
        String numStr = arrayName.substring(arrayName.lastIndexOf("[") + 1, arrayName.lastIndexOf("]"));
        return Integer.parseInt(numStr);
    }

    static TypeReference<?> checkStaticArray(String typeName) {
        if (Pattern.matches("address\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(getArraySize(typeName)) {
            };
        } else if (Pattern.matches("bytes32\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes32>>(getArraySize(typeName)) {
            };
        } else if (Pattern.matches("[Uu]int8\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Uint8>>(getArraySize(typeName)) {
            };
        } else if (Pattern.matches("int8\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Int8>>(getArraySize(typeName)) {
            };
        } else if (Pattern.matches("[Uu]int32\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Uint32>>(getArraySize(typeName)) {
            };
        } else if (Pattern.matches("int32\\[\\d+\\]", typeName)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Int32>>(getArraySize(typeName)) {
            };
        } else {
            return null;// 不用throw异常，如果这里是null，buildReturnParams会throwUnsupport异常
        }
    }

    public static TypeReference<?> getType(String type) {
        switch (type) {
        case "address[]":
            return new TypeReference<DynamicArray<Address>>() {
            };
        case "bytes32[]":
            return new TypeReference<DynamicArray<Bytes32>>() {
            };
        case "Uint8[]":
            return new TypeReference<DynamicArray<Uint8>>() {
            };
        case "int8[]":
            return new TypeReference<DynamicArray<Uint8>>() {
            };
        case "address":
            return new TypeReference<Address>() {
            };
        case "bool":
            return new TypeReference<Bool>() {
            };
        case "string":
            return new TypeReference<Utf8String>() {
            };
        case "bytes":
            return new TypeReference<DynamicBytes>() {
            };
        case "uint8":
            return new TypeReference<Uint8>() {
            };
        case "int8":
            return new TypeReference<Int8>() {
            };
        case "uint16":
            return new TypeReference<Uint16>() {
            };
        case "int16":
            return new TypeReference<Int16>() {
            };
        case "uint24":
            return new TypeReference<Uint24>() {
            };
        case "int24":
            return new TypeReference<Int24>() {
            };
        case "uint32":
            return new TypeReference<Uint32>() {
            };
        case "int32":
            return new TypeReference<Int32>() {
            };
        case "uint40":
            return new TypeReference<Uint40>() {
            };
        case "int40":
            return new TypeReference<Int40>() {
            };
        case "uint48":
            return new TypeReference<Uint48>() {
            };
        case "int48":
            return new TypeReference<Int48>() {
            };
        case "uint56":
            return new TypeReference<Uint56>() {
            };
        case "int56":
            return new TypeReference<Int56>() {
            };
        case "uint64":
            return new TypeReference<Uint64>() {
            };
        case "int64":
            return new TypeReference<Int64>() {
            };
        case "uint72":
            return new TypeReference<Uint72>() {
            };
        case "int72":
            return new TypeReference<Int72>() {
            };
        case "uint80":
            return new TypeReference<Uint80>() {
            };
        case "int80":
            return new TypeReference<Int80>() {
            };
        case "uint88":
            return new TypeReference<Uint88>() {
            };
        case "int88":
            return new TypeReference<Int88>() {
            };
        case "uint96":
            return new TypeReference<Uint96>() {
            };
        case "int96":
            return new TypeReference<Int96>() {
            };
        case "uint104":
            return new TypeReference<Uint104>() {
            };
        case "int104":
            return new TypeReference<Int104>() {
            };
        case "uint112":
            return new TypeReference<Uint112>() {
            };
        case "int112":
            return new TypeReference<Int112>() {
            };
        case "uint120":
            return new TypeReference<Uint120>() {
            };
        case "int120":
            return new TypeReference<Int120>() {
            };
        case "uint128":
            return new TypeReference<Uint128>() {
            };
        case "int128":
            return new TypeReference<Int128>() {
            };
        case "uint136":
            return new TypeReference<Uint136>() {
            };
        case "int136":
            return new TypeReference<Int136>() {
            };
        case "uint144":
            return new TypeReference<Uint144>() {
            };
        case "int144":
            return new TypeReference<Int144>() {
            };
        case "uint152":
            return new TypeReference<Uint152>() {
            };
        case "int152":
            return new TypeReference<Int152>() {
            };
        case "uint160":
            return new TypeReference<Uint160>() {
            };
        case "int160":
            return new TypeReference<Int160>() {
            };
        case "uint168":
            return new TypeReference<Uint168>() {
            };
        case "int168":
            return new TypeReference<Int168>() {
            };
        case "uint176":
            return new TypeReference<Uint176>() {
            };
        case "int176":
            return new TypeReference<Int176>() {
            };
        case "uint184":
            return new TypeReference<Uint184>() {
            };
        case "int184":
            return new TypeReference<Int184>() {
            };
        case "uint192":
            return new TypeReference<Uint192>() {
            };
        case "int192":
            return new TypeReference<Int192>() {
            };
        case "uint200":
            return new TypeReference<Uint200>() {
            };
        case "int200":
            return new TypeReference<Int200>() {
            };
        case "uint208":
            return new TypeReference<Uint208>() {
            };
        case "int208":
            return new TypeReference<Int208>() {
            };
        case "uint216":
            return new TypeReference<Uint216>() {
            };
        case "int216":
            return new TypeReference<Int216>() {
            };
        case "uint224":
            return new TypeReference<Uint224>() {
            };
        case "int224":
            return new TypeReference<Int224>() {
            };
        case "uint232":
            return new TypeReference<Uint232>() {
            };
        case "int232":
            return new TypeReference<Int232>() {
            };
        case "uint240":
            return new TypeReference<Uint240>() {
            };
        case "int240":
            return new TypeReference<Int240>() {
            };
        case "uint248":
            return new TypeReference<Uint248>() {
            };
        case "int248":
            return new TypeReference<Int248>() {
            };
        case "uint256":
            return new TypeReference<Uint256>() {
            };
        case "int256":
            return new TypeReference<Int256>() {
            };
        case "ufixed8x248":
            return new TypeReference<Ufixed8x248>() {
            };
        case "fixed8x248":
            return new TypeReference<Fixed8x248>() {
            };
        case "ufixed16x240":
            return new TypeReference<Ufixed16x240>() {
            };
        case "fixed16x240":
            return new TypeReference<Fixed16x240>() {
            };
        case "ufixed24x232":
            return new TypeReference<Ufixed24x232>() {
            };
        case "fixed24x232":
            return new TypeReference<Fixed24x232>() {
            };
        case "ufixed32x224":
            return new TypeReference<Ufixed32x224>() {
            };
        case "fixed32x224":
            return new TypeReference<Fixed32x224>() {
            };
        case "ufixed40x216":
            return new TypeReference<Ufixed40x216>() {
            };
        case "fixed40x216":
            return new TypeReference<Fixed40x216>() {
            };
        case "ufixed48x208":
            return new TypeReference<Ufixed48x208>() {
            };
        case "fixed48x208":
            return new TypeReference<Fixed48x208>() {
            };
        case "ufixed56x200":
            return new TypeReference<Ufixed56x200>() {
            };
        case "fixed56x200":
            return new TypeReference<Fixed56x200>() {
            };
        case "ufixed64x192":
            return new TypeReference<Ufixed64x192>() {
            };
        case "fixed64x192":
            return new TypeReference<Fixed64x192>() {
            };
        case "ufixed72x184":
            return new TypeReference<Ufixed72x184>() {
            };
        case "fixed72x184":
            return new TypeReference<Fixed72x184>() {
            };
        case "ufixed80x176":
            return new TypeReference<Ufixed80x176>() {
            };
        case "fixed80x176":
            return new TypeReference<Fixed80x176>() {
            };
        case "ufixed88x168":
            return new TypeReference<Ufixed88x168>() {
            };
        case "fixed88x168":
            return new TypeReference<Fixed88x168>() {
            };
        case "ufixed96x160":
            return new TypeReference<Ufixed96x160>() {
            };
        case "fixed96x160":
            return new TypeReference<Fixed96x160>() {
            };
        case "ufixed104x152":
            return new TypeReference<Ufixed104x152>() {
            };
        case "fixed104x152":
            return new TypeReference<Fixed104x152>() {
            };
        case "ufixed112x144":
            return new TypeReference<Ufixed112x144>() {
            };
        case "fixed112x144":
            return new TypeReference<Fixed112x144>() {
            };
        case "ufixed120x136":
            return new TypeReference<Ufixed120x136>() {
            };
        case "fixed120x136":
            return new TypeReference<Fixed120x136>() {
            };
        case "ufixed128x128":
            return new TypeReference<Ufixed128x128>() {
            };
        case "fixed128x128":
            return new TypeReference<Fixed128x128>() {
            };
        case "ufixed136x120":
            return new TypeReference<Ufixed136x120>() {
            };
        case "fixed136x120":
            return new TypeReference<Fixed136x120>() {
            };
        case "ufixed144x112":
            return new TypeReference<Ufixed144x112>() {
            };
        case "fixed144x112":
            return new TypeReference<Fixed144x112>() {
            };
        case "ufixed152x104":
            return new TypeReference<Ufixed152x104>() {
            };
        case "fixed152x104":
            return new TypeReference<Fixed152x104>() {
            };
        case "ufixed160x96":
            return new TypeReference<Ufixed160x96>() {
            };
        case "fixed160x96":
            return new TypeReference<Fixed160x96>() {
            };
        case "ufixed168x88":
            return new TypeReference<Ufixed168x88>() {
            };
        case "fixed168x88":
            return new TypeReference<Fixed168x88>() {
            };
        case "ufixed176x80":
            return new TypeReference<Ufixed176x80>() {
            };
        case "fixed176x80":
            return new TypeReference<Fixed176x80>() {
            };
        case "ufixed184x72":
            return new TypeReference<Ufixed184x72>() {
            };
        case "fixed184x72":
            return new TypeReference<Fixed184x72>() {
            };
        case "ufixed192x64":
            return new TypeReference<Ufixed192x64>() {
            };
        case "fixed192x64":
            return new TypeReference<Fixed192x64>() {
            };
        case "ufixed200x56":
            return new TypeReference<Ufixed200x56>() {
            };
        case "fixed200x56":
            return new TypeReference<Fixed200x56>() {
            };
        case "ufixed208x48":
            return new TypeReference<Ufixed208x48>() {
            };
        case "fixed208x48":
            return new TypeReference<Fixed208x48>() {
            };
        case "ufixed216x40":
            return new TypeReference<Ufixed216x40>() {
            };
        case "fixed216x40":
            return new TypeReference<Fixed216x40>() {
            };
        case "ufixed224x32":
            return new TypeReference<Ufixed224x32>() {
            };
        case "fixed224x32":
            return new TypeReference<Fixed224x32>() {
            };
        case "ufixed232x24":
            return new TypeReference<Ufixed232x24>() {
            };
        case "fixed232x24":
            return new TypeReference<Fixed232x24>() {
            };
        case "ufixed240x16":
            return new TypeReference<Ufixed240x16>() {
            };
        case "fixed240x16":
            return new TypeReference<Fixed240x16>() {
            };
        case "ufixed248x8":
            return new TypeReference<Ufixed248x8>() {
            };
        case "fixed248x8":
            return new TypeReference<Fixed248x8>() {
            };
        case "bytes1":
            return new TypeReference<Bytes1>() {
            };
        case "bytes2":
            return new TypeReference<Bytes2>() {
            };
        case "bytes3":
            return new TypeReference<Bytes3>() {
            };
        case "bytes4":
            return new TypeReference<Bytes4>() {
            };
        case "bytes5":
            return new TypeReference<Bytes5>() {
            };
        case "bytes6":
            return new TypeReference<Bytes6>() {
            };
        case "bytes7":
            return new TypeReference<Bytes7>() {
            };
        case "bytes8":
            return new TypeReference<Bytes8>() {
            };
        case "bytes9":
            return new TypeReference<Bytes9>() {
            };
        case "bytes10":
            return new TypeReference<Bytes10>() {
            };
        case "bytes11":
            return new TypeReference<Bytes11>() {
            };
        case "bytes12":
            return new TypeReference<Bytes12>() {
            };
        case "bytes13":
            return new TypeReference<Bytes13>() {
            };
        case "bytes14":
            return new TypeReference<Bytes14>() {
            };
        case "bytes15":
            return new TypeReference<Bytes15>() {
            };
        case "bytes16":
            return new TypeReference<Bytes16>() {
            };
        case "bytes17":
            return new TypeReference<Bytes17>() {
            };
        case "bytes18":
            return new TypeReference<Bytes18>() {
            };
        case "bytes19":
            return new TypeReference<Bytes19>() {
            };
        case "bytes20":
            return new TypeReference<Bytes20>() {
            };
        case "bytes21":
            return new TypeReference<Bytes21>() {
            };
        case "bytes22":
            return new TypeReference<Bytes22>() {
            };
        case "bytes23":
            return new TypeReference<Bytes23>() {
            };
        case "bytes24":
            return new TypeReference<Bytes24>() {
            };
        case "bytes25":
            return new TypeReference<Bytes25>() {
            };
        case "bytes26":
            return new TypeReference<Bytes26>() {
            };
        case "bytes27":
            return new TypeReference<Bytes27>() {
            };
        case "bytes28":
            return new TypeReference<Bytes28>() {
            };
        case "bytes29":
            return new TypeReference<Bytes29>() {
            };
        case "bytes30":
            return new TypeReference<Bytes30>() {
            };
        case "bytes31":
            return new TypeReference<Bytes31>() {
            };
        case "bytes32":
            return new TypeReference<Bytes32>() {
            };
        /*
         * test contract return array addr case "TransactionSucCallback": return new
         * TypeReference<TransactionSucCallback>() {}; break;
         */
        default:
            throw new UnsupportedOperationException("Unsupported type encountered: " + type);
        }
    }

    /**
     * @param abi：abi接口定义
     * @return 返回值的存储空间
     */
    public static List<TypeReference<?>> buildReturnParams(AbiDefinition abi) {
        List<TypeReference<?>> solidityReturnParams = new ArrayList<TypeReference<?>>();
        for (int i = 0; i < abi.getOutputs().size(); ++i) {
            NamedType nt = abi.getOutputs().get(i);

            TypeReference<?> tr = checkStaticArray(nt.getType());// 返回静态数组类型，需要特殊处理
            if (tr != null) {
                solidityReturnParams.add(tr);
                continue;
            }
            solidityReturnParams.add(getType(nt.getType()));
        }
        return solidityReturnParams;
    }
}