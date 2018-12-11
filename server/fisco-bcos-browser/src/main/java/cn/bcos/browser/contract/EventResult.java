package cn.bcos.browser.contract;

import java.util.ArrayList;
import java.util.List;

import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;

public class EventResult {
    private int index;
    private String name;
    private List<NamedType> params;
    private List<Type> values;

    public EventResult(String name, List<NamedType> params, List<Type> values) {
        this.name = name;
        this.params = params;
        this.values = values;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EventResult(String name) {
        this.name = name;
        this.params = new ArrayList<NamedType>();
        this.values = new ArrayList<Type>();
    }

    public void addParam(NamedType param) {
        this.params.add(param);
    }

    public void addValue(Type value) {
        this.values.add(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NamedType> getParams() {
        return params;
    }

    public void setParams(List<NamedType> params) {
        this.params = params;
    }

    public List<Type> getValues() {
        return values;
    }

    public void setValues(List<Type> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        for (int i = 0; i < params.size(); ++i) {
            builder.append(System.getProperty("line.separator"));
            builder.append(params.get(i).getName());
            builder.append(": ");
            if (params.get(i).getType().equals("address"))
                builder.append(values.get(i));
            else
                builder.append(values.get(i));
        }
        return builder.toString();
    }

}
